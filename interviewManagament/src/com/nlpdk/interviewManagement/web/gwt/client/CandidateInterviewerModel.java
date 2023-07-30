package com.nlpdk.interviewManagement.web.gwt.client;
import java.util.List;
public class CandidateInterviewerModel {
	private Long candidateId;
	private List<Long> interviewerIds;
	public Long getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}
	public CandidateInterviewerModel(Long candidateId, List<Long> interviewerIds) {
		super();
		this.candidateId = candidateId;
		this.interviewerIds = interviewerIds;
	}
	@Override
	public String toString() {
		return "CandidateInterviewerModel [candidateId=" + candidateId + ", interviewerIds=" + interviewerIds + "]";
	}
	public List<Long> getInterviewerIds() {
		return interviewerIds;
	}
	public void setInterviewerIds(List<Long> interviewerIds) {
		this.interviewerIds = interviewerIds;
	}
}
