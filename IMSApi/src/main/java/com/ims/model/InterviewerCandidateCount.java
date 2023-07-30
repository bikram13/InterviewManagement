package com.ims.model;

public class InterviewerCandidateCount {
	private Long interviewerId;
    private String interviewerName;
    private Long candidateAssignedCount;

    // Constructor
    public InterviewerCandidateCount(Long interviewerId, String interviewerName, Long candidateAssignedCount) {
        this.interviewerId = interviewerId;
    	this.interviewerName = interviewerName;
        this.candidateAssignedCount = candidateAssignedCount;
    }

    // Builder method
    public static class InterviewerCandidateCountBuilder {
    	private Long interviewerId;
        private String interviewerName;
        private Long candidateAssignedCount;
        
        public InterviewerCandidateCountBuilder setInterviewerId(Long interviewerId) {
        	this.interviewerId = interviewerId;
            return this;
        }

        public InterviewerCandidateCountBuilder setInterviewerName(String interviewerName) {
            this.interviewerName = interviewerName;
            return this;
        }

        public InterviewerCandidateCountBuilder setCandidateAssignedCount(Long candidateAssignedCount) {
            this.candidateAssignedCount = candidateAssignedCount;
            return this;
        }

        public InterviewerCandidateCount build() {
            return new InterviewerCandidateCount(interviewerId, interviewerName, candidateAssignedCount);
        }
    }

    // Getter and Setter methods
    public String getInterviewerName() {
        return interviewerName;
    }

    public void setInterviewerName(String interviewerName) {
        this.interviewerName = interviewerName;
    }

    public Long getCandidateAssignedCount() {
        return candidateAssignedCount;
    }

    public Long getInterviewerId() {
		return interviewerId;
	}

	public void setInterviewerId(Long interviewerId) {
		this.interviewerId = interviewerId;
	}

	public void setCandidateAssignedCount(Long candidateAssignedCount) {
        this.candidateAssignedCount = candidateAssignedCount;
    }

	@Override
	public String toString() {
		return "InterviewerCandidateCount [interviewerId=" + interviewerId + ", interviewerName=" + interviewerName
				+ ", candidateAssignedCount=" + candidateAssignedCount + "]";
	}

    
}


