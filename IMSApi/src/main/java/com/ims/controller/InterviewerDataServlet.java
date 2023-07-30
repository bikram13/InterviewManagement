package com.ims.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;


/**
 * Servlet implementation class InterviewerDataServlet
 */
public class InterviewerDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InterviewerDataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Interviewer> interviewerList = getInterviewerDataFromDatabase();

	    // Convert the interviewer list to JSON format using Gson library
	    Gson gson = new Gson();
	    String jsonData = gson.toJson(interviewerList);

	    // Set response content type to JSON
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    
	    // Set CORS headers for preflight requests (if needed)
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");

	    // Set the max age for preflight requests (in seconds)
	    response.setHeader("Access-Control-Max-Age", "3600");

	    // Write the JSON data to the response
	    PrintWriter out = response.getWriter();
	    out.print("{\"interviewers\":" + jsonData + "}"); // Wrap the JSON data in an object with key "interviewers"
	    out.flush();
        
	}
	
	
	private List<Interviewer> getInterviewerDataFromDatabase() {
        // Replace this with your logic to fetch data from the database or any other data source
        // For demonstration purposes, we'll use some sample data
        List<Interviewer> interviewerList = new ArrayList<>();
        interviewerList.add(new Interviewer("Interviewer 1", 0));
        interviewerList.add(new Interviewer("Interviewer 2", 0));
        interviewerList.add(new Interviewer("Interviewer 3", 0));
        return interviewerList;
    }

}
