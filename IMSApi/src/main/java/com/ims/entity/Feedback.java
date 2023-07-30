package com.ims.entity;
import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "FEEDBACK")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_sequence")
    @SequenceGenerator(name = "feedback_sequence", sequenceName = "FEEDBACK_SEQ", allocationSize = 1)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Candidate_Interviewer_Id", referencedColumnName = "Id")
    private CandidateInterviewer candidateInterviewer;

    @Column(name = "TechFeedback", columnDefinition = "varchar(500)")
    private String techFeedback;

    @Column(name = "DomainFeedback", columnDefinition = "varchar(500)")
    private String domainFeedback;

    @Column(name = "CommSkillsFeedback", columnDefinition = "varchar(500)")
    private String commSkillsFeedback;

    @Column(name = "finalResult", columnDefinition = "varchar(20)")
    private String finalResult;

    @Column(name = "CreationTimestamp", insertable = false, updatable = false)
    private Timestamp creationTimestamp;

    @Column(name = "LastUpdatedTimestamp", insertable = false, updatable = false)
    private Timestamp lastUpdatedTimestamp;

	public Feedback() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Feedback(Long id, CandidateInterviewer candidateInterviewer, String techFeedback, String domainFeedback,
			String commSkillsFeedback, String finalResult, Timestamp creationTimestamp,
			Timestamp lastUpdatedTimestamp) {
		super();
		this.id = id;
		this.candidateInterviewer = candidateInterviewer;
		this.techFeedback = techFeedback;
		this.domainFeedback = domainFeedback;
		this.commSkillsFeedback = commSkillsFeedback;
		this.finalResult = finalResult;
		this.creationTimestamp = creationTimestamp;
		this.lastUpdatedTimestamp = lastUpdatedTimestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CandidateInterviewer getCandidateInterviewer() {
		return candidateInterviewer;
	}

	public void setCandidateInterviewer(CandidateInterviewer candidateInterviewer) {
		this.candidateInterviewer = candidateInterviewer;
	}

	public String getTechFeedback() {
		return techFeedback;
	}

	public void setTechFeedback(String techFeedback) {
		this.techFeedback = techFeedback;
	}

	public String getDomainFeedback() {
		return domainFeedback;
	}

	public void setDomainFeedback(String domainFeedback) {
		this.domainFeedback = domainFeedback;
	}

	public String getCommSkillsFeedback() {
		return commSkillsFeedback;
	}

	public void setCommSkillsFeedback(String commSkillsFeedback) {
		this.commSkillsFeedback = commSkillsFeedback;
	}

	public String getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(String finalResult) {
		this.finalResult = finalResult;
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
