package com.ims.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.ims.entity.CandidateInterviewer;
import com.ims.entity.Users;
import com.ims.model.InterviewerCandidateCount;
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
	
	public List<InterviewerCandidateCount> getCandidateCountAssignedToInterviewers() {
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	Transaction transaction = session.beginTransaction();

    	try {
    	    String hql = "SELECT ci.interviewer.id, ci.interviewer.firstName, COUNT(ci.candidate) " +
    	                 "FROM CandidateInterviewer ci " +
    	                 "RIGHT JOIN ci.interviewer " +
    	                 "WHERE ci.feedbackStatus = 'NOT_SUBMITTED' " +
    	                 "GROUP BY ci.interviewer.id, ci.interviewer.firstName";

    	    Query<Object[]> query = session.createQuery(hql, Object[].class);
    	    List<Object[]> results = query.getResultList();
    	    
    	    List<InterviewerCandidateCount> list = new ArrayList<>();

    	    for (Object[] result : results) {
    	    	Long interviewerId = (Long) result[0];
    	        String interviewerName = (String) result[1];
    	        Long numCandidatesAssigned = (Long) result[2];

    	        // Do whatever you want with the results (e.g., print them)
    	        
    	        InterviewerCandidateCount interviewerCandidateCount = new InterviewerCandidateCount(interviewerId, interviewerName,numCandidatesAssigned);
    	        list.add(interviewerCandidateCount);
    	        System.out.println("Interviewer ID: " + interviewerCandidateCount.getInterviewerId() +
    	                           ", Interviewer Name: " + interviewerCandidateCount.getInterviewerName() +
    	                           ", Num Candidates Assigned: " + interviewerCandidateCount.getCandidateAssignedCount());
    	    }

    	    transaction.commit();
    	    return list;
    	} catch (Exception e) {
    	    transaction.rollback();
    	    e.printStackTrace();
    	} finally {
    	    session.close();
    	}
    	return new ArrayList<>();

    }

	// New Method added for All Candidates
	public List<Users> getAssignedInterviewersToTheCandidate(Long candidateId) {
    	 List<Users> listUsers = null;
    	  try (Session session = HibernateUtil.getSessionFactory().openSession()) {
    		  String strQuery = "  from  CandidateInterviewer  where candidate.id =: candidateId  ";
    		  Query query = session.createQuery(strQuery);
         	 query.setParameter("candidateId", candidateId);
         	 List<CandidateInterviewer>  listCandidateInterviewer =  query.getResultList();
         	listUsers = new ArrayList<>();
         	for(CandidateInterviewer candInter: listCandidateInterviewer) {
         		listUsers.add(candInter.getInterviewer());
         	}
          	return listUsers;
    	  } catch (Exception e) {
              e.printStackTrace();
              return null;
          }
	}
	
	public CandidateInterviewer getCandidateInterviewersByCandidateIdandInterviewerId(Long canidateId,
			Long interviewerUserId) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// The following HQL query fetches the candidateInterviewers associated with the
			// given interviewerId
			String hql = "SELECT ci FROM CandidateInterviewer ci WHERE ci.interviewer.id = :interviewerUserId and ci.candidate.id = :canidateId";
			Query<CandidateInterviewer> query = session.createQuery(hql, CandidateInterviewer.class);
			query.setParameter("canidateId", canidateId);
			query.setParameter("interviewerUserId", interviewerUserId);
			return query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
