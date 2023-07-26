package com.ims.service.impl;

import com.ims.dao.CandidateInterviewerDAO;
import com.ims.entity.CandidateInterviewer;

import java.util.List;

public class CandidateInterviewerServiceImpl {
    private CandidateInterviewerDAO candidateInterviewerDAO;

    public CandidateInterviewerServiceImpl() {
        candidateInterviewerDAO = new CandidateInterviewerDAO();
    }

    public void addCandidateInterviewer(CandidateInterviewer candidateInterviewer) {
        candidateInterviewerDAO.addCandidateInterviewer(candidateInterviewer);
    }

    public List<CandidateInterviewer> getAllCandidateInterviewers() {
        return candidateInterviewerDAO.getAllCandidateInterviewers();
    }

    // Implement other methods as needed

    // ...
}
