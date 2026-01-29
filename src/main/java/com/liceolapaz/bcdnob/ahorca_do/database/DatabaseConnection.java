package com.liceolapaz.bcdnob.ahorca_do.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseConnection {

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    private Session session = sessionFactory.openSession();

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Session getSession() {
        return session;
    }
}
