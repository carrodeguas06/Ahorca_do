package com.liceolapaz.bcdnob.ahorca_do.dao;

import com.liceolapaz.bcdnob.ahorca_do.database.DatabaseConnection;
import com.liceolapaz.bcdnob.ahorca_do.model.User;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.List;

public class UserDAO implements UsersDAOInter {

    @Override
    public User getUserByName(String username) {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("FROM User u WHERE u.nickname LIKE :nickname", User.class);
        query.setParameter("nickname", username);
        return (User) query.getSingleResult();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("FROM User", User.class);
        return query.getResultList();
    }
}
