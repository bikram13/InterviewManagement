package com.nlpdk.interviewManagement.web.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class InterviewManagament implements EntryPoint, LoginHandler {
	private Label welcomeLabel;
	Button logoutButton;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		String sessionData = Storage.getLocalStorageIfSupported().getItem("sessionData");

		if (sessionData != null) {
			GWT.log("storage" + sessionData);
			Users user = parseUserJSON(sessionData);
			onLoginSuccess(user);
		} else {
			showLoginView();
		}

//		showLoginView();
	}

	private void showLoginView() {

		LoginView loginView = new LoginView(this);
		RootPanel.get().add(loginView);
	}

	@Override
	public void onLoginSuccess(Users user) {

		setWelcomeAndLogout(user);

		saveSessionData(user);

		if (user != null) {
			if ("assigner".equalsIgnoreCase(user.getRole())) {
				showAssignerView(user);
			} else if ("interviewer".equalsIgnoreCase(user.getRole())) {
				showInterviewerView(user);
			}
		}
	}

	private void setWelcomeAndLogout(Users user) {

		HorizontalPanel welcomePanel = new HorizontalPanel();

		welcomeLabel = new Label();

		welcomeLabel.setText("Welcome " + user.getFirstName() + " " + user.getLastName() + ", Role :" + user.getRole());

		logoutButton = new Button("Logout");
		logoutButton.addClickHandler(event -> handleLogout());
		logoutButton.setStyleName("logout");

		Label spaceLabel = new Label(" ");

		welcomePanel.add(welcomeLabel);
		welcomePanel.add(spaceLabel);
		welcomePanel.add(logoutButton);
		// Add the wrapper panel to the root panel
		welcomePanel.setStyleName("welcome-panel");
		RootPanel.get("welcome-message").add(welcomeLabel);
		RootPanel.get("logout").add(logoutButton);
	}

	@Override
	public void onLoginFailure() {

		Window.alert("Please enter correct username or password");
	}

	private void showAssignerView(Users user) {

		CandidateListPage candidateListPage = new CandidateListPage();
		RootPanel.get().clear();
		RootPanel.get().add(candidateListPage);
	}

	private void showInterviewerView(Users user) {

		InterviewerCandidateListPage interviewerCandidateListPage = new InterviewerCandidateListPage(user);
		RootPanel.get().clear();
		RootPanel.get().add(interviewerCandidateListPage);
	}

	private Users parseUserJSON(String sessionData) {
		GWT.log("Parse");
		JSONObject jsonObject = JSONParser.parseStrict(sessionData).isObject();
		int id = (int) jsonObject.get("id").isNumber().doubleValue();
		String firstName = jsonObject.get("firstName").isString().stringValue();
		String lastName = jsonObject.get("lastName").isString().stringValue();
		String email = jsonObject.get("emailId").isString().stringValue();
		String psNo = jsonObject.get("psNo").isString().stringValue();
		String role = jsonObject.get("role").isString().stringValue();
		String contactNo = jsonObject.get("contactNo").isString().stringValue();
		String password = jsonObject.get("password").isString().stringValue();

		GWT.log("Parse over");

		return new Users(id, firstName, lastName, email, psNo, role, contactNo, password);
	}

	private void saveSessionData(Users user) {
		JSONObject sessionDataObject = new JSONObject();
		sessionDataObject.put("id", new JSONNumber(user.getId()));
		sessionDataObject.put("firstName", new JSONString(user.getFirstName()));
		sessionDataObject.put("lastName", new JSONString(user.getLastName()));
		sessionDataObject.put("emailId", new JSONString(user.getEmail()));
		sessionDataObject.put("psNo", new JSONString(user.getPsNo()));
		sessionDataObject.put("role", new JSONString(user.getRole()));
		sessionDataObject.put("contactNo", new JSONString(user.getContactNo()));
		sessionDataObject.put("password", new JSONString(user.getPassword()));

		String sessionData = sessionDataObject.toString();
		Storage.getLocalStorageIfSupported().setItem("sessionData", sessionData);
	}

	private void clearSessionData() {
		// Clear the session data from LocalStorage
		Storage.getLocalStorageIfSupported().removeItem("sessionData");
		RootPanel.get("welcome-message").clear();
		RootPanel.get().clear();
	}

	private void handleLogout() {
		// Clear the session data from LocalStorage
		clearSessionData();

		// Show the login view again
		showLoginView();
	}

}
