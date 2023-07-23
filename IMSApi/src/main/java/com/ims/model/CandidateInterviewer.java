package com.ims.model;
import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "Candidate_Interviewer")
public class CandidateInterviewer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_interviewer_sequence")
    @SequenceGenerator(name = "candidate_interviewer_sequence", sequenceName = "CANDIDATE_INTERVIEWER_SEQ", allocationSize = 1)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Feedback_Status", columnDefinition = "varchar(50) default 'NOT_SUBMITTED'")
    private String feedbackStatus;

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "Id")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "Interviewer_User_id", referencedColumnName = "Id")
    private Users interviewer;

    @Column(name = "CreationTimestamp", insertable = false, updatable = false)
    private Timestamp creationTimestamp;

    @Column(name = "LastUpdatedTimestamp", insertable = false, updatable = false)
    private Timestamp lastUpdatedTimestamp;

	public CandidateInterviewer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CandidateInterviewer(Long id, String feedbackStatus, Candidate candidate, Users interviewer,
			Timestamp creationTimestamp, Timestamp lastUpdatedTimestamp) {
		super();
		this.id = id;
		this.feedbackStatus = feedbackStatus;
		this.candidate = candidate;
		this.interviewer = interviewer;
		this.creationTimestamp = creationTimestamp;
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFeedbackStatus() {
		return feedbackStatus;
	}

	public void setFeedbackStatus(String feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public Users getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(Users interviewer) {
		this.interviewer = interviewer;
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
