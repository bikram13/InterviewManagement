package com.ims.util;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.ims.model.Candidate;
import com.ims.model.CandidateInterviewer;
import com.ims.model.Feedback;
import com.ims.model.Users;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the Configuration object and set properties
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:h2:tcp://localhost/~/test");
            configuration.setProperty("hibernate.connection.username", "sa");
            configuration.setProperty("hibernate.connection.password", "");
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//            configuration.setProperty("hibernate.hbm2ddl.auto", "update");

            // Add entity classes
            configuration.addAnnotatedClass(Users.class); 
            configuration.addAnnotatedClass(Candidate.class);
            configuration.addAnnotatedClass(CandidateInterviewer.class);
            configuration.addAnnotatedClass(Feedback.class);

            
            // Build the StandardServiceRegistry using the Configuration
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            // Build the SessionFactory
            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception ex) {
            System.err.println("SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
