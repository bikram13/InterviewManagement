package com.ims.service;

import com.ims.dao.FeedbackDAO;
import com.ims.model.Feedback;

import java.util.List;

public class FeedbackService {
    private FeedbackDAO feedbackDAO;

    public FeedbackService() {
        feedbackDAO = new FeedbackDAO();
    }

    public void addFeedback(Feedback feedback) {
        feedbackDAO.addFeedback(feedback);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackDAO.getAllFeedbacks();
    }

    // Implement other methods as needed

    // ...
}
