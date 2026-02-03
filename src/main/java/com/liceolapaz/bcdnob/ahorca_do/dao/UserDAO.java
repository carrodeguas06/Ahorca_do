package com.liceolapaz.bcdnob.ahorca_do.dao;

import com.liceolapaz.bcdnob.ahorca_do.database.DatabaseConnection;
import com.liceolapaz.bcdnob.ahorca_do.model.User;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDAO implements UsersDAOInter {

    @Override
    public Optional<User> getUserByName(String nickname) {
        try (Session session = DatabaseConnection.getSessionFactory().openSession()) {
            Query query = session.createQuery("FROM User u WHERE u.nickname = :nickname", User.class);
            query.setParameter("nickname", nickname);
            return Optional.ofNullable((User) query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = DatabaseConnection.getSessionFactory().openSession();
        Query query = session.createQuery("FROM User", User.class);
        return query.getResultList();
    }

    public void save(User user) throws SQLException {
      try(Session session = DatabaseConnection.getSessionFactory().openSession()){
          session.beginTransaction();
          session.persist(user);
          session.getTransaction().commit();
      }

    }

    public void update(User user) {
        try (Session session = DatabaseConnection.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        }
    }

    public void delete(User user) {
        try (Session session = DatabaseConnection.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.remove(session.contains(user) ? user : session.merge(user));
            session.getTransaction().commit();
        }

    }
}
