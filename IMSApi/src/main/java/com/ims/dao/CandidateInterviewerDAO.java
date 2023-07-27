package com.ims.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

	public List<CandidateInterviewer> getAllCandidateInterviewersByInterviewerId(Long interviewerId) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// The following HQL query fetches the candidateInterviewers associated with the
			// given interviewerId
			String hql = "SELECT ci FROM CandidateInterviewer ci WHERE ci.interviewer.id = :interviewerId";
			Query<CandidateInterviewer> query = session.createQuery(hql, CandidateInterviewer.class);
			query.setParameter("interviewerId", interviewerId);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
