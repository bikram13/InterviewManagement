package com.ims.dto;

import java.util.List;

public class CandidateInterviewerDTO {

	private List<CandidateDTO> candidateList;

	public CandidateInterviewerDTO() {
		super();
	}

	public CandidateInterviewerDTO(List<CandidateDTO> candidateList) {
		super();
		this.candidateList = candidateList;
	}

	public List<CandidateDTO> getCandidateList() {
		return candidateList;
	}

	public void setCandidateList(List<CandidateDTO> candidateList) {
		this.candidateList = candidateList;
	}	
}
