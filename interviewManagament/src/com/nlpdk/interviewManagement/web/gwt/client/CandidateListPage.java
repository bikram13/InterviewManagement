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
import com.google.gwt.user.client.Window;
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
		candidateTable.setText(0, 6, "Feedback");
		candidates = new ArrayList<>();
		candidateToInterviewersMap = new HashMap<>();
		getAllCandidates();
		populateFeedbackColumn();
		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.add(new HTML("<h2>Candidate List</h2>"));
		mainPanel.add(candidateTable);
		Button addButton = new Button("Add Candidate");
		addButton.addClickHandler(event -> showAddCandidateDialog());
		addButton.setStyleName("add-candidate-button");
		mainPanel.insert(addButton,1);
		initWidget(mainPanel);
	}
	private void getAllCandidates() {
		GWT.log("inside kkk");
				RequestBuilder builder = new RequestBuilder(RequestBuilder.GET,
						"http://localhost:8080/IMSApi/api?action=getAllCandidatesForAssigner");
				try {
					builder.sendRequest(null, new RequestCallback() {
						@Override
						public void onResponseReceived(Request request, Response response) {
							if (response.getStatusCode() == 200) {
								parseCandidateData(response.getText());
							} else {
								GWT.log("Error: " + response.getStatusText());
							}
						}
						@Override
						public void onError(Request request, Throwable throwable) {
							GWT.log("Error: " + throwable.getMessage());
						}
					});
				} catch (RequestException e) {
					GWT.log("Error: " + e.getMessage());
				}
	}
	private void parseCandidateData(String responseText) {
		
		JSONObject jsonObject = JSONParser.parseStrict(responseText).isObject();
		JSONArray jsonArray = jsonObject.get("candidateList").isArray();
		if (jsonArray != null) {
			candidates = new ArrayList<>();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject candidateObject = jsonArray.get(i).isObject();
				Candidate candidateData = createCandidateDataFromJSON(candidateObject);
				if (candidateData != null) {
					candidates.add(candidateData);
				}
			}
			GWT.log(String.valueOf("Size of list::"+candidates.size()));
			populateCandidateTable(candidates);
		}
	}
	private Candidate createCandidateDataFromJSON(JSONObject candidateJsonObject) {
		String assignedTo = candidateJsonObject.get("assignedTo").isString().stringValue();
		JSONObject candidateObject = (JSONObject) candidateJsonObject.get("candidate"); 
		long id = Long.parseLong(candidateObject.get("id").toString());
		String firstName = candidateObject.get("firstName").isString().stringValue();
		String emailId = candidateObject.get("emailId").isString().stringValue();
		String contactNo = candidateObject.get("contactNo").isString().stringValue();
		String psNo = candidateObject.get("psNo").isString().stringValue();
		return new Candidate(id, firstName, emailId, psNo, contactNo, null
				,assignedTo);
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
		JSONObject requestDataObject = new JSONObject();
		requestDataObject.put("candidateId", new JSONNumber(candidate.getCandidateId()));
		requestDataObject.put("candidateName", new JSONString(candidate.getName()));
		String requestData = requestDataObject.toString();
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
				"http://localhost:8080/IMSApi/api?action=getFeedbackData");
		try {
			builder.sendRequest(requestData, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						List<FeedbackData> feedbackDataList = parseFeedbackData(response.getText());
						showFeedbackDialog(candidate, feedbackDataList);
					} else {
						GWT.log("Error: " + response.getStatusText());
					}
				}
				@Override
				public void onError(Request request, Throwable throwable) {
					GWT.log("Error: " + throwable.getMessage());
				}
			});
		} catch (RequestException e) {
			GWT.log("Error: " + e.getMessage());
		}
	}
	private void showFeedbackDialog(Candidate candidate, List<FeedbackData> feedbackDataList) {
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
		DialogBox feedbackDialog = new DialogBox();
		feedbackDialog.setText("Candidate Feedback: " + candidate.getName());
		VerticalPanel dialogPanel = new VerticalPanel();
		feedbackTable.setStyleName("custom-table");
		dialogPanel.add(feedbackTable);
		Button closeButton = new Button("Close");
		closeButton.addClickHandler(event -> feedbackDialog.hide());
		closeButton.setStyleName("close-button");
		dialogPanel.add(closeButton);
		feedbackDialog.add(dialogPanel);
		feedbackDialog.center();
		feedbackDialog.show();
	}
	private List<FeedbackData> parseFeedbackData(String responseText) {
		List<FeedbackData> feedbackDataList = new ArrayList<>();
		JSONObject jsonObject = JSONParser.parseStrict(responseText).isObject();
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
	private List<Interviewer> parseInterviewerCountData(String responseText){
		List<Interviewer> interviewerDataList = new ArrayList<>();
		// Parse the responseText and create interviewerData objects
		// Assuming the responseText is in JSON format
		JSONObject jsonObject = JSONParser.parseStrict(responseText).isObject();
		JSONArray jsonArray = jsonObject.get("interviewers").isArray();
		if (jsonArray != null) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject interviewerObject = jsonArray.get(i).isObject();
				Interviewer interviewerData = createInterviewerDataFromJSON(interviewerObject);
				if (interviewerData != null) {
					interviewerDataList.add(interviewerData);
				}
			}
		}
		return interviewerDataList;
	}
	private Interviewer createInterviewerDataFromJSON(JSONObject interviewerObject) {
		long interviewerId = Long.parseLong(interviewerObject.get("interviewerId").toString());
		String interviewerName = interviewerObject.get("interviewerName").isString().stringValue();
		long candidateAssignedCount = Long.parseLong(interviewerObject.get("candidateAssignedCount").toString());
		return new Interviewer(interviewerId, interviewerName,candidateAssignedCount);
	}
	private FeedbackData createFeedbackDataFromJSON(JSONObject feedbackObject) {
		String techFeedback = feedbackObject.get("techFeedback").isString().stringValue();
		String domainFeedback = feedbackObject.get("domainFeedback").isString().stringValue();
		String commSkillsFeedback = feedbackObject.get("commSkillsFeedback").isString().stringValue();
		String finalResult = feedbackObject.get("finalResult").isString().stringValue();
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
		return new Candidate(id, firstName + " " + lastName, email, psNo, contactNo, null, null);
	}
	private Users getUsersFromJSON(JSONObject interviewerObject) {
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
	}
	private void populateCandidateTable(List<Candidate> candidates) {
		for (int i = 0; i < candidates.size(); i++) {
			Candidate candidate = candidates.get(i);
			candidateTable.setText(i + 1, 0, candidate.getName());
			candidateTable.setText(i + 1, 1, candidate.getEmail());
			candidateTable.setText(i + 1, 2, candidate.getPsNo());
			candidateTable.setText(i + 1, 3, candidate.getContactNo());
			if (isCandidateAssigned(candidate.getName())) {
				Button assignInterviewerButton = (Button) candidateTable.getWidget(i + 1, 4);
				assignInterviewerButton.setEnabled(false);
			} else {
				Button assignInterviewerButton = new Button("Assign Interviewer");
				assignInterviewerButton.addClickHandler(event -> showCandidateDetailsDialog(candidate));
				candidateTable.setWidget(i + 1, 4, assignInterviewerButton);
			}
			GWT.log(candidate.getAssignedInterviwers());
			candidateTable.setText(i + 1, 5, candidate.getAssignedInterviwers());
			candidateTable.setWidget(i + 1, 6, createFeedbackLink(candidate));
			candidateTable.setStyleName("custom-table");
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
	private void showCandidateDetailsDialog(Candidate candidate) {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
				"http://localhost:8080/IMSApi/api?action=getCandidateCountAssignedToInterviewers");
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						List<Interviewer> interviewerList = parseInterviewerCountData(response.getText());
						showInterviewerAssignedDialog(interviewerList, candidate);
					} else {
						GWT.log("Error: " + response.getStatusText());
					}
				}
				@Override
				public void onError(Request request, Throwable throwable) {
					GWT.log("Error: " + throwable.getMessage());
				}
			});
		} catch (RequestException e) {
			GWT.log("Error: " + e.getMessage());
		}
	}
	private void showInterviewerAssignedDialog(List<Interviewer> interviewerList, Candidate candidate) {
				DialogBox candidateDialog = new DialogBox();
				candidateDialog.setText("Candidate Details: " + candidate.getName());
				VerticalPanel dialogPanel = new VerticalPanel();
				dialogPanel.add(new HTML("<b>Name:</b> " + candidate.getName()));
				dialogPanel.add(new HTML("<b>Email:</b> " + candidate.getEmail()));
				dialogPanel.add(new HTML("<b>PS No:</b> " + candidate.getPsNo()));
				dialogPanel.add(new HTML("<b>Contact Number:</b> " + candidate.getContactNo()));
				FlexTable interviewerTable = new FlexTable();
				interviewerTable.setText(0, 0, "Select");
				interviewerTable.setText(0, 1, "Interviewer Name");
				interviewerTable.setText(0, 2, "Number of Candidates Assigned");
		for (int i = 0; i < interviewerList.size(); i++) {
			Interviewer interviewer = interviewerList.get(i);
			CheckBox selectCheckBox = new CheckBox();
			selectCheckBox.addValueChangeHandler(event -> {
				boolean isSelected = event.getValue();
				if (isSelected) {
					selectedInterviewers.add(String.valueOf(interviewer.getId()));
				} else {
					selectedInterviewers.remove(String.valueOf(interviewer.getId()));
				}
			});
			interviewerTable.setWidget(i + 1, 0, selectCheckBox);
			interviewerTable.setText(i + 1, 1, interviewer.getName());
			interviewerTable.setText(i + 1, 2, String.valueOf(interviewer.getNumCandidatesAssigned()));
		}
		dialogPanel.add(new HTML("<h3>Assign Interviewer</h3>"));
		interviewerTable.setStyleName("custom-table");
		dialogPanel.add(interviewerTable);
		Button submitButton = new Button("Submit");
		submitButton.addClickHandler(event -> {
			assignInterviewersToCandidate(candidate.getCandidateId(), selectedInterviewers);
			candidateDialog.hide();
			getAllCandidates();
		});
		Button closeButton = new Button("Close");
		closeButton.addClickHandler(event -> candidateDialog.hide());
		
		Label spacer = new Label(" "); 
        spacer.setWidth("20px");
        
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(submitButton);
		buttonPanel.add(spacer);
		buttonPanel.add(closeButton);
		dialogPanel.add(buttonPanel);
		candidateDialog.add(dialogPanel);
		// Show the dialog box
		candidateDialog.center();
		candidateDialog.show();	
	}
	private void assignInterviewersToCandidate(long candidateId, Set<String> selectedInterviewers) {	
		if(!selectedInterviewers.isEmpty()) {
			List<Long> interviewerIdsList = new ArrayList<Long>();
			for (String interviewerId : selectedInterviewers) {
				interviewerIdsList.add(Long.parseLong(interviewerId));
			}
			JSONArray interviewerJsonArray = new JSONArray();
			for (Long idLong : interviewerIdsList) {
				interviewerJsonArray.set(interviewerJsonArray.size(), new JSONNumber(idLong));
			}
				    JSONObject requestDataObject = new JSONObject();
					requestDataObject.put("candidateId", new JSONNumber(candidateId));
					requestDataObject.put("interviewerIds", interviewerJsonArray);
					String requestData = requestDataObject.toString();
					RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
							"http://localhost:8080/IMSApi/api?action=addInterviewertoCandidate");
					try {
						builder.sendRequest(requestData, new RequestCallback() {
							@Override
							public void onResponseReceived(Request request, Response response) {
								if (response.getStatusCode() == 200) {
								} else {
									GWT.log("Error: " + response.getStatusText());
								}
							}
							@Override
							public void onError(Request request, Throwable throwable) {
								GWT.log("Error: " + throwable.getMessage());
							}
						});
					} catch (RequestException e) {
						GWT.log("Error: " + e.getMessage());
					}
		}
	}
	private void showAddCandidateDialog() {
		DialogBox addCandidateDialog = new DialogBox();
		addCandidateDialog.setText("Add Candidate");
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
		Button addButton = new Button("Add");
		addButton.addClickHandler(event -> {
			String firstName = firstNameTextBox.getText();
			String lastName = lastNameTextBox.getText();
			String email = emailTextBox.getText();
			String psNo = psNoTextBox.getText();
			String contactNumber = contactNumberTextBox.getText();
			addCandidate(firstName, lastName, email, psNo, contactNumber);
			addCandidateDialog.hide();
			GWT.log("Add button");
			
		});
		Button closeButton = new Button("Close");
		closeButton.addClickHandler(event -> addCandidateDialog.hide());
		HorizontalPanel buttonPanel = new HorizontalPanel();
		Label spacer = new Label(" "); 
        spacer.setWidth("20px");
        
		buttonPanel.add(addButton);
		buttonPanel.add(spacer);
		buttonPanel.add(closeButton);
		dialogPanel.add(buttonPanel);
		addCandidateDialog.add(dialogPanel);
		addCandidateDialog.center();
		addCandidateDialog.show();
	}
	private void addCandidate(String firstName, String lastName, String email, String psNo, String contactNumber) {
		insertCandidateData( firstName,  lastName,  email,  contactNumber,  psNo);

	}
	private void insertCandidateData(String firstName, String lastName, String email, String contactNo, String psNo) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("firstName", new JSONString(firstName));
		jsonObj.put("lastName", new JSONString(lastName));
		jsonObj.put("psNo", new JSONString(psNo));
		jsonObj.put("emailId", new JSONString(email));
		jsonObj.put("contactNo", new JSONString(contactNo));
		String jsonPayload = jsonObj.toString();
		String url = "http://localhost:8080/IMSApi/api?action=addCandidate";
		RequestBuilder reqBuilder = new RequestBuilder(RequestBuilder.POST, url);
		try {
			reqBuilder.sendRequest(jsonPayload, new RequestCallback() {
				@Override
				public void onResponseReceived(Request req, Response res) {
					if (res.getStatusCode() == 200) {
						String jsonResponse = res.getText();
						Window.alert("API call passed" + " " + res.getStatusCode());
						getAllCandidates();
					} else {
						getAllCandidates();
						Window.alert("API call failed" + " " + res.getStatusCode());
					}
				}
				@Override
				public void onError(Request request, Throwable exception) {
				}
			});
		} catch (Exception e) {
			Window.alert("API call failed" + e.getMessage());
		}
	}
}
