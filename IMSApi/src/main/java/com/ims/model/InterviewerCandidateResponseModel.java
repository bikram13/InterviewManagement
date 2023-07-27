package com.ims.model;

import java.util.List;

 public class InterviewerCandidateResponseModel {
    private long candidateId;
    private String name;
    private String email;
    private String psNo;
    private String contactNo;
    private List<InterviewerResponseModel> interviewers;

    public InterviewerCandidateResponseModel(long candidateId, String name, String email, String psNo, String contactNo, List<InterviewerResponseModel> interviewers) {
        this.candidateId = candidateId;
        this.name = name;
        this.email = email;
        this.psNo = psNo;
        this.contactNo = contactNo;
        this.interviewers = interviewers;
    }
}