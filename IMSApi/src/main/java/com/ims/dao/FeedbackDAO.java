package com.ims.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ims.entity.Feedback;
import com.ims.util.HibernateUtil;

public class FeedbackDAO {

	public boolean addFeedback(Feedback feedback) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(feedback);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public Feedback getFeedbackById(int id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Feedback.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Feedback> getAllFeedbacks() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Feedback", Feedback.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Feedback getFeedbackByCandidateInterviewerId(Long candidateInterviewerId) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session
					.createQuery("from Feedback where candidateInterviewer.id = :candidateInterviewerId",
							Feedback.class)
					.setParameter("candidateInterviewerId", candidateInterviewerId).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void updateFeedback(Feedback feedback) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.update(feedback);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void deleteFeedback(int id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Feedback feedback = session.get(Feedback.class, id);
			if (feedback != null) {
				session.delete(feedback);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}
}
