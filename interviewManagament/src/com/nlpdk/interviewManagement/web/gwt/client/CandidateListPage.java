package com.nlpdk.interviewManagement.web.gwt.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class CandidateListPage extends Composite {

    private FlexTable candidateTable;
    private Button addButton;
    private Map<String, List<String>> candidateToInterviewersMap;

    public CandidateListPage() {

        candidateTable = new FlexTable();
        candidateTable.setText(0, 0, "Candidate Name");
        candidateTable.setText(0, 1, "Candidate Email");
        candidateTable.setText(0, 2, "Assign"); // Renamed "Interview Panel" to "Assign"
        candidateTable.setText(0, 3, "Delete");
        candidateTable.setText(0, 4, "Evaluate");
        candidateTable.setText(0, 5, "Interview Panel"); // New column for Interview Panel

        // Sample data (you will fetch data from the server later)
        addExistingCandidate("John Doe", "john.doe@example.com", new ArrayList<>());
        addExistingCandidate("Jane Smith", "jane.smith@example.com", new ArrayList<>());

        addButton = new Button("Add Candidate");
        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                showAddCandidateDialog();
            }
        });

        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.add(new HTML("<h2>Candidate List</h2>"));
        mainPanel.add(candidateTable);
        mainPanel.add(addButton);

        // Button to call data from server
        Button getDataButton = new Button("Get Interviewer Data");
        getDataButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                getInterviewerDataFromServer();
            }
        });
        mainPanel.add(getDataButton);

        RootPanel.get().add(mainPanel);

        // Initialize the candidate to interviewers mapping
        candidateToInterviewersMap = new HashMap<>();
    }

    private void addExistingCandidate(String name, String email, List<String> interviewers) {
        // Create a new row in the candidate table and add candidate details
        int row = candidateTable.getRowCount();
        candidateTable.setText(row, 0, name);
        candidateTable.setText(row, 1, email);

        // Add "Assign Interviewer" button for the existing candidate
        Button assignInterviewerButton = new Button("Assign Interviewer");
        assignInterviewerButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                showCandidateDetailsDialog(name, email);
            }
        });
        candidateTable.setWidget(row, 2, assignInterviewerButton);

        // Add "Delete" button for the existing candidate (same as before)
        Button deleteButton = new Button("Delete");
        deleteButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                removeCandidate(row);
            }
        });
        candidateTable.setWidget(row, 3, deleteButton);

        // Add "Evaluate" button for the existing candidate (same as before)
        Button evaluateButton = new Button("Evaluate");
        evaluateButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                showEvaluateDialog(name); // Show the evaluation dialog box for the candidate
            }
        });
        candidateTable.setWidget(row, 4, evaluateButton);

        // Add a label to display the assigned interviewers
        Label interviewersLabel = new Label(getAssignedInterviewersAsString(interviewers));
        candidateTable.setWidget(row, 5, interviewersLabel);
    }

    private String getAssignedInterviewersAsString(List<String> interviewers) {
        if (interviewers.isEmpty()) {
            return "None";
        }
        return String.join(", ", interviewers);
    }

    private void showEvaluateDialog(String candidateName) {
        // Create the evaluation dialog
        DialogBox evaluateDialog = new DialogBox();
        evaluateDialog.setText("Evaluate Candidate: " + candidateName);

        // Create the form for evaluation
        VerticalPanel dialogPanel = new VerticalPanel();

        // Add fields and labels for evaluation (you can customize as needed)
        TextBox technicalFeedback = new TextBox();
        TextBox domainFeedback = new TextBox();
        TextBox commSkills = new TextBox();
        TextBox finalFeedback = new TextBox();
        dialogPanel.add(new HTML("<b>Candidate Name:</b> " + candidateName));
        dialogPanel.add(new Label("Technical Knowledge:"));
        dialogPanel.add(technicalFeedback);
        dialogPanel.add(new Label("Domain Knowledge:"));
        dialogPanel.add(domainFeedback);
        dialogPanel.add(new Label("Communication Skills:"));
        dialogPanel.add(commSkills);
        dialogPanel.add(new Label("Final Feedback:"));
        dialogPanel.add(finalFeedback);

        // Add a Submit button
        Button submitButton = new Button("Submit");
        submitButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // Add your logic to handle the submitted data here
                evaluateDialog.hide(); // Close the dialog box upon clicking the Submit button
            }
        });
        dialogPanel.add(submitButton);

        evaluateDialog.add(dialogPanel);

        // Show the dialog box
        evaluateDialog.center();
        evaluateDialog.show();
    }

    private void removeCandidate(int row) {
        // Remove the candidate from the table
        String candidateName = candidateTable.getText(row, 0);
        candidateTable.removeRow(row);
        candidateToInterviewersMap.remove(candidateName);
    }

    private void updateAssignInterviewerButton(int row, String candidateName, String candidateEmail) {
        Button assignInterviewerButton = new Button("Assign Interviewer");

        assignInterviewerButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                showCandidateDetailsDialog(candidateName, candidateEmail);
            }
        });

        candidateTable.setWidget(row, 2, assignInterviewerButton);
    }

    private void showCandidateDetailsDialog(String candidateName, String candidateEmail) {
        // Create the candidate details dialog
        DialogBox candidateDialog = new DialogBox();
        candidateDialog.setText("Candidate Details: " + candidateName);

        // Show candidate details
        VerticalPanel dialogPanel = new VerticalPanel();
        dialogPanel.add(new HTML("<b>Name:</b> " + candidateName));
        dialogPanel.add(new HTML("<b>Email:</b> " + candidateEmail)); // Add the candidate's email

        // Show available interviewers and number of candidates assigned
        FlexTable interviewerTable = new FlexTable();
        interviewerTable.setText(0, 0, "Select");
        interviewerTable.setText(0, 1, "Interviewer Name");
        interviewerTable.setText(0, 2, "Number of Candidates Assigned");

        List<Interviewer> interviewerList = getInterviewerData();

        for (int i = 0; i < interviewerList.size(); i++) {
            Interviewer interviewer = interviewerList.get(i);

            CheckBox selectCheckBox = new CheckBox();
            interviewerTable.setWidget(i + 1, 0, selectCheckBox);

            interviewerTable.setText(i + 1, 1, interviewer.getName());
            interviewerTable.setText(i + 1, 2, String.valueOf(interviewer.getNumCandidatesAssigned()));
        }

        dialogPanel.add(new HTML("<h3>Assign Interviewer</h3>"));
        dialogPanel.add(interviewerTable);

        // Add Assign button at the bottom of the interviewer list
        Button assignButton = new Button("Assign");
        assignButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                List<String> assignedInterviewers = new ArrayList<>();
                for (int i = 1; i < interviewerTable.getRowCount(); i++) {
                    CheckBox checkBox = (CheckBox) interviewerTable.getWidget(i, 0);
                    if (checkBox.getValue()) {
                        assignedInterviewers.add(interviewerTable.getText(i, 1));
                    }
                }
                candidateToInterviewersMap.put(candidateName, assignedInterviewers);
                updateCandidateInterviewersLabel(candidateName, assignedInterviewers);
                candidateDialog.hide(); // Close the dialog box upon clicking the Assign button
            }
        });
        dialogPanel.add(assignButton);

        candidateDialog.add(dialogPanel);

        // Show the dialog box
        candidateDialog.center();
        candidateDialog.show();
    }

    private void updateCandidateInterviewersLabel(String candidateName, List<String> assignedInterviewers) {
        int row = findCandidateRow(candidateName);
        Label interviewersLabel = new Label(getAssignedInterviewersAsString(assignedInterviewers));
        candidateTable.setWidget(row, 5, interviewersLabel);
    }

    private int findCandidateRow(String candidateName) {
        for (int i = 1; i < candidateTable.getRowCount(); i++) {
            if (candidateTable.getText(i, 0).equals(candidateName)) {
                return i;
            }
        }
        return -1;
    }

    private void showAddCandidateDialog() {
        // Create the add candidate dialog
        DialogBox addDialog = new DialogBox();
        addDialog.setText("Add Candidate");

        // Input fields for candidate details
        Label firstNameLabel = new Label("First Name:");
        TextBox firstNameTextBox = new TextBox();
        Label lastNameLabel = new Label("Last Name:");
        TextBox lastNameTextBox = new TextBox();
        Label emailLabel = new Label("Email:");
        TextBox emailTextBox = new TextBox();
        Label psNoLabel = new Label("PS No:");
        TextBox psNoTextBox = new TextBox();
        Label contactNoLabel = new Label("Contact No:");
        TextBox contactNoTextBox = new TextBox();
        Label profileLabel = new Label("Profile:");
        TextBox profileTextBox = new TextBox();
        
       
        Button addButton = new Button("Add");
        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // Get the candidate details from input fields
                String firstName = firstNameTextBox.getText();
                String lastName = lastNameTextBox.getText();
                String email = emailTextBox.getText();
                String psNo = psNoTextBox.getText();
                String contactNo = contactNoTextBox.getText();
                String profileFileName = profileTextBox.getText();

                

                // Create a new row in the candidate table and add candidate details
                int row = candidateTable.getRowCount();
                candidateTable.setText(row, 0, firstName + " " + lastName);
                candidateTable.setText(row, 1, email);

                // Add "Assign Interviewer" button for the newly added candidate
                Button assignInterviewerButton = new Button("Assign Interviewer");
                assignInterviewerButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        showCandidateDetailsDialog(firstName + " " + lastName, email);
                    }
                });
                candidateTable.setWidget(row, 2, assignInterviewerButton);

                // Add "Delete" button for the newly added candidate (same as before)
                Button deleteButton = new Button("Delete");
                deleteButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        removeCandidate(row);
                    }
                });
                candidateTable.setWidget(row, 3, deleteButton); // Place the "Delete" button in the 3rd column

                // Add "Evaluate" button for the newly added candidate (same as before)
                Button evaluateButton = new Button("Evaluate");
                evaluateButton.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        showEvaluateDialog(firstName + " " + lastName); // Show the evaluation dialog box for the candidate
                    }
                });
                candidateTable.setWidget(row, 4, evaluateButton); // Place the "Evaluate" button in the 4th column

                // Add label for the assigned interviewers (initially "None")
                Label interviewersLabel = new Label("None");
                candidateTable.setWidget(row, 5, interviewersLabel); // Place the label in the 5th column

                // Close the dialog after adding the candidate
                addDialog.hide();
            }
        });

        Button closeButton = new Button("Close");
        closeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                addDialog.hide();
            }
        });

        // Layout for the add candidate dialog
        VerticalPanel dialogPanel = new VerticalPanel();
        dialogPanel.add(new HTML("<h3>Add Candidate</h3>"));
        dialogPanel.add(firstNameLabel);
        dialogPanel.add(firstNameTextBox);
        dialogPanel.add(lastNameLabel);
        dialogPanel.add(lastNameTextBox);
        dialogPanel.add(emailLabel);
        dialogPanel.add(emailTextBox);
        dialogPanel.add(psNoLabel);
        dialogPanel.add(psNoTextBox);
        dialogPanel.add(contactNoLabel);
        dialogPanel.add(contactNoTextBox);
        dialogPanel.add(profileLabel);
        dialogPanel.add(profileTextBox);
        dialogPanel.add(addButton);
        dialogPanel.add(closeButton);

        addDialog.add(dialogPanel);

        // Show the dialog box
        addDialog.center();
        addDialog.show();
    }

    

    private String getCandidateEmail(String candidateName) {
        // Sample method to fetch candidate email from the data (replace with your data source)
        // For demonstration purposes, we use a simple string array.
        String[] candidates = { "John Doe", "Jane Smith",
                // Add more candidate names as needed...
        };
        String[] candidateEmails = { "john.doe@example.com", "jane.smith@example.com",
                // Add more candidate emails as needed...
        };

        for (int i = 0; i < candidates.length; i++) {
            if (candidates[i].equals(candidateName)) {
                return candidateEmails[i];
            }
        }

        return "";
    }

    private List<Interviewer> getInterviewerData() {
        // Sample data (replace this with data fetched from the server)
        List<Interviewer> interviewerList = new ArrayList<>();

        interviewerList.add(new Interviewer("Interviewer 1", 3));
        interviewerList.add(new Interviewer("Interviewer 2", 2));
        interviewerList.add(new Interviewer("Interviewer 3", 1));

        return interviewerList;
    }

    private void getInterviewerDataFromServer() {
        // Create the URL to the InterviewerServlet
        String url = "http://localhost:8080/IMSApi/InterviewerDataServlet"; // Replace with the correct URL

        // Create a RequestBuilder instance
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

        try {
            // Send the HTTP GET request
            builder.sendRequest(null, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (response.getStatusCode() == Response.SC_OK) {
                        // Parse the JSON response and update the interviewerList with the received data
                        String json = response.getText();
                        System.out.println("Request JSON" + json);
                        List<Interviewer> interviewerList = parseJson(json);
                        updateInterviewerList(interviewerList);

                        GWT.log(interviewerList.toString());
                    } else {
                        Window.alert("Error: " + response.getStatusText());
                    }
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    Window.alert("Error fetching interviewer data: " + exception.getMessage());
                }
            });
        } catch (RequestException e) {
            Window.alert("RequestException: " + e.getMessage());
        }
    }

    private List<Interviewer> parseJson(String json) {
        // Parse the JSON data and create a List of Interviewer objects
        GWT.log(json);
        List<Interviewer> interviewerList = new ArrayList<>();

        // Use the GWT-compatible JSON classes to parse the JSON data
        JSONObject jsonObject = JSONParser.parseStrict(json).isObject();

        // Assuming your JSON data is an array of Interviewer objects
        JSONArray interviewersArray = jsonObject.get("interviewers").isArray();

        for (int i = 0; i < interviewersArray.size(); i++) {
            JSONObject interviewerObject = interviewersArray.get(i).isObject();
            String name = interviewerObject.get("name").isString().stringValue();
            int numCandidatesAssigned = (int) interviewerObject.get("numCandidatesAssigned").isNumber().doubleValue();
            interviewerList.add(new Interviewer(name, numCandidatesAssigned));
        }

        return interviewerList;
    }

    private void updateInterviewerList(List<Interviewer> interviewerList) {
        // Update the interviewer list in your UI
        // ...
    }

    private static class Interviewer {
        private String name;
        private int numCandidatesAssigned;

        public Interviewer(String name, int numCandidatesAssigned) {
            this.name = name;
            this.numCandidatesAssigned = numCandidatesAssigned;
        }

        public String getName() {
            return name;
        }

        public int getNumCandidatesAssigned() {
            return numCandidatesAssigned;
        }
    }
}
