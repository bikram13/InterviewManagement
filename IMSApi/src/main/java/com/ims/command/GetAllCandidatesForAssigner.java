package com.ims.command;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.ims.dao.CandidateDAO;
import com.ims.dao.CandidateInterviewerDAO;
import com.ims.dto.CandidateDTO;
import com.ims.dto.CandidateInterviewerDTO;
import com.ims.entity.Candidate;
import com.ims.entity.Users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetAllCandidatesForAssigner implements Command {
	
	private CandidateInterviewerDAO candidateInterviewerDAO;
    private CandidateDAO candidateDAO;
	@Override
	public Object doExecute(HttpServletRequest request, HttpServletResponse response) {
		candidateInterviewerDAO = new CandidateInterviewerDAO();
		candidateDAO = new CandidateDAO();
		return getAllCandidates(request, response);

	}

	private String getAllCandidates(HttpServletRequest request, HttpServletResponse response) {
		String strJSON = null;
		 try {
			 List<Candidate> candidateListAll = candidateDAO.getAllCandidates();
				Gson gson = new Gson();
				CandidateInterviewerDTO candInterDTO = new CandidateInterviewerDTO();
				List<CandidateDTO> listOfCandidates = new ArrayList<>();
				CandidateDTO candidateDTO = null;
				if (candidateListAll != null && candidateListAll.size() > 0) {
					for (Candidate candidate : candidateListAll) {
						String strInterCommaSep = getAssignedInterviewers(candidate.getId());
						candidateDTO = new CandidateDTO();
						candidateDTO.setCandidate(candidate);
						candidateDTO.setAssignedTo(strInterCommaSep);
						listOfCandidates.add(candidateDTO);
					}
					candInterDTO.setCandidateList(listOfCandidates);
			 		strJSON = gson.toJson(candInterDTO);
				}
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		
		return strJSON;

	}

	public String getAssignedInterviewers(Long candidateId) {
		List<Users> usersList = candidateInterviewerDAO.getAssignedInterviewersToTheCandidate(candidateId);
		StringBuilder strBuider = new StringBuilder();
		for (Users user : usersList) {
			strBuider.append(user.getFirstName()).append(" ").append(user.getLastName()).append(", ");
		
		}
		Integer lastCommaIndex = strBuider.lastIndexOf(",");
		if(lastCommaIndex>0) {
			strBuider.replace(lastCommaIndex, lastCommaIndex + 1, "");
		}
		return strBuider.toString();

	}

}
