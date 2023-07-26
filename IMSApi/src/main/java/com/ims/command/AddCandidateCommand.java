package com.ims.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddCandidateCommand implements Command{

	@Override
	public Object doExecute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	 return "json";
	
	}

}
