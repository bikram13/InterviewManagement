package com.nlpdk.interviewManagement.web.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class InterviewManagament implements EntryPoint {
	

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		CandidateListPage candidateListPage=  new CandidateListPage();
		RootPanel.get().add(candidateListPage);;
		
	}
}
