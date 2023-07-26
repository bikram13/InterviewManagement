package com.ims.command;

public class CommandFactory {
	public Command getCommand(String action) {

		if (action != null) {
			switch (action) {
			case "getAllUsers":
//	                    getAllUsers(request, response);
				break;
			case "getAllCandidates":
				return new GetAllCandidateCommand();

			case "getFeedbackData":
				return new GetFeedbackDataCommand();

			default:
//	                    response.sendRedirect("index.jsp");
				return null;
			}
		}
		return null;
	}
}