package com.ims.command;

import java.util.List;

import com.google.gson.Gson;
import com.ims.dao.CandidateDAO;
import com.ims.entity.Candidate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetAllCandidateCommand implements Command {

	private CandidateDAO candidateDAO;

	@Override
	public Object doExecute(HttpServletRequest request, HttpServletResponse response) {
		candidateDAO= new CandidateDAO();
		return getAllCandidates(request, response);
	}

	private String getAllCandidates(HttpServletRequest request, HttpServletResponse response) {
		List<Candidate> candidates = candidateDAO.getAllCandidates();
		// Convert the candidates list to JSON format using Gson library
		Gson gson = new Gson();
		String jsonData = "{\"candidates\":" + gson.toJson(candidates) + "}";
		return jsonData;
	}

}
