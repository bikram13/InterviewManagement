package com.nlpdk.interviewManagement.web.gwt.client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class InterviewerCandidateListPage extends Composite {

	private FlexTable candidateTable;
	private Users user;
	private int loggedInUserId;

	public InterviewerCandidateListPage(Users user) {
		this.user = user;
		loggedInUserId = user.getId();
		candidateTable = new FlexTable();
		candidateTable.setText(0, 0, "Candidate ID");
		candidateTable.setText(0, 1, "Candidate Name");
		candidateTable.setText(0, 2, "Email ID");
		candidateTable.setText(0, 3, "PS No");
		candidateTable.setText(0, 4, "Contact Number");
		candidateTable.setText(0, 5, "Interviewer Panel");
		candidateTable.setText(0, 6, "Evaluate");

		// Fetch candidate data for the current interviewer upon page load
		fetchCandidateData();

		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.add(new HTML("<h2>Interviewer Candidate List</h2>"));
		mainPanel.add(candidateTable);

		initWidget(mainPanel);
	}

	private void fetchCandidateData() {
		// Send a request to the backend to fetch candidate data for the current
		// interviewer
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
				"http://localhost:8080/IMSApi/api?action=getIinterviewerCandidateList&interviewerId=" + user.getId());

		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						// Parse the response and display candidate data in the table
						List<Candidate> candidateList = parseCandidateData(response.getText());
						GWT.log(candidateList.toString());
						populateCandidateTable(candidateList);
					} else {
						GWT.log("Error fetching candidate data: " + response.getStatusText());
					}
				}

				@Override
				public void onError(Request request, Throwable throwable) {
					GWT.log("Error fetching candidate data: " + throwable.getMessage());
				}
			});
		} catch (Exception e) {
			GWT.log("Error fetching candidate data: " + e.getMessage());
		}
	}

	private List<Candidate> parseCandidateData(String responseText) {
		try {
			List<Candidate> candidates = new ArrayList<>();
			JSONValue jsonValue = JSONParser.parseStrict(responseText);
			if (jsonValue.isArray() != null) {
				JSONArray jsonArray = jsonValue.isArray();
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONValue candidateValue = jsonArray.get(i);
					if (candidateValue.isObject() != null) {
						JSONObject candidateObject = candidateValue.isObject();

						Candidate candidate = new Candidate();
						candidate.setCandidateId((int) candidateObject.get("candidateId").isNumber().doubleValue());
						candidate.setName(candidateObject.get("name").isString().stringValue());
						candidate.setEmail(candidateObject.get("email").isString().stringValue());
						candidate.setPsNo(candidateObject.get("psNo").isString().stringValue());
						candidate.setContactNo(candidateObject.get("contactNo").isString().stringValue());

						List<Users> interviewers = new ArrayList<>();
						JSONArray interviewerArray = candidateObject.get("interviewers").isArray();
						for (int j = 0; j < interviewerArray.size(); j++) {
							JSONValue interviewerValue = interviewerArray.get(j);
							if (interviewerValue.isObject() != null) {
								JSONObject interviewerObject = interviewerValue.isObject();

								Users interviewer = new Users();
								interviewer.setId((int) interviewerObject.get("id").isNumber().doubleValue());
								interviewer.setFirstName(interviewerObject.get("firstName").isString().stringValue());
								interviewer.setLastName(interviewerObject.get("lastName").isString().stringValue());
								interviewer.setEmail(interviewerObject.get("emailId").isString().stringValue());
								interviewer.setPsNo(interviewerObject.get("psNo").isString().stringValue());
								interviewer.setRole(interviewerObject.get("role").isString().stringValue());
								interviewer.setContactNo(interviewerObject.get("contactNo").isString().stringValue());

								interviewers.add(interviewer);
							}
						}
						candidate.setInterviewers(interviewers);
						candidates.add(candidate);
					}
				}
			}
			return candidates;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	private void populateCandidateTable(List<Candidate> candidateList) {
		// Clear existing rows (excluding header row)
		int numRows = candidateTable.getRowCount();
		for (int i = numRows - 1; i > 0; i--) {
			candidateTable.removeRow(i);
		}

		// Populate the table with fetched candidate data
		for (int i = 0; i < candidateList.size(); i++) {
			Candidate candidate = candidateList.get(i);
			candidateTable.setText(i + 1, 0, String.valueOf(candidate.getCandidateId()));
			candidateTable.setText(i + 1, 1, candidate.getName());
			candidateTable.setText(i + 1, 2, candidate.getEmail());
			candidateTable.setText(i + 1, 3, candidate.getPsNo());
			candidateTable.setText(i + 1, 4, candidate.getContactNo());

			// Get and display the interviewers' names for each candidate in a
			// comma-separated string
			List<String> interviewerNames = candidate.getInterviewers().stream()
					.map(x -> x.getFirstName() + " " + x.getLastName()).collect(Collectors.toList());
			if (interviewerNames == null || interviewerNames.isEmpty()) {
				candidateTable.setText(i + 1, 5, "None");
			} else {
				candidateTable.setText(i + 1, 5, String.join(", ", interviewerNames));
			}

			// Create a button to evaluate the candidate's feedback
			Button evaluateButton = new Button("Evaluate");
			evaluateButton.addClickHandler(new EvaluateButtonClickHandler(candidate));
			candidateTable.setWidget(i + 1, 6, evaluateButton);
		}
	}

	private class EvaluateButtonClickHandler implements ClickHandler {
		private Candidate candidate;

		public EvaluateButtonClickHandler(Candidate candidate) {
			this.candidate = candidate;
		}

		@Override
		public void onClick(ClickEvent event) {
			// Show the feedback form for evaluation
			showFeedbackForm(candidate);
		}
	}

	// ...

	// ...

	private void showFeedbackForm(Candidate candidate) {
		// Create a dialog box to show the feedback form
		DialogBox feedbackDialog = new DialogBox();
		feedbackDialog.setText("Candidate Feedback");

		VerticalPanel dialogPanel = new VerticalPanel();
		dialogPanel.add(new Label("Candidate ID: " + candidate.getCandidateId()));
		dialogPanel.add(new Label("Candidate Name: " + candidate.getName()));

		TextBox techFeedbackTextBox = new TextBox();
		techFeedbackTextBox.getElement().setPropertyString("placeholder", "Technical Feedback");
		dialogPanel.add(techFeedbackTextBox);

		TextBox domainFeedbackTextBox = new TextBox();
		domainFeedbackTextBox.getElement().setPropertyString("placeholder", "Domain Feedback");
		dialogPanel.add(domainFeedbackTextBox);

		TextBox commSkillsFeedbackTextBox = new TextBox();
		commSkillsFeedbackTextBox.getElement().setPropertyString("placeholder", "Communication Skills Feedback");
		dialogPanel.add(commSkillsFeedbackTextBox);

		ListBox finalResultListBox = new ListBox();
		finalResultListBox.addItem("Final Recommendation");
		finalResultListBox.addItem("SELECTED");
		finalResultListBox.addItem("REJECTED");
		finalResultListBox.addItem("ON HOLD");
		dialogPanel.add(new Label("Final Recommendation"));
		dialogPanel.add(finalResultListBox);

		Button submitButton = new Button("Submit");
		submitButton.addClickHandler(event -> {
			String techFeedback = techFeedbackTextBox.getText().trim();
			String domainFeedback = domainFeedbackTextBox.getText().trim();
			String commSkillsFeedback = commSkillsFeedbackTextBox.getText().trim();
			String finalResult = finalResultListBox.getSelectedValue();

			if (techFeedback.isEmpty() || domainFeedback.isEmpty() || commSkillsFeedback.isEmpty()
					|| finalResult.isEmpty()) {
				Window.alert("Please fill in all feedback fields and select the final result.");
				return;
			}

			// Submit the feedback to the backend
			submitFeedback(candidate, techFeedback, domainFeedback, commSkillsFeedback, finalResult);
			feedbackDialog.hide();
		});

		Button closeButton = new Button("Close");
		closeButton.addClickHandler(event -> {
			feedbackDialog.hide();
		});

		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(submitButton);
		buttonPanel.add(closeButton);

		dialogPanel.add(buttonPanel);
		feedbackDialog.add(dialogPanel);

		feedbackDialog.setAnimationEnabled(true);
		feedbackDialog.setGlassEnabled(true);
		feedbackDialog.center();
	}

	private void submitFeedback(Candidate candidate, String techFeedback, String domainFeedback,
			String commSkillsFeedback, String finalResult) {
		JSONObject feedbackJson = new JSONObject();
		feedbackJson.put("candidateId", new JSONNumber(candidate.getCandidateId()));
		feedbackJson.put("interviewerUserId", new JSONString(Integer.toString(loggedInUserId))); // Assuming you have
																									// the interviewer's
		// user ID stored

		feedbackJson.put("techFeedback", new JSONString(techFeedback));
		feedbackJson.put("domainFeedback", new JSONString(domainFeedback));
		feedbackJson.put("commSkillsFeedback", new JSONString(commSkillsFeedback));
		feedbackJson.put("finalResult", new JSONString(finalResult));

		String jsonPayload = feedbackJson.toString();

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
				"http://localhost:8080/IMSApi/api?action=submitFeedback");
		try {
			builder.setHeader("Content-Type", "application/json");
			builder.sendRequest(jsonPayload, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						// Feedback submitted successfully, update the frontend
						Window.alert("Feedback submitted successfully!");
						fetchCandidateData(); // Reload the table with the latest data
					} else {
						Window.alert("Error submitting feedback: " + response.getStatusText());
					}
				}

				@Override
				public void onError(Request request, Throwable throwable) {
					Window.alert("Error submitting feedback: " + throwable.getMessage());
				}
			});
		} catch (RequestException e) {
			Window.alert("Error submitting feedback: " + e.getMessage());
		}
	}

}
