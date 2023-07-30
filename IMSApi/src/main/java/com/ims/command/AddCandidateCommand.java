package com.ims.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.ims.dao.CandidateDAO;
import com.ims.entity.Candidate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddCandidateCommand implements Command{
	private CandidateDAO candidateDAO;

	@Override
	public Object doExecute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		candidateDAO= new CandidateDAO();
		try {
			return addCandidate(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	
	private String addCandidate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Candidate candidate = new Candidate();
				
		StringBuilder requestBody = new StringBuilder();
		try(BufferedReader reader = request.getReader()){
			String line;
			while((line = reader.readLine()) != null) {
				requestBody.append(line);
			}
		}
		
		Gson gson = new Gson();
		candidate = gson.fromJson(requestBody.toString(),Candidate.class);
		
		candidate.getContactNo();
		candidate.getFirstName();
		candidate.getEmailId();
		candidate.getLastName();
		candidate.getPsNo();
		candidateDAO.addCandidate(candidate);
		
		// Set response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Set CORS headers for preflight requests (if needed)
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

        // Set the max age for preflight requests (in seconds)
        response.setHeader("Access-Control-Max-Age", "3600");
		return "inserted";
	}
}
