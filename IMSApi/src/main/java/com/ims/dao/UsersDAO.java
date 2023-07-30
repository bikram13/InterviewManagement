package com.ims.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.ims.entity.Users;
import com.ims.util.HibernateUtil;

public class UsersDAO {

	public void addUser(Users user) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(user);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public Users getUserById(long id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Users.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Users> getAllUsers() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Users", Users.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void updateUser(Users user) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.update(user);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public void deleteUser(int id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Users user = session.get(Users.class, id);
			if (user != null) {
				session.delete(user);
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public Users getUserByEmailAndPassword(String email, String password) {

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "FROM Users WHERE emailId = :email AND password = :password";
			Query<Users> query = session.createQuery(hql, Users.class);
			query.setParameter("email", email);
			query.setParameter("password", password);
			List<Users> usersList = query.list();
			return usersList.isEmpty() ? null : usersList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Users> getInterviewersByCandidateId(Long candidateId) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// The following HQL query fetches the interviewers associated with the given
			// candidateId
			String hql = "SELECT u FROM Users u, CandidateInterviewer ci WHERE ci.candidate.id = :candidateId AND ci.interviewer.id = u.id";
			Query<Users> query = session.createQuery(hql, Users.class);
			query.setParameter("candidateId", candidateId);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
