package com.ims.model;

public class InterviewerResponseModel {
    private long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String psNo;
    private String role;
    private String contactNo;

    public InterviewerResponseModel(long id, String firstName, String lastName, String emailId, String psNo, String role, String contactNo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.psNo = psNo;
        this.role = role;
        this.contactNo = contactNo;
    }
}