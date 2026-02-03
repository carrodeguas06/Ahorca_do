package com.liceolapaz.bcdnob.ahorca_do.dao;

import com.liceolapaz.bcdnob.ahorca_do.database.DatabaseConnection;
import com.liceolapaz.bcdnob.ahorca_do.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UsersDAOInter {
    Optional<User> getUserByName(String username);

    List<User> getAllUsers();

    void save(User user) throws SQLException;

    void update(User user);

    void delete(User user);
}
