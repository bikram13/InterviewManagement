package com.nlpdk.interviewManagement.web.gwt.client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

public class InterviewerCandidateListPage extends Composite {

	private FlexTable candidateTable;
	private Users user;

	public InterviewerCandidateListPage(Users user) {
		this.user = user;
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

			// Add "Evaluate" button for each candidate
			Button evaluateButton = new Button("Evaluate");
			evaluateButton.addClickHandler(event -> showFeedbackDialog(candidate));
			candidateTable.setWidget(i + 1, 6, evaluateButton);
		}
	}

	private void showFeedbackDialog(Candidate candidate) {
		// Create the feedback dialog
		DialogBox feedbackDialog = new DialogBox();
		feedbackDialog.setText("Candidate Feedback: " + candidate.getName());

		// Show feedback data in tabular format
		FlexTable feedbackTable = new FlexTable();
		feedbackTable.setText(0, 0, "Interviewer Name");
		feedbackTable.setText(0, 1, "Tech Feedback");
		feedbackTable.setText(0, 2, "Domain Feedback");
		feedbackTable.setText(0, 3, "Comm Skills Feedback");
		feedbackTable.setText(0, 4, "Final Result");

		// Add rows and data for feedback received from backend

		// Add a close button to close the dialog
		Button closeButton = new Button("Close");
		closeButton.addClickHandler(event -> feedbackDialog.hide());

		VerticalPanel dialogPanel = new VerticalPanel();
		dialogPanel.add(feedbackTable);
		dialogPanel.add(closeButton);

		feedbackDialog.add(dialogPanel);

		// Show the feedback dialog
		feedbackDialog.center();
		feedbackDialog.show();
	}
}
