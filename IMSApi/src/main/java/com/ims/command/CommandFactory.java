package com.ims.command;

public class CommandFactory {
	public Command getCommand(String action) {

		if (action != null) {
			switch (action) {
			case "getIinterviewerCandidateList":
				return new InterviewerCandidateListCommand();
			case "getAllCandidates":
				return new GetAllCandidateCommand();

			case "getFeedbackData":
				return new GetFeedbackDataCommand();

			case "login":
				return new GetLoginDataCommand();

			default:
//	                    response.sendRedirect("index.jsp");
				return null;
			}
		}
		return null;
	}
}