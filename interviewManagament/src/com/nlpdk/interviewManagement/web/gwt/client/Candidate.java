package com.nlpdk.interviewManagement.web.gwt.client;

import java.util.List;

public class Candidate {
	private int candidateId;
	private String name;
	private String email;
	private String psNo;
	private String contactNo;
	private List<Users> interviewers;

	// Getters and setters for the candidate fields

	public String getName() {
		return name;
	}

	public Candidate() {
		super();
		// TODO Auto-generated constructor stub
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

	public Candidate(int candidateId, String name, String email, String psNo, String contactNo,
			List<Users> interviewers) {
		super();
		this.candidateId = candidateId;
		this.name = name;
		this.email = email;
		this.psNo = psNo;
		this.contactNo = contactNo;
		this.interviewers = interviewers;
	}

	public List<Users> getInterviewers() {
		return interviewers;
	}

	public void setInterviewers(List<Users> interviewers) {
		this.interviewers = interviewers;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public int getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}

}