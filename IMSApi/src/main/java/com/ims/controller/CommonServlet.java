package com.ims.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.ims.command.Command;
import com.ims.helper.RequestHelper;
import com.ims.util.ReportGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CommonServlet
 */
@WebServlet("/api")
public class CommonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ReportGenerator reportGenerator;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommonServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		super.init();
		reportGenerator = new ReportGenerator();
		// Schedule the report generation at 5 am daily
		reportGenerator.scheduleReportGeneration();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		String result;
		try {
			// Use a helper object to gather parameter
			// specific information.
			RequestHelper helper = new RequestHelper(request);

			Command command = helper.getCommand();
			// Command helper perform custom operation
			result = (String) command.doExecute(request, response);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			// Set CORS headers for preflight requests (if needed)
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "Content-Type, *");

			// Set the max age for preflight requests (in seconds)
			response.setHeader("Access-Control-Max-Age", "3600");
			out.print(result);
			out.flush();
		} catch (Exception e) {
			// Add logs here
		}
	}

}
