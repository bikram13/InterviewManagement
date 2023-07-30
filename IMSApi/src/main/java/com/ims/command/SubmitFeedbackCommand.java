package com.ims.command;

import com.google.gson.Gson;
import com.ims.dao.CandidateInterviewerDAO;
import com.ims.dao.FeedbackDAO;
import com.ims.entity.CandidateInterviewer;
import com.ims.entity.Feedback;
import com.ims.model.FeedbackData;

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
		FeedbackData feedbackData = parseFeedbackFromRequest(request);
		if (feedbackData == null) {
			return createErrorResponse("Error parsing request JSON.");
		}
		System.out.println("Test....."+feedbackData.getInterviewerUserId());
		System.out.println("Test....."+feedbackData.getCanidateId());
		// Update the feedback status in the CANDIDATE_INTERVIEWER table to SUBMITTED
		CandidateInterviewer candidateInterviewer = candidateInterviewerDAO
				.getCandidateInterviewersByCandidateIdandInterviewerId(feedbackData.getCanidateId(),feedbackData.getInterviewerUserId());
		if (candidateInterviewer == null) {
			return createErrorResponse("CandidateInterviewer not found in the database.");
		}

		feedbackData.setCandidateInterviewer(candidateInterviewer);

//		Feedback feedbackEntity= new Feedback(null, candidateInterviewer, feedbackData.getTechFeedback(), feedbackData.getDomainFeedback(), feedbackData.getDomainFeedback(), feedbackData.getFinalResult(), null, null);
	
		candidateInterviewer.setFeedbackStatus("SUBMITTED");
		Feedback feedbackEntity=new Feedback();
		feedbackEntity.setCandidateInterviewer(candidateInterviewer);
		feedbackEntity.setTechFeedback(feedbackData.getTechFeedback());
		feedbackEntity.setDomainFeedback(feedbackData.getDomainFeedback());
		feedbackEntity.setCommSkillsFeedback(feedbackData.getCommSkillsFeedback());
		feedbackEntity.setFinalResult(feedbackData.getFinalResult());
		
	

		
		candidateInterviewerDAO.updateCandidateInterviewer(candidateInterviewer);
		
		
		
		boolean feedbackSaved = feedbackDAO.addFeedback(feedbackEntity);
		System.out.println(feedbackSaved);
		if (!feedbackSaved) {
			return createErrorResponse("Error saving feedback to the database.");
		}

		// Prepare the success response JSON
		return createSuccessResponse("Feedback submitted successfully!");
	}

	private FeedbackData parseFeedbackFromRequest(HttpServletRequest request) {
		try {
			Gson gson = new Gson();
			return gson.fromJson(request.getReader(), FeedbackData.class);
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
