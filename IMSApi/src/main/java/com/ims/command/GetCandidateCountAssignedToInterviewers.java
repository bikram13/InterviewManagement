package com.ims.command;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ims.dao.CandidateInterviewerDAO;
import com.ims.model.InterviewerCandidateCount;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetCandidateCountAssignedToInterviewers implements Command{
	
		private CandidateInterviewerDAO candidateInterviewerDAO;

		@Override
		public Object doExecute(HttpServletRequest request, HttpServletResponse response) {
			// TODO Auto-generated method stub
			candidateInterviewerDAO = new CandidateInterviewerDAO();
			
//		 Type collectionType = new TypeToken<Collection<InterviewerCandidateCount>>(){}.getType();
//		 Gson gson = new Gson();
//		 System.out.println(getCandidateCountAssignedToInterviewers().toString());
//		 List<InterviewerCandidateCount> interviewerCountList = gson.fromJson(getCandidateCountAssignedToInterviewers().toString(), collectionType);
//		 for (InterviewerCandidateCount interviewerCandidateCount : interviewerCountList) {
//			System.out.println(interviewerCandidateCount.toString());
//		}
//		 
			Gson gson = new Gson();
			String jsonData = "{\"interviewers\":" + gson.toJson(getCandidateCountAssignedToInterviewers()) + "}";
			return jsonData;
		}
		
		private Object getCandidateCountAssignedToInterviewers() {
			return candidateInterviewerDAO.getCandidateCountAssignedToInterviewers();
			
		}
}
