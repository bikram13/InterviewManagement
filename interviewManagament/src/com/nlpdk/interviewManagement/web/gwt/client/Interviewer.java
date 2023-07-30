package com.nlpdk.interviewManagement.web.gwt.client;
public class Interviewer {
	private long id;
	private String name;
	private long numCandidatesAssigned;
	public Interviewer(long id, String name, long numCandidatesAssigned) {
		this.id = id;
		this.name = name;
		this.numCandidatesAssigned = numCandidatesAssigned;
	}
	public String getName() {
		return name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getNumCandidatesAssigned() {
		return numCandidatesAssigned;
	}
}
