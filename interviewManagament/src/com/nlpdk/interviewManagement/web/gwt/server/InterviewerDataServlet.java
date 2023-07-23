package com.nlpdk.interviewManagement.web.gwt.server;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.nlpdk.interviewManagement.web.gwt.client.Interviewer;

public class InterviewerDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Interviewer> interviewerList = getInterviewerDataFromDatabase(); // Replace this with your logic to fetch data from the database

        // Convert the interviewer list to JSON format using Gson library
        Gson gson = new Gson();
        String jsonData = gson.toJson(interviewerList);

        // Set response content type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Write the JSON data to the response
        PrintWriter out = response.getWriter();
        out.print(jsonData);
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
