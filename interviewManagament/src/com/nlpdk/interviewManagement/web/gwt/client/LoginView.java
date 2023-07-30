package com.nlpdk.interviewManagement.web.gwt.client;

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
	private LoginHandler loginHandler;

	public LoginView(LoginHandler loginHandler) {
		this.loginHandler = loginHandler;

		Label emailLabel = new Label("Email:");
		emailTextBox = new TextBox();
		Label passwordLabel = new Label("Password:");
		passwordTextBox = new PasswordTextBox();
		loginButton = new Button("Login");

		VerticalPanel loginPanel = new VerticalPanel();
		loginPanel.add(new HTML("<h2>Login</h2>"));
		loginPanel.add(emailLabel);
		loginPanel.add(emailTextBox);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordTextBox);
		loginPanel.add(loginButton);

		initWidget(loginPanel);
		loginButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doLogin();
			}
		});
	}

	private void doLogin() {
		String email = emailTextBox.getValue();
		String password = passwordTextBox.getValue();

		JSONObject loginDataObject = new JSONObject();
		loginDataObject.put("email", new JSONString(email));
		loginDataObject.put("password", new JSONString(password));
		String loginData = loginDataObject.toString();

		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST,
				"http://localhost:8080/IMSApi/api?action=login");
		try {

			builder.sendRequest(loginData, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {

					if (response.getStatusCode() == 200 && !response.getText().contains("Invalid")) {

						Users user = parseUserJSON(response.getText());
						loginHandler.onLoginSuccess(user);
					} else {

						loginHandler.onLoginFailure();
					}
				}

				@Override
				public void onError(Request request, Throwable throwable) {

					loginHandler.onLoginFailure();
				}
			});
		} catch (RequestException e) {

			loginHandler.onLoginFailure();
		}
	}

	private Users parseUserJSON(String responseText) {

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
