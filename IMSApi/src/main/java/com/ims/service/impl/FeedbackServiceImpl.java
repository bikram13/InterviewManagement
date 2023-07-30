package com.ims.service.impl;

import com.ims.dao.FeedbackDAO;
import com.ims.entity.Feedback;

import java.util.List;

public class FeedbackServiceImpl {
    private FeedbackDAO feedbackDAO;

    public FeedbackServiceImpl() {
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
