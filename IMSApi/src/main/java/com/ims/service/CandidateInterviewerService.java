package com.ims.service;

import com.ims.dao.CandidateInterviewerDAO;
import com.ims.model.CandidateInterviewer;

import java.util.List;

public class CandidateInterviewerService {
    private CandidateInterviewerDAO candidateInterviewerDAO;

    public CandidateInterviewerService() {
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
