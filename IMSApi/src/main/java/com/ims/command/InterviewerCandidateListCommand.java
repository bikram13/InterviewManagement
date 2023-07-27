package com.ims.command;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.ims.dao.CandidateInterviewerDAO;
import com.ims.dao.UsersDAO;
import com.ims.entity.Candidate;
import com.ims.entity.CandidateInterviewer;
import com.ims.entity.Users;
import com.ims.model.InterviewerCandidateResponseModel;
import com.ims.model.InterviewerResponseModel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class InterviewerCandidateListCommand implements Command {

	private CandidateInterviewerDAO candidateInterviewerDAO;
	private UsersDAO usersDAO;

	@Override
	public Object doExecute(HttpServletRequest request, HttpServletResponse response) {
		candidateInterviewerDAO = new CandidateInterviewerDAO();
		usersDAO = new UsersDAO();
		return getInterviewerCandidateList(request);
	}

	private String getInterviewerCandidateList(HttpServletRequest request) {
		long interviewerId = Long.parseLong(request.getParameter("interviewerId"));
		List<CandidateInterviewer> candidateInterviewers = candidateInterviewerDAO
				.getAllCandidateInterviewersByInterviewerId(interviewerId);
		List<InterviewerCandidateResponseModel> responseModels = new ArrayList<>();

		for (CandidateInterviewer ci : candidateInterviewers) {
			if ("NOT_SUBMITTED".equalsIgnoreCase(ci.getFeedbackStatus())) {
				Candidate candidate = ci.getCandidate();
				List<Users> interviewers = usersDAO.getInterviewersByCandidateId(candidate.getId());
				InterviewerCandidateResponseModel responseModel = new InterviewerCandidateResponseModel(
						candidate.getId(), candidate.getFirstName() + " " + candidate.getLastName(),
						candidate.getEmailId(), candidate.getPsNo(), candidate.getContactNo(),
						mapToInterviewerList(interviewers));
				responseModels.add(responseModel);
			}
		}

		// Convert the responseModels list to JSON format using Gson library
		Gson gson = new Gson();
		String jsonData = gson.toJson(responseModels);
		return jsonData;
	}

	private List<InterviewerResponseModel> mapToInterviewerList(List<Users> interviewers) {
		List<InterviewerResponseModel> interviewerList = new ArrayList<>();
		for (Users user : interviewers) {
			InterviewerResponseModel interviewer = new InterviewerResponseModel(user.getId(), user.getFirstName(),
					user.getLastName(), user.getEmailId(), user.getPsNo(), user.getRole(), user.getContactNo());
			interviewerList.add(interviewer);
		}
		return interviewerList;
	}
}
