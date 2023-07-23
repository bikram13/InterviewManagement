package com.nlpdk.interviewManagement.web.gwt.model;
public class CandidateData {
    private String firstName;
    private String lastName;
    private String email;
    private String psNo;
    private String contactNo;
    private String profileFileName;
    private byte[] profileFileData; // You can use byte array to hold the uploaded file data
	public CandidateData(String firstName, String lastName, String email, String psNo, String contactNo,
			String profileFileName, byte[] profileFileData) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.psNo = psNo;
		this.contactNo = contactNo;
		this.profileFileName = profileFileName;
		this.profileFileData = profileFileData;
	}
	public CandidateData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public String getProfileFileName() {
		return profileFileName;
	}
	public void setProfileFileName(String profileFileName) {
		this.profileFileName = profileFileName;
	}
	public byte[] getProfileFileData() {
		return profileFileData;
	}
	public void setProfileFileData(byte[] profileFileData) {
		this.profileFileData = profileFileData;
	}

    
}
