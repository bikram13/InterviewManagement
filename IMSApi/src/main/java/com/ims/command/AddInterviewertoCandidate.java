package com.ims.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.ims.dao.CandidateDAO;
import com.ims.dao.CandidateInterviewerDAO;
import com.ims.dao.UsersDAO;
import com.ims.entity.Candidate;
import com.ims.entity.CandidateInterviewer;
import com.ims.model.CandidateInterviewerModel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddInterviewertoCandidate implements Command{
	
	private CandidateInterviewerDAO candidateInterviewerDAO;
	
	private CandidateDAO candidateDAO;
	
	private UsersDAO usersDAO;

	@Override
	public Object doExecute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		candidateInterviewerDAO = new CandidateInterviewerDAO();
		candidateDAO = new CandidateDAO();
		usersDAO = new UsersDAO();
		
		addInterviewertoCandidate(request);
		return null;
	
	}
	
	private void addInterviewertoCandidate(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
	    try(BufferedReader reader = request.getReader()) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line).append('\n');
	        }
	    } 
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println(sb.toString());
	    
	    Gson gson = new Gson();
	    CandidateInterviewerModel candidateInterviewerModel 
	    = gson.fromJson(sb.toString(), CandidateInterviewerModel.class);
	    
	    List<Long> interviewersIntegers = candidateInterviewerModel.getInterviewerIds();
	    Candidate candidate = candidateDAO.getCandidateById(candidateInterviewerModel.getCandidateId());
	    for (Long interviewerId : interviewersIntegers) {
	    	CandidateInterviewer candidateInterviewer = new CandidateInterviewer();
	    	
	    	candidateInterviewer.setCandidate(candidate);
	    	candidateInterviewer.setInterviewer(usersDAO.getUserById(interviewerId));
	    	candidateInterviewer.setFeedbackStatus("NOT_SUBMITTED");
	    	
			candidateInterviewerDAO.addCandidateInterviewer(candidateInterviewer);
		}
		
		
	}
}
