package com.nlpdk.interviewManagement.web.gwt.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class CandidateListPage extends Composite {

	private FlexTable candidateTable;
	private List<Candidate> candidates;
	private Map<String, List<String>> candidateToInterviewersMap;
	private Set<String> selectedInterviewers = new HashSet<>();

	public CandidateListPage() {
		candidateTable = new FlexTable();
		candidateTable.setText(0, 0, "Candidate Name");
		candidateTable.setText(0, 1, "Email ID");
		candidateTable.setText(0, 2, "PS No");
		candidateTable.setText(0, 3, "Contact Number");
		candidateTable.setText(0, 4, "Assign");
		candidateTable.setText(0, 5, "Interview Panel");
		// Add "Feedback" column
		candidateTable.setText(0, 6, "Feedback");

		candidates = new ArrayList<>();
		candidateToInterviewersMap = new HashMap<>();

		// For demonstration, add some sample data to the candidates list
		candidates.add(new Candidate(100, "John Doe", "john.doe@example.com", "PS001", "1234567890", null));
		candidates.add(new Candidate(101, "Jane Smith", "jane.smith@example.com", "PS002", "9876543210", null));

		populateCandidateTable();
		// Populate the "Feedback" column for existing candidates
		populateFeedbackColumn();

		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.add(new HTML("<h2>Candidate List</h2>"));
		mainPanel.add(candidateTable);

		Button addButton = new Button("Add Candidate");
		addButton.addClickHandler(event -> showAddCandidateDialog());
		mainPanel.add(addButton);

		initWidget(mainPanel);
	}

	private void populateFeedbackColumn() {
		for (int i = 0; i < candidates.size(); i++) {
			Candidate candidate = candidates.get(i);
			String candidateName = candidate.getName();
			Anchor feedbackLink = new Anchor("Feedback");
			feedbackLink.addClickHandler(event -> getFeedbackDataForCandidate(candidate));
			candidateTable.setWidget(i + 1, 6, feedbackLink);
		}
	}

	private void getFeedbackDataForCandidate(Candidate candidate) {
		String action = "getFeedbackData";
		// Create a JSON object to store candidateId and candidateName
		JSONObject requestDataObject = new JSONObject();
		requestDataObject.put("candidateId", new JSONNumber(candidate.getCandidateId()));
		requestDataObject.put("candidateName", new JSONString(candidate.getName()));
		String requestData = requestDataObject.toString();

		// Create the RequestBuilder for the POST request
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
				"http://localhost:8080/IMSApi/api?action=getFeedbackData");
//		builder.setHeader("Content-Type", "application/json");

		try {
			// Send the request
			builder.sendRequest(requestData, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					// Check if the request was successful (status code 200)
					if (response.getStatusCode() == 200) {
						// Handle the response
						List<FeedbackData> feedbackDataList = parseFeedbackData(response.getText());
						showFeedbackDialog(candidate, feedbackDataList);
					} else {
						// Handle the error
						GWT.log("Error: " + response.getStatusText());
					}
				}

				@Override
				public void onError(Request request, Throwable throwable) {
					// Handle the error
					GWT.log("Error: " + throwable.getMessage());
				}
			});
		} catch (RequestException e) {
			// Handle the exception
			GWT.log("Error: " + e.getMessage());
		}
	}

	private void showFeedbackDialog(Candidate candidate, List<FeedbackData> feedbackDataList) {
		// Show feedback data in tabular format
		FlexTable feedbackTable = new FlexTable();
		feedbackTable.setText(0, 0, "Interviewer Name");
		feedbackTable.setText(0, 1, "Tech Feedback");
		feedbackTable.setText(0, 2, "Domain Feedback");
		feedbackTable.setText(0, 3, "Comm Skills Feedback");
		feedbackTable.setText(0, 4, "Final Result");

		for (int i = 0; i < feedbackDataList.size(); i++) {
			FeedbackData feedbackData = feedbackDataList.get(i);
			feedbackTable.setText(i + 1, 0, feedbackData.getCandidateInterviewer().getInterviewer().getFirstName());
			feedbackTable.setText(i + 1, 1, feedbackData.getTechFeedback());
			feedbackTable.setText(i + 1, 2, feedbackData.getDomainFeedback());
			feedbackTable.setText(i + 1, 3, feedbackData.getCommSkillsFeedback());
			feedbackTable.setText(i + 1, 4, feedbackData.getFinalResult());
		}

		// Create the feedback dialog
		DialogBox feedbackDialog = new DialogBox();
		feedbackDialog.setText("Candidate Feedback: " + candidate.getName());

		VerticalPanel dialogPanel = new VerticalPanel();
		dialogPanel.add(feedbackTable);

		// Add a close button to close the dialog
		Button closeButton = new Button("Close");
		closeButton.addClickHandler(event -> feedbackDialog.hide());
		dialogPanel.add(closeButton);

		feedbackDialog.add(dialogPanel);

		// Show the feedback dialog
		feedbackDialog.center();
		feedbackDialog.show();
	}

	private List<FeedbackData> parseFeedbackData(String responseText) {
		List<FeedbackData> feedbackDataList = new ArrayList<>();

		// Parse the responseText and create FeedbackData objects
		// Assuming the responseText is in JSON format
		JSONObject jsonObject = JSONParser.parseStrict(responseText).isObject();
//		String name = jsonObject.get("feedbackData").isString().stringValue();

		JSONArray jsonArray = jsonObject.get("feedbackData").isArray();

		if (jsonArray != null) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject feedbackObject = jsonArray.get(i).isObject();
				FeedbackData feedbackData = createFeedbackDataFromJSON(feedbackObject);
				if (feedbackData != null) {
					feedbackDataList.add(feedbackData);
				}
			}
		}

		return feedbackDataList;
	}

	private FeedbackData createFeedbackDataFromJSON(JSONObject feedbackObject) {
		// Parse the JSON object and create a FeedbackData object
		// Assumes the JSON object contains fields like "techFeedback",
		// "domainFeedback", etc.
		// Replace these keys with the actual keys used in your backend response
		String techFeedback = feedbackObject.get("techFeedback").isString().stringValue();
		String domainFeedback = feedbackObject.get("domainFeedback").isString().stringValue();
		String commSkillsFeedback = feedbackObject.get("commSkillsFeedback").isString().stringValue();
		String finalResult = feedbackObject.get("finalResult").isString().stringValue();

		// Here you need to fetch the Candidate_Interviewer object from the JSON data
		// Replace "getCandidate_InterviewerFromJSON" with the actual method to convert
		// JSON to Candidate_Interviewer
		Candidate_Interviewer candidateInterviewer = getCandidate_InterviewerFromJSON(
				feedbackObject.get("candidateInterviewer").isObject());

		return new FeedbackData(candidateInterviewer, techFeedback, domainFeedback, commSkillsFeedback, finalResult);
	}

	private Candidate_Interviewer getCandidate_InterviewerFromJSON(JSONObject interviewerObject) {
		Candidate candidate = getCandidateFromJSON(interviewerObject.get("candidate").isObject());
		Users interviewer = getUsersFromJSON(interviewerObject.get("interviewer").isObject());

		return new Candidate_Interviewer(candidate, interviewer);
	}

	private Candidate getCandidateFromJSON(JSONObject candidateObject) {
		String firstName = candidateObject.get("firstName").isString().stringValue();
		String lastName = candidateObject.get("lastName").isString().stringValue();
		String email = candidateObject.get("emailId").isString().stringValue();
		String psNo = candidateObject.get("psNo").isString().stringValue();
		String contactNo = candidateObject.get("contactNo").isString().stringValue();
		int id = (int) candidateObject.get("id").isNumber().doubleValue();

		return new Candidate(id, firstName + " " + lastName, email, psNo, contactNo, null);
	}

	private Users getUsersFromJSON(JSONObject interviewerObject) {
		// Parse the JSON object and create a Users object (interviewer)
		// Assumes the JSON object contains fields like "name", "email", etc.
		// Replace these keys with the actual keys used in your backend response
		int id = (int) interviewerObject.get("id").isNumber().doubleValue();
		String firstName = interviewerObject.get("firstName").isString().stringValue();
		String lastName = interviewerObject.get("lastName").isString().stringValue();
		String emailId = interviewerObject.get("emailId").isString().stringValue();
		String psNo = interviewerObject.get("psNo").isString().stringValue();
		String role = interviewerObject.get("role").isString().stringValue();
		String contactNo = interviewerObject.get("contactNo").isString().stringValue();
		String password = interviewerObject.get("password").isString().stringValue();
		// Add other fields based on your response JSON structure

		return new Users(id, firstName, lastName, emailId, psNo, role, contactNo, password); // Replace ... with other
																								// fields as needed
	}

	private void populateCandidateTable() {
		for (int i = 0; i < candidates.size(); i++) {
			Candidate candidate = candidates.get(i);
			candidateTable.setText(i + 1, 0, candidate.getName());
			candidateTable.setText(i + 1, 1, candidate.getEmail());
			candidateTable.setText(i + 1, 2, candidate.getPsNo());
			candidateTable.setText(i + 1, 3, candidate.getContactNo());

			if (isCandidateAssigned(candidate.getName())) {
				// If an interviewer is assigned, disable the "Assign" button
				Button assignInterviewerButton = (Button) candidateTable.getWidget(i + 1, 4);
				assignInterviewerButton.setEnabled(false);
			} else {
				// If no interviewer is assigned, enable the "Assign" button
				Button assignInterviewerButton = new Button("Assign Interviewer");
				assignInterviewerButton.addClickHandler(event -> showCandidateDetailsDialog(candidate));
				candidateTable.setWidget(i + 1, 4, assignInterviewerButton);
			}

			// Update the "Interview Panel" column
			updateCandidateInterviewersLabel(candidate.getName());

			// Add "Feedback" link for each candidate
			candidateTable.setWidget(i + 1, 6, createFeedbackLink(candidate));

		}
	}

	private Widget createFeedbackLink(Candidate candidate) {
		Anchor feedbackLink = new Anchor("Feedback");
		feedbackLink.addClickHandler(event -> getFeedbackDataForCandidate(candidate));
		return feedbackLink;
	}

	private boolean isCandidateAssigned(String candidateName) {
		return candidateToInterviewersMap.containsKey(candidateName);
	}

	private void updateCandidateInterviewersLabel(String candidateName) {
		int row = findCandidateRow(candidateName);
		List<String> assignedInterviewers = candidateToInterviewersMap.get(candidateName);
		if (assignedInterviewers == null || assignedInterviewers.isEmpty()) {
			candidateTable.setText(row, 5, "None");
		} else {
			String interviewerNames = String.join(", ", assignedInterviewers);
			Label interviewersLabel = new Label(interviewerNames);
			candidateTable.setWidget(row, 5, interviewersLabel);
		}
	}

	private int findCandidateRow(String candidateName) {
		for (int i = 1; i < candidateTable.getRowCount(); i++) {
			if (candidateTable.getText(i, 0).equals(candidateName)) {
				return i;
			}
		}
		return -1;
	}

	private void showCandidateDetailsDialog(Candidate candidate) {
		// Create the candidate details dialog
		DialogBox candidateDialog = new DialogBox();
		candidateDialog.setText("Candidate Details: " + candidate.getName());

		// Show candidate details
		VerticalPanel dialogPanel = new VerticalPanel();
		dialogPanel.add(new HTML("<b>Name:</b> " + candidate.getName()));
		dialogPanel.add(new HTML("<b>Email:</b> " + candidate.getEmail()));
		dialogPanel.add(new HTML("<b>PS No:</b> " + candidate.getPsNo()));
		dialogPanel.add(new HTML("<b>Contact Number:</b> " + candidate.getContactNo()));

		// Show available interviewers and number of candidates assigned
		FlexTable interviewerTable = new FlexTable();
		interviewerTable.setText(0, 0, "Select");
		interviewerTable.setText(0, 1, "Interviewer Name");
		interviewerTable.setText(0, 2, "Number of Candidates Assigned");

		List<Interviewer> interviewerList = getInterviewerData();

		for (int i = 0; i < interviewerList.size(); i++) {
			Interviewer interviewer = interviewerList.get(i);

			// Use CheckBox instead of Button
			CheckBox selectCheckBox = new CheckBox();
			selectCheckBox.addValueChangeHandler(event -> {
				boolean isSelected = event.getValue();
				if (isSelected) {
					// Add the selected interviewer to the Set
					selectedInterviewers.add(interviewer.getName());
				} else {
					// Remove the interviewer from the Set if it was deselected
					selectedInterviewers.remove(interviewer.getName());
				}
			});

			interviewerTable.setWidget(i + 1, 0, selectCheckBox);
			interviewerTable.setText(i + 1, 1, interviewer.getName());
			interviewerTable.setText(i + 1, 2, String.valueOf(interviewer.getNumCandidatesAssigned()));
		}

		dialogPanel.add(new HTML("<h3>Assign Interviewer</h3>"));
		dialogPanel.add(interviewerTable);

		// Add Submit and Close buttons
		Button submitButton = new Button("Submit");
		submitButton.addClickHandler(event -> {
			assignInterviewersToCandidate(candidate.getName(), selectedInterviewers);
			candidateDialog.hide();
			// Repopulate the candidate table to update the "Assign" button
			populateCandidateTable();
		});

		Button closeButton = new Button("Close");
		closeButton.addClickHandler(event -> candidateDialog.hide());

		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(submitButton);
		buttonPanel.add(closeButton);
		dialogPanel.add(buttonPanel);

		candidateDialog.add(dialogPanel);

		// Show the dialog box
		candidateDialog.center();
		candidateDialog.show();
	}

	private void assignInterviewersToCandidate(String candidateName, Set<String> selectedInterviewers) {
		if (selectedInterviewers.isEmpty()) {
			candidateToInterviewersMap.remove(candidateName);
		} else {
			candidateToInterviewersMap.put(candidateName, new ArrayList<>(selectedInterviewers));
		}
		updateCandidateInterviewersLabel(candidateName);

		// For demonstration, send the data to the server
		sendAssignInterviewersDataToServer(candidateName, selectedInterviewers);
	}

	private void sendAssignInterviewersDataToServer(String candidateName, Set<String> selectedInterviewers) {
		// Replace this with the actual server call to send the data to the backend
		// For demonstration, we'll just print the data to the console
		System.out.println(
				"Candidate: " + candidateName + ", Selected Interviewers: " + String.join(", ", selectedInterviewers));
	}

	private List<Interviewer> getInterviewerData() {
		// Replace this with actual server call to fetch interviewer data
		// This is just a placeholder for demonstration purposes
		List<Interviewer> interviewerList = new ArrayList<>();
		interviewerList.add(new Interviewer("Interviewer 1", 2));
		interviewerList.add(new Interviewer("Interviewer 2", 1));
		interviewerList.add(new Interviewer("Interviewer 3", 0));
		return interviewerList;
	}

	private void showAddCandidateDialog() {
		// Create the add candidate dialog
		DialogBox addCandidateDialog = new DialogBox();
		addCandidateDialog.setText("Add Candidate");

		// Create input fields for candidate data
		VerticalPanel dialogPanel = new VerticalPanel();
		TextBox firstNameTextBox = new TextBox();
		TextBox lastNameTextBox = new TextBox();
		TextBox emailTextBox = new TextBox();
		TextBox psNoTextBox = new TextBox();
		TextBox contactNumberTextBox = new TextBox();

		dialogPanel.add(new Label("First Name:"));
		dialogPanel.add(firstNameTextBox);
		dialogPanel.add(new Label("Last Name:"));
		dialogPanel.add(lastNameTextBox);
		dialogPanel.add(new Label("Email ID:"));
		dialogPanel.add(emailTextBox);
		dialogPanel.add(new Label("PS No:"));
		dialogPanel.add(psNoTextBox);
		dialogPanel.add(new Label("Contact Number:"));
		dialogPanel.add(contactNumberTextBox);

		// Add buttons for adding the candidate and closing the dialog
		Button addButton = new Button("Add");
		addButton.addClickHandler(event -> {
			String firstName = firstNameTextBox.getText();
			String lastName = lastNameTextBox.getText();
			String email = emailTextBox.getText();
			String psNo = psNoTextBox.getText();
			String contactNumber = contactNumberTextBox.getText();
			addCandidate(firstName, lastName, email, psNo, contactNumber);
			addCandidateDialog.hide();
		});

		Button closeButton = new Button("Close");
		closeButton.addClickHandler(event -> addCandidateDialog.hide());

		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(addButton);
		buttonPanel.add(closeButton);

		dialogPanel.add(buttonPanel);
		addCandidateDialog.add(dialogPanel);

		// Show the add candidate dialog
		addCandidateDialog.center();
		addCandidateDialog.show();
	}

	private void addCandidate(String firstName, String lastName, String email, String psNo, String contactNumber) {
		Candidate candidate = new Candidate(2, firstName + " " + lastName, email, psNo, contactNumber, null);
		candidates.add(candidate);
		populateCandidateTable();
	}
}
