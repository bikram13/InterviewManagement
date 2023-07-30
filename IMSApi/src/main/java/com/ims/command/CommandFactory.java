package com.ims.command;

import net.bytebuddy.asm.Advice.Return;

public class CommandFactory {
	public Command getCommand(String action) {

		if (action != null) {
			switch (action) {
			case "getIinterviewerCandidateList":
				return new InterviewerCandidateListCommand();
			case "getAllCandidates":
				return new GetAllCandidateCommand();
			case "addCandidate":
				return new AddCandidateCommand();
			case "getFeedbackData":
				return new GetFeedbackDataCommand();

			case "login":
				return new GetLoginDataCommand();
			case "submitFeedback":
				return new SubmitFeedbackCommand();
			case "getCandidateCountAssignedToInterviewers":
                return new GetCandidateCountAssignedToInterviewers();
            case "addInterviewertoCandidate":
                return new AddInterviewertoCandidate();
               // getAllCandidatesForAssigner new added
            case "getAllCandidatesForAssigner":
				return new GetAllCandidatesForAssigner();
			default:
//	                    response.sendRedirect("index.jsp");
				return null;
			}
		}
		return null;
	}
}