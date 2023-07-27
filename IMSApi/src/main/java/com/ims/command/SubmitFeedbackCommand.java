package com.ims.command;

import com.google.gson.Gson;
import com.ims.dao.CandidateInterviewerDAO;
import com.ims.dao.FeedbackDAO;
import com.ims.entity.CandidateInterviewer;
import com.ims.entity.Feedback;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SubmitFeedbackCommand implements Command {

	private FeedbackDAO feedbackDAO;
	private CandidateInterviewerDAO candidateInterviewerDAO;

	public SubmitFeedbackCommand() {
		feedbackDAO = new FeedbackDAO();
		candidateInterviewerDAO = new CandidateInterviewerDAO();
	}

	@Override
	public Object doExecute(HttpServletRequest request, HttpServletResponse response) {
		// Parse the request JSON into the Feedback object
		Feedback feedback = parseFeedbackFromRequest(request);
		if (feedback == null) {
			return createErrorResponse("Error parsing request JSON.");
		}
		System.out.println("Test.....");
		// Update the feedback status in the CANDIDATE_INTERVIEWER table to SUBMITTED
		CandidateInterviewer candidateInterviewer = candidateInterviewerDAO
				.getCandidateInterviewerById(feedback.getCandidateInterviewer().getId().intValue());
		if (candidateInterviewer == null) {
			return createErrorResponse("CandidateInterviewer not found in the database.");
		}

		feedback.setCandidateInterviewer(candidateInterviewer);

		// Save the feedback in the database
		boolean feedbackSaved = feedbackDAO.addFeedback(feedback);
		System.out.println(feedbackSaved);
		if (!feedbackSaved) {
			return createErrorResponse("Error saving feedback to the database.");
		}

		candidateInterviewer.setFeedbackStatus("SUBMITTED");
		candidateInterviewerDAO.updateCandidateInterviewer(candidateInterviewer);

		// Prepare the success response JSON
		return createSuccessResponse("Feedback submitted successfully!");
	}

	private Feedback parseFeedbackFromRequest(HttpServletRequest request) {
		try {
			Gson gson = new Gson();
			return gson.fromJson(request.getReader(), Feedback.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String createErrorResponse(String message) {
		return "{\"status\":\"error\",\"message\":\"" + message + "\"}";
	}

	private String createSuccessResponse(String message) {
		return "{\"status\":\"success\",\"message\":\"" + message + "\"}";
	}
}
