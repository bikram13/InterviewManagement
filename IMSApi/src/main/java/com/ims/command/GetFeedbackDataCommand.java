package com.ims.command;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ims.dao.CandidateDAO;
import com.ims.dao.CandidateInterviewerDAO;
import com.ims.dao.FeedbackDAO;
import com.ims.entity.Candidate;
import com.ims.entity.CandidateInterviewer;
import com.ims.entity.Feedback;
import com.ims.model.FeedbackData;
import com.ims.model.ResponseData;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetFeedbackDataCommand implements Command {

	@Override
	public Object doExecute(HttpServletRequest request, HttpServletResponse response) {
		try {

			// Step 1: Read JSON data from the request's input stream
			BufferedReader reader = request.getReader();
			StringBuilder jsonData = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				jsonData.append(line);
			}

			// Step 2: Parse the JSON string to JsonObject
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = jsonParser.parse(jsonData.toString()).getAsJsonObject();

			// Now you can work with the jsonObject and access its properties
			// For example, if your JSON contains "candidateId" and "candidateName"
			// properties:
			String candidateIdStr = jsonObject.get("candidateId").getAsString();
			String candidateName = jsonObject.get("candidateName").getAsString();

			// Convert candidateIdStr to a numeric type if needed
			long candidateId = Long.parseLong(candidateIdStr);

			// Fetch the candidate from the database using the candidate ID
			CandidateDAO candidateDAO = new CandidateDAO();
			Candidate candidate = candidateDAO.getCandidateById(candidateId);

			if (candidate == null) {
				// Candidate not found, return an empty response
				return "{\"error\": \"Candidate not found\"}";
			}

			// Fetch all candidate interviewers from the database for the given candidate
			CandidateInterviewerDAO candidateInterviewerDAO = new CandidateInterviewerDAO();
			List<CandidateInterviewer> candidateInterviewers = candidateInterviewerDAO
					.getAllCandidateInterviewersByCandidateId(candidateId);

			// Prepare the feedback data for the candidate
			List<FeedbackData> feedbackDataList = new ArrayList<>();
			for (CandidateInterviewer candidateInterviewer : candidateInterviewers) {
				// Fetch feedback for each candidate interviewer
				FeedbackDAO feedbackDAO = new FeedbackDAO();
				Feedback feedback = feedbackDAO.getFeedbackByCandidateInterviewerId(candidateInterviewer.getId());

				if (feedback != null) {
					// Prepare FeedbackData object and add it to the list
					FeedbackData feedbackData = new FeedbackData();
					feedbackData.setInterviewerName(candidateInterviewer.getInterviewer().getFirstName() + " "
							+ candidateInterviewer.getInterviewer().getLastName());
					feedbackData.setTechFeedback(feedback.getTechFeedback());
					feedbackData.setDomainFeedback(feedback.getDomainFeedback());
					feedbackData.setCommSkillsFeedback(feedback.getCommSkillsFeedback());
					feedbackData.setFinalResult(feedback.getFinalResult());
					feedbackData.setCandidateInterviewer(candidateInterviewer);
					feedbackDataList.add(feedbackData);
				}
			}

			// Prepare the response JSON
			ResponseData responseData = new ResponseData();
			responseData.setCandidateId(candidate.getId());
			responseData.setCandidateName(candidateName);
			responseData.setFeedbackData(feedbackDataList);

			// Convert the response data to JSON using Gson library
			Gson gson = new Gson();
			return gson.toJson(responseData);
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"error\": \"An error occurred while processing the request\"}";
		}
	}
}
