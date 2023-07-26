package com.nlpdk.interviewManagement.web.gwt.client;

public class FeedbackData {
	private Candidate_Interviewer candidateInterviewer;
	private String techFeedback;
	private String domainFeedback;
	private String commSkillsFeedback;
	private String finalResult;

	public FeedbackData(Candidate_Interviewer candidateInterviewer, String techFeedback, String domainFeedback,
			String commSkillsFeedback, String finalResult) {
		this.candidateInterviewer = candidateInterviewer;
		this.techFeedback = techFeedback;
		this.domainFeedback = domainFeedback;
		this.commSkillsFeedback = commSkillsFeedback;
		this.finalResult = finalResult;
	}

	public Candidate_Interviewer getCandidateInterviewer() {
		return candidateInterviewer;
	}

	public void setCandidateInterviewer(Candidate_Interviewer candidateInterviewer) {
		this.candidateInterviewer = candidateInterviewer;
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

}
