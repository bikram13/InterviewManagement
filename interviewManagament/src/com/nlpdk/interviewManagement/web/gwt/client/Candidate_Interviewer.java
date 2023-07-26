package com.nlpdk.interviewManagement.web.gwt.client;

public class Candidate_Interviewer {
	private Candidate candidate;
	private Users interviewer;

	public Candidate_Interviewer(Candidate candidate, Users interviewer) {
		this.candidate = candidate;
		this.interviewer = interviewer;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Users getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(Users interviewer) {
		this.interviewer = interviewer;
	}

}
