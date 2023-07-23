package com.ims.service;

import com.ims.dao.CandidateDAO;
import com.ims.model.Candidate;

import java.util.List;

public class CandidateService {
    private CandidateDAO candidateDAO;

    public CandidateService() {
        candidateDAO = new CandidateDAO();
    }

    public void addCandidate(Candidate candidate) {
        candidateDAO.addCandidate(candidate);
    }

    public List<Candidate> getAllCandidates() {
        return candidateDAO.getAllCandidates();
    }

    public Candidate getCandidateById(long candidateId) {
        return candidateDAO.getCandidateById(candidateId);
    }
}
