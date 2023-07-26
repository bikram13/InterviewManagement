package com.nlpdk.interviewManagement.web.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class InterviewManagament implements EntryPoint, LoginHandler {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Show the login view on module load
		showLoginView();
	}

	private void showLoginView() {
		// Create and display the login view
		LoginView loginView = new LoginView(this);
		RootPanel.get().add(loginView);
	}

	@Override
	public void onLoginSuccess(Users user) {
		// Handle login success based on the user role
		if (user != null) {
			if ("assigner".equalsIgnoreCase(user.getRole())) {
				showAssignerView(user);
			} else if ("interviewer".equalsIgnoreCase(user.getRole())) {
				showInterviewerView(user);
			}
		}
	}

	@Override
	public void onLoginFailure() {
		// Handle login failure
		// You can show an error message or perform any other action as needed
		// For now, let's just show the login view again
		Window.alert("Please enter correct username or password");
	}

	private void showAssignerView(Users user) {
		// Show the CandidateListPage for assigners
		CandidateListPage candidateListPage = new CandidateListPage();
		RootPanel.get().clear();
		RootPanel.get().add(candidateListPage);
	}

	private void showInterviewerView(Users user) {
		// Show the CandidateListPage for interviewers
		InterviewerCandidateListPage interviewerCandidateListPage = new InterviewerCandidateListPage(user);
		RootPanel.get().clear();
		RootPanel.get().add(interviewerCandidateListPage);
	}
}
