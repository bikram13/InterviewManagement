package com.nlpdk.interviewManagement.web.gwt.client;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
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

        initWidget(mainPanel); // Make sure to call initWidget to set the main panel as the widget of the Composite

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
        HorizontalPanel namePanel = new HorizontalPanel();
        Label nameLabel = new Label("First Name:");
        TextBox nameTextBox = new TextBox();
        namePanel.add(nameLabel);
        namePanel.add(nameTextBox);

        HorizontalPanel lastNamePanel = new HorizontalPanel();
        Label lastNameLabel = new Label("Last Name:");
        TextBox lastNameTextBox = new TextBox();
        lastNamePanel.add(lastNameLabel);
        lastNamePanel.add(lastNameTextBox);

        HorizontalPanel emailPanel = new HorizontalPanel();
        Label emailLabel = new Label("Email:");
        TextBox emailTextBox = new TextBox();
        emailPanel.add(emailLabel);
        emailPanel.add(emailTextBox);

        // ... Add other input field panels ...

        Button addButton = new Button("Add");
        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                // Get the candidate details from input fields
                String firstName = nameTextBox.getText();
                String lastName = lastNameTextBox.getText();
                String email = emailTextBox.getText();

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

        // Add input fields and "Add" button to the dialog panel
        VerticalPanel dialogPanel = new VerticalPanel();
        dialogPanel.add(namePanel);
        dialogPanel.add(lastNamePanel);
        dialogPanel.add(emailPanel);

        // ... Add other input field panels ...

        dialogPanel.add(addButton);
        addDialog.add(dialogPanel);

        // Show the dialog box
        addDialog.center();
        addDialog.show();
    }

    private void getInterviewerDataFromServer() {
        // Replace this with actual server call to fetch interviewer data
        // This is just a placeholder for demonstration purposes

        String jsonString = "[{\"name\":\"Interviewer 1\",\"numCandidatesAssigned\":2},{\"name\":\"Interviewer 2\",\"numCandidatesAssigned\":1},{\"name\":\"Interviewer 3\",\"numCandidatesAssigned\":0}]";
        
        JSONValue jsonValue = JSONParser.parseStrict(jsonString);
        JSONArray jsonArray = jsonValue.isArray();

        List<Interviewer> interviewerList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.get(i).isObject();
            String name = jsonObject.get("name").isString().stringValue();
            int numCandidatesAssigned = (int) jsonObject.get("numCandidatesAssigned").isNumber().doubleValue();
            interviewerList.add(new Interviewer(name, numCandidatesAssigned));
        }

        // Update the UI with the fetched data
        updateInterviewerData(interviewerList);
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

    private void updateInterviewerData(List<Interviewer> interviewerList) {
        for (Interviewer interviewer : interviewerList) {
            // Find the row for the interviewer in the table
            int row = findInterviewerRow(interviewer.getName());

            // If the interviewer is not found, skip the update
            if (row == -1) {
                continue;
            }

            // Update the number of candidates assigned for the interviewer
            int numCandidatesAssigned = interviewer.getNumCandidatesAssigned();
            candidateTable.setText(row, 2, String.valueOf(numCandidatesAssigned));
        }
    }

    private int findInterviewerRow(String interviewerName) {
        for (int i = 1; i < candidateTable.getRowCount(); i++) {
            if (candidateTable.getText(i, 1).equals(interviewerName)) {
                return i;
            }
        }
        return -1;
    }

    private static class Interviewer {
        private final String name;
        private final int numCandidatesAssigned;

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
