package com.ims.dto;

import com.ims.entity.Candidate;

public class CandidateDTO   {
 private String assignedTo;
 private Candidate candidate;
 
public String getAssignedTo() {
	return assignedTo;
}

public void setAssignedTo(String assignedTo) {
	this.assignedTo = assignedTo;
}

public Candidate getCandidate() {
	return candidate;
}

public void setCandidate(Candidate candidate) {
	this.candidate = candidate;
}
 
}
