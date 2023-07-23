package com.ims.model;
import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "USERS")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_sequence")
    @SequenceGenerator(name = "users_sequence", sequenceName = "USERS_SEQ", allocationSize = 1)
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

    @Column(name = "Role")
    private String role;

    @Column(name = "contactNo")
    private String contactNo;

    @Column(name = "Password")
    private String password;

    @Column(name = "CreationTimestamp", insertable = false, updatable = false)
    private Timestamp creationTimestamp;

    @Column(name = "LastUpdatedTimestamp", insertable = false, updatable = false)
    private Timestamp lastUpdatedTimestamp;

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Users(Long id, String firstName, String lastName, String emailId, String psNo, String role, String contactNo,
			String password, Timestamp creationTimestamp, Timestamp lastUpdatedTimestamp) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.psNo = psNo;
		this.role = role;
		this.contactNo = contactNo;
		this.password = password;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
