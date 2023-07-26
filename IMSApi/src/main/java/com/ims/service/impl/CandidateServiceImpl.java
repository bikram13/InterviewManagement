package com.ims.service.impl;

import com.ims.dao.CandidateDAO;
import com.ims.entity.Candidate;

import java.util.List;

public class CandidateServiceImpl {
    private CandidateDAO candidateDAO;

    public CandidateServiceImpl() {
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
