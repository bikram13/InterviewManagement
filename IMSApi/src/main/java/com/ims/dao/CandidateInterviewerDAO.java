package com.ims.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ims.entity.CandidateInterviewer;
import com.ims.util.HibernateUtil;

public class CandidateInterviewerDAO {

	public void addCandidateInterviewer(CandidateInterviewer candidateInterviewer) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(candidateInterviewer);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public CandidateInterviewer getCandidateInterviewerById(int id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(CandidateInterviewer.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<CandidateInterviewer> getAllCandidateInterviewers() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from CandidateInterviewer", CandidateInterviewer.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<CandidateInterviewer> getAllCandidateInterviewersByCandidateId(Long candidateId) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from CandidateInterviewer where candidate.id = :candidateId",
					CandidateInterviewer.class).setParameter("candidateId", candidateId).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void updateCandidateInterviewer(CandidateInterviewer candidateInterviewer) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.update(candidateInterviewer);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void deleteCandidateInterviewer(int id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			CandidateInterviewer candidateInterviewer = session.get(CandidateInterviewer.class, id);
			if (candidateInterviewer != null) {
				session.delete(candidateInterviewer);
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
