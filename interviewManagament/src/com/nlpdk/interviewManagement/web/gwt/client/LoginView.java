package com.nlpdk.interviewManagement.web.gwt.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginView extends Composite {

	private TextBox emailTextBox;
	private PasswordTextBox passwordTextBox;
	private Button loginButton;

	private LoginHandler loginHandler; // Use the LoginHandler from the new file

	public LoginView(LoginHandler loginHandler) {
		this.loginHandler = loginHandler; // Assign the loginHandler

		// Create UI components for the login view
		Label emailLabel = new Label("Email:");
		emailTextBox = new TextBox();
		Label passwordLabel = new Label("Password:");
		passwordTextBox = new PasswordTextBox();
		loginButton = new Button("Login");

		// Layout for the login view
		VerticalPanel loginPanel = new VerticalPanel();
		loginPanel.add(new HTML("<h2>Login</h2>"));
		loginPanel.add(emailLabel);
		loginPanel.add(emailTextBox);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordTextBox);
		loginPanel.add(loginButton);

		// Set the main widget of the composite to the loginPanel
		initWidget(loginPanel);

		loginButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doLogin();
			}
		});

	}

	private boolean validateUser(String email, String password) {
		// Sample method to validate user (replace with actual user validation logic)
		// For demonstration purposes, we use a simple list of users.
		List<Users> usersList = getUsersList();
		for (Users user : usersList) {
			if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return true;
			}
		}
		return false;
	}

	private Users getUserByEmail(String email) {
		// Sample method to fetch user by email (replace with actual user retrieval
		// logic)
		// For demonstration purposes, we use a simple list of users.
		List<Users> usersList = getUsersList();
		for (Users user : usersList) {
			if (user.getEmail().equals(email)) {
				return user;
			}
		}
		return null;
	}

	private List<Users> getUsersList() {
		// Sample method to return a list of users (replace with actual user data
		// retrieval logic)
		List<Users> usersList = new ArrayList<>();
		usersList.add(new Users(1, "John", "Doe", "assigner", "PS001", "assigner", "1234567890", "assigner"));
		usersList
				.add(new Users(2, "Jane", "Smith", "interviewer", "PS002", "interviewer", "9876543210", "interviewer"));
		return usersList;
	}

	private void doLogin() {
		String email = emailTextBox.getValue();
		String password = passwordTextBox.getValue();

		// Prepare a JSON object with email and password
		JSONObject loginDataObject = new JSONObject();
		loginDataObject.put("email", new JSONString(email));
		loginDataObject.put("password", new JSONString(password));
		String loginData = loginDataObject.toString();

		// Create the RequestBuilder for the login request
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
				"http://localhost:8080/IMSApi/api?action=login");

		try {
			// Send the request
//			builder.setHeader("Content-Type", "application/json");
			builder.sendRequest(loginData, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					// Check if the request was successful (status code 200)
					if (response.getStatusCode() == 200 && !response.getText().contains("Invalid")) {
						// Handle the response
						Users user = parseUserJSON(response.getText());
						loginHandler.onLoginSuccess(user);
					} else {
						// Handle the error
						loginHandler.onLoginFailure();
					}
				}

				@Override
				public void onError(Request request, Throwable throwable) {
					// Handle the error
					loginHandler.onLoginFailure();
				}
			});
		} catch (RequestException e) {
			// Handle the exception
			loginHandler.onLoginFailure();
		}
	}

	private Users parseUserJSON(String responseText) {
		// Assuming the responseText is in JSON format
		JSONObject jsonObject = JSONParser.parseStrict(responseText).isObject();
		int id = (int) jsonObject.get("id").isNumber().doubleValue();
		String firstName = jsonObject.get("firstName").isString().stringValue();
		String lastName = jsonObject.get("lastName").isString().stringValue();
		String email = jsonObject.get("emailId").isString().stringValue();
		String psNo = jsonObject.get("psNo").isString().stringValue();
		String role = jsonObject.get("role").isString().stringValue();
		String contactNo = jsonObject.get("contactNo").isString().stringValue();
		String password = jsonObject.get("password").isString().stringValue();

		return new Users(id, firstName, lastName, email, psNo, role, contactNo, password);
	}

}
