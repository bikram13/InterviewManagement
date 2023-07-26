package com.nlpdk.interviewManagement.web.gwt.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

public class APIService {
	static Object result = null;

	public static String URL = "http://localhost:8080/IMSApi/api";

	public Object httpRequest(String action, String requestData, Method httpMethod) {
		URL += "?action=" + action;
		RequestBuilder builder = new RequestBuilder(httpMethod, URL);

		try {
			Request response = builder.sendRequest(requestData, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					// Code omitted for clarity
					System.out.println(exception.getMessage());

				}

				public void onResponseReceived(Request request, Response response) {
					// Code omitted for clarity
					System.out.println(response.getText());
					result = response.getText();

				}
			});

		} catch (RequestException e) {
			// Code omitted for clarity
			System.out.println(e.getMessage());

		}
		return result;
	}

}
