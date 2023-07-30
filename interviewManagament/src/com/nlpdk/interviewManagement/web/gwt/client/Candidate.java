package com.nlpdk.interviewManagement.web.gwt.client;
import java.util.List;
public class Candidate {
	@Override
	public String toString() {
		return "Candidate [candidateId=" + candidateId + ", name=" + name + ", email=" + email + ", psNo=" + psNo
				+ ", contactNo=" + contactNo + ", interviewers=" + interviewers + ", assignedInterviwers="
				+ assignedInterviwers + "]";
	}
	private long candidateId;
	private String name;
	private String email;
	private String psNo;
	private String contactNo;
	private List<Users> interviewers;
	private String assignedInterviwers;
	public String getAssignedInterviwers() {
		return assignedInterviwers;
	}
	public void setAssignedInterviwers(String assignedInterviwers) {
		this.assignedInterviwers = assignedInterviwers;
	}
	
	public String getName() {
		return name;
	}
	public Candidate() {
		super();
		
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
	public Candidate(long candidateId, String name, String email, String psNo, String contactNo,
			List<Users> interviewers, String assignedInterviwers) {
		super();
		this.candidateId = candidateId;
		this.name = name;
		this.email = email;
		this.psNo = psNo;
		this.contactNo = contactNo;
		this.interviewers = interviewers;
		this.assignedInterviwers = assignedInterviwers;
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
	public long getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}
}