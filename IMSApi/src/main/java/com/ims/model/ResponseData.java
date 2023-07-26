package com.ims.model;

import java.util.List;

import com.ims.model.FeedbackData;

// Inner class to hold the overall response data
public class ResponseData {
	private Long candidateId;
	private String candidateName;
	private List<FeedbackData> feedbackData;

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public List<FeedbackData> getFeedbackData() {
		return feedbackData;
	}

	public void setFeedbackData(List<FeedbackData> feedbackData) {
		this.feedbackData = feedbackData;
	}

	// Getters and setters (omitted for brevity)

}