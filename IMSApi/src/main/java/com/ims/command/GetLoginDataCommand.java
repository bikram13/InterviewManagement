package com.ims.command;

import java.io.BufferedReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ims.dao.UsersDAO;
import com.ims.entity.Users;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class GetLoginDataCommand implements Command {

	@Override
	public Object doExecute(HttpServletRequest request, HttpServletResponse response) {
		// Step 1: Read JSON data from the request's input stream

		try {

			BufferedReader reader = request.getReader();
			StringBuilder jsonData = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				jsonData.append(line);
			}

			// Step 2: Parse the JSON string to JsonObject
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = jsonParser.parse(jsonData.toString()).getAsJsonObject();

			// Now you can work with the jsonObject and access its properties
			// For example, if your JSON contains "candidateId" and "candidateName"
			// properties:

			String email = jsonObject.get("email").getAsString();
			String password = jsonObject.get("password").getAsString();

			// Replace this logic with your actual user authentication implementation
			UsersDAO usersDAO = new UsersDAO();
			Users user = usersDAO.getUserByEmailAndPassword(email, password);

			// Prepare the JSON response
			Gson gson = new Gson();
			String jsonResponse;
			if (user != null) {
				// User found, return user details
				jsonResponse = gson.toJson(user);
			} else {
				// User not found, return error message
				jsonResponse = "{\"error\": \"Invalid credentials\"}";
			}

			return jsonResponse;
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"error\": \"An error occurred while processing the request\"}";
		}
	}
}
