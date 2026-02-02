package com.liceolapaz.bcdnob.ahorca_do.dao;

import com.liceolapaz.bcdnob.ahorca_do.database.DatabaseConnection;
import com.liceolapaz.bcdnob.ahorca_do.model.User;

import java.util.List;

public interface UsersDAOInter {
    User getUserByName(String username);
    List<User> getAllUsers();
}
