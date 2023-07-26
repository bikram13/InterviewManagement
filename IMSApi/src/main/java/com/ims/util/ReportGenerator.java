package com.ims.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.ims.entity.Candidate;
import com.ims.entity.CandidateInterviewer;
import com.ims.entity.Feedback;
import com.ims.entity.Users;
import com.ims.service.impl.FeedbackServiceImpl;
import com.opencsv.CSVWriter;

public class ReportGenerator {

	FeedbackServiceImpl feedbackService = new FeedbackServiceImpl();

	public void scheduleReportGeneration() {
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		// Calculate the delay until the next 5 am
		long initialDelay = calculateInitialDelayToFiveAM();
		// Schedule the task to run at 5 am daily
		scheduler.scheduleAtFixedRate(this::generateCandidateReport, initialDelay, 24, TimeUnit.HOURS);
	}

	private long calculateInitialDelayToFiveAM() {
		LocalDate now = LocalDate.now();
		LocalDate tomorrow = now.plusDays(1);
		LocalDate targetDate = tomorrow.atTime(5, 0, 0).toLocalDate();
		return targetDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC) - System.currentTimeMillis() / 1000;
	}

	private void generateCandidateReport() {
		// Create the report folder if it doesn't exist
		String reportFolderPath = "/Users/bikram/eclipse-workspace/IMSApi/src/main/resources";
		File reportFolder = new File(reportFolderPath);
		if (!reportFolder.exists()) {
			reportFolder.mkdirs();
		}

		System.out.println("Path: " + reportFolderPath);

		// Get the list of candidates and their feedbacks
		List<Feedback> feedbacks = feedbackService.getAllFeedbacks();

		// Generate the PDF and CSV reports
//        generatePDFReport(feedbacks, reportFolderPath);
		generateCSVReport(feedbacks, reportFolderPath);
	}

	private void generatePDFReport(List<Feedback> feedbacks, String reportFolderPath) {
		String pdfFilePath = reportFolderPath + "/CandidateReport.pdf";

		// Add font for PDF generation
		PDType1Font font = PDType1Font.HELVETICA;

		try (PDDocument document = new PDDocument()) {
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);

			try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
				// Set up table parameters
				float margin = 50;
				float yStart = page.getMediaBox().getHeight() - margin;
				float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
				float tableMargin = 10;
				float bottomMargin = 70;
				float yPosition = yStart;
				int numberOfRows = feedbacks.size() + 1;
				int numberOfColumns = 9;
				float rowHeight = 20f;
				float tableWidthAdjustment = tableWidth / numberOfColumns;

				// Create the table header
				String[] headers = { "Candidate ID", "Candidate Name", "Email", "PSNo", "Interviewer Name",
						"Technical Feedback", "Domain Feedback", "Comm Skills Feedback", "Final Result" };
				float currentX = margin;
				float currentY = yStart - tableMargin;
				for (int i = 0; i < numberOfColumns; i++) {
					contentStream.setFont(font, 12);
					contentStream.beginText();
					contentStream.moveTextPositionByAmount(currentX, currentY);
					contentStream.drawString(headers[i]);
					contentStream.endText();
					currentX += tableWidthAdjustment;
				}

				// Create the table content
				for (Feedback feedback : feedbacks) {
					currentY -= rowHeight;
					currentX = margin;
					// Fetch related entities
					CandidateInterviewer candidateInterviewer = feedback.getCandidateInterviewer();
					Candidate candidate = candidateInterviewer.getCandidate();
					Users interviewer = candidateInterviewer.getInterviewer();
					String[] rowData = { String.valueOf(candidate.getId()),
							candidate.getFirstName() + " " + candidate.getLastName(), candidate.getEmailId(),
							candidate.getPsNo(), interviewer.getFirstName() + " " + interviewer.getLastName(),
							feedback.getTechFeedback(), feedback.getDomainFeedback(), feedback.getCommSkillsFeedback(),
							feedback.getFinalResult() };
					for (int i = 0; i < numberOfColumns; i++) {
						contentStream.setFont(font, 12);
						contentStream.beginText();
						contentStream.moveTextPositionByAmount(currentX, currentY);
						contentStream.drawString(rowData[i]);
						contentStream.endText();
						currentX += tableWidthAdjustment;
					}
				}

				// Draw table borders
				contentStream.setLineWidth(0.5f);
				currentY = yStart - tableMargin;
				for (int i = 0; i <= numberOfRows; i++) {
					contentStream.moveTo(margin, currentY);
					contentStream.lineTo(margin + tableWidth, currentY);
					contentStream.stroke();
					currentY -= rowHeight;
				}
				currentX = margin;
				for (int i = 0; i <= numberOfColumns; i++) {
					contentStream.moveTo(currentX, yStart);
					contentStream.lineTo(currentX, currentY + rowHeight);
					contentStream.stroke();
					currentX += tableWidthAdjustment;
				}

				contentStream.close();
				// Save the PDF report
				document.save(new File(pdfFilePath));
				System.out.println("PDF report saved at: " + pdfFilePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Generate CSV report method
	private void generateCSVReport(List<Feedback> feedbacks, String reportFolderPath) {
		String csvFilePath = reportFolderPath + "/CandidateReport.csv";

		try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
			// Define the CSV headers
			String[] headers = { "Candidate ID", "Candidate Name", "Email", "PSNo", "Interviewer Name",
					"Technical Feedback", "Domain Feedback", "Comm Skills Feedback", "Final Result" };
			writer.writeNext(headers);

			// Write the data rows to the CSV file
			for (Feedback feedback : feedbacks) {
				CandidateInterviewer candidateInterviewer = feedback.getCandidateInterviewer();
				Candidate candidate = candidateInterviewer.getCandidate();
				Users interviewer = candidateInterviewer.getInterviewer();

				String[] dataRow = { String.valueOf(candidate.getId()),
						candidate.getFirstName() + " " + candidate.getLastName(), candidate.getEmailId(),
						candidate.getPsNo(), interviewer.getFirstName() + " " + interviewer.getLastName(),
						feedback.getTechFeedback(), feedback.getDomainFeedback(), feedback.getCommSkillsFeedback(),
						feedback.getFinalResult() };
				writer.writeNext(dataRow);
			}

			System.out.println("CSV report saved at: " + csvFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
