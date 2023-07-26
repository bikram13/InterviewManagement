package com.nlpdk.interviewManagement.web.gwt.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

		// Perform login validation (you can fetch user data from the server instead)
		boolean isValidUser = validateUser(email, password);

		if (isValidUser) {
			// Simulate user data (replace this with actual user data fetched from the
			// server)
			Users user = getUserByEmail(email);

			// Call the loginHandler.onLoginSuccess method and pass the user object
			loginHandler.onLoginSuccess(user);
		} else {
			loginHandler.onLoginFailure();
		}
	}
}
