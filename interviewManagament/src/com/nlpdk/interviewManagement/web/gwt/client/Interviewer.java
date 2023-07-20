package com.nlpdk.interviewManagement.web.gwt.client;
public class Interviewer {
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
