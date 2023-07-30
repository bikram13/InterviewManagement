package com.ims.model;

import com.ims.entity.CandidateInterviewer;

// Inner class to hold feedback data for each interviewer
public class FeedbackData {
	private String interviewerName;
	private String techFeedback;
	private String domainFeedback;
	private String commSkillsFeedback;
	private String finalResult;
	private CandidateInterviewer candidateInterviewer;
	private Long interviewerUserId;
	private Long candidateId;

	public Long getCanidateId() {
		return candidateId;
	}

	public void setCanidateId(Long canidateId) {
		this.candidateId = canidateId;
	}

	public Long getInterviewerUserId() {
		return interviewerUserId;
	}

	public void setInterviewerUserId(Long interviewerUserId) {
		this.interviewerUserId = interviewerUserId;
	}

	public CandidateInterviewer getCandidateInterviewer() {
		return candidateInterviewer;
	}

	public void setCandidateInterviewer(CandidateInterviewer candidateInterviewer) {
		this.candidateInterviewer = candidateInterviewer;
	}

	public String getInterviewerName() {
		return interviewerName;
	}

	public void setInterviewerName(String interviewerName) {
		this.interviewerName = interviewerName;
	}

	public String getTechFeedback() {
		return techFeedback;
	}

	public void setTechFeedback(String techFeedback) {
		this.techFeedback = techFeedback;
	}

	public String getDomainFeedback() {
		return domainFeedback;
	}

	public void setDomainFeedback(String domainFeedback) {
		this.domainFeedback = domainFeedback;
	}

	public String getCommSkillsFeedback() {
		return commSkillsFeedback;
	}

	public void setCommSkillsFeedback(String commSkillsFeedback) {
		this.commSkillsFeedback = commSkillsFeedback;
	}

	public String getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(String finalResult) {
		this.finalResult = finalResult;
	}

	@Override
	public String toString() {
		return "FeedbackData [interviewerName=" + interviewerName + ", techFeedback=" + techFeedback
				+ ", domainFeedback=" + domainFeedback + ", commSkillsFeedback=" + commSkillsFeedback + ", finalResult="
				+ finalResult + ", candidateInterviewer=" + candidateInterviewer + ", interviewerUserId="
				+ interviewerUserId + ", canidateId=" + candidateId + "]";
	}

	// Getters and setters (omitted for brevity)

}