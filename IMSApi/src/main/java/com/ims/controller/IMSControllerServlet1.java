package com.ims.controller;

import com.google.gson.Gson;
import com.ims.model.Candidate;
import com.ims.model.CandidateInterviewer;
import com.ims.model.Feedback;
import com.ims.model.Users;
import com.ims.service.CandidateService;
import com.ims.service.CandidateInterviewerService;
import com.ims.service.FeedbackService;
import com.ims.service.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//@WebServlet("/controller")
public class IMSControllerServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private UsersService usersService;
    private CandidateService candidateService;
    private CandidateInterviewerService candidateInterviewerService;
    private FeedbackService feedbackService;

    
    public IMSControllerServlet1() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize the service classes here
        usersService = new UsersService();
        candidateService = new CandidateService();
        candidateInterviewerService = new CandidateInterviewerService();
        feedbackService = new FeedbackService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("Get all");
        if (action != null) {
            switch (action) {
                case "getAllUsers":
                    getAllUsers(request, response);
                    break;
                case "getAllCandidateInterviewers":
                    getAllCandidateInterviewers(request, response);
                    break;
                case "getAllCandidates":
                    getAllCandidates(request, response);
                    break;
                case "getAllFeedbacks":
                    getAllFeedbacks(request, response);
                    break;
                default:
                    response.sendRedirect("index.jsp");
                    break;
            }
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    private void getAllUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Users> users = usersService.getAllUsers();

        // Convert the users list to JSON format using Gson library
        Gson gson = new Gson();
        String jsonData = gson.toJson(users);

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
        out.print("{\"users\":" + jsonData + "}"); // Wrap the JSON data in an object with key "users"
        out.flush();
    }

    private void getAllCandidateInterviewers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<CandidateInterviewer> candidateInterviewers = candidateInterviewerService.getAllCandidateInterviewers();

        // Convert the candidateInterviewers list to JSON format using Gson library
        Gson gson = new Gson();
        String jsonData = gson.toJson(candidateInterviewers);

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
        out.print("{\"candidateInterviewers\":" + jsonData + "}"); // Wrap the JSON data in an object with key "candidateInterviewers"
        out.flush();
    }

    private void getAllCandidates(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Candidate> candidates = candidateService.getAllCandidates();

        // Convert the candidates list to JSON format using Gson library
        Gson gson = new Gson();
        String jsonData = gson.toJson(candidates);

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
        out.print("{\"candidates\":" + jsonData + "}"); // Wrap the JSON data in an object with key "candidates"
        out.flush();
    }

    private void getAllFeedbacks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();

        // Convert the feedbacks list to JSON format using Gson library
        Gson gson = new Gson();
        String jsonData = gson.toJson(feedbacks);

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
        out.print("{\"feedbacks\":" + jsonData + "}"); // Wrap the JSON data in an object with key "feedbacks"
        out.flush();
    }

    // ...
}
