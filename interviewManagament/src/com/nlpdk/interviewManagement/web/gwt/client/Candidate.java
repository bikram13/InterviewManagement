package com.nlpdk.interviewManagement.web.gwt.client;

import java.util.List;

public class Candidate {
	private int candidateId;
	private String name;
	private String email;
	private String psNo;
	private String contactNo;
	private List<String> interviewers;

	// Feedback data
	private String techFeedback;
	private String domainFeedback;
	private String commSkillsFeedback;
	private String finalResult;

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

	public Candidate(int candidateId, String name, String email, String psNo, String contactNo,
			List<String> interviewers, String techFeedback, String domainFeedback, String commSkillsFeedback,
			String finalResult) {
		super();
		this.candidateId = candidateId;
		this.name = name;
		this.email = email;
		this.psNo = psNo;
		this.contactNo = contactNo;
		this.interviewers = interviewers;
		this.techFeedback = techFeedback;
		this.domainFeedback = domainFeedback;
		this.commSkillsFeedback = commSkillsFeedback;
		this.finalResult = finalResult;
	}

	// Getters and setters for the candidate fields

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPsNo() {
		return psNo;
	}

	public void setPsNo(String psNo) {
		this.psNo = psNo;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public List<String> getInterviewers() {
		return interviewers;
	}

	public void setInterviewers(List<String> interviewers) {
		this.interviewers = interviewers;
	}

	public int getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}

}