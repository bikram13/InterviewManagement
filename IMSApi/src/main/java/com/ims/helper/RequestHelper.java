package com.ims.helper;

import com.ims.command.Command;
import com.ims.command.CommandFactory;

import jakarta.servlet.http.HttpServletRequest;

public class RequestHelper {
	HttpServletRequest request;

	public RequestHelper(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public Command getCommand() {
		String strAction = request.getParameter("action");
		CommandFactory commandFactory = new CommandFactory();
		return commandFactory.getCommand(strAction);
	}

}
