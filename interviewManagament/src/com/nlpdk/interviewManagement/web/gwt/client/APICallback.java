package com.nlpdk.interviewManagement.web.gwt.client;

public interface APICallback {
	void onSuccess(String responseText);

	void onFailure(String errorMessage);
}
