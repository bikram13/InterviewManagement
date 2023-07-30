package com.ims.entity;
import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "Candidate")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_sequence")
    @SequenceGenerator(name = "candidate_sequence", sequenceName = "CANDIDATE_SEQ", allocationSize = 1)
    @Column(name = "Id")
    private Long id;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "EmailId")
    private String emailId;

    @Column(name = "PSNo")
    private String psNo;

    @Column(name = "ContactNo")
    private String contactNo;

    @Lob
    @Column(name = "Profile")
    private byte[] profile;

    @Column(name = "CreationTimestamp", insertable = false, updatable = false)
    private Timestamp creationTimestamp;

    @Column(name = "LastUpdatedTimestamp", insertable = false, updatable = false)
    private Timestamp lastUpdatedTimestamp;

    
    
	

	public Candidate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Candidate(Long id, String firstName, String lastName, String emailId, String psNo, String contactNo,
			byte[] profile, Timestamp creationTimestamp, Timestamp lastUpdatedTimestamp) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.psNo = psNo;
		this.contactNo = contactNo;
		this.profile = profile;
		this.creationTimestamp = creationTimestamp;
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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

	public byte[] getProfile() {
		return profile;
	}

	public void setProfile(byte[] profile) {
		this.profile = profile;
	}

	public Timestamp getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Timestamp creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public Timestamp getLastUpdatedTimestamp() {
		return lastUpdatedTimestamp;
	}

	public void setLastUpdatedTimestamp(Timestamp lastUpdatedTimestamp) {
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

    
    

}
