package com.liceolapaz.bcdnob.ahorca_do.service;

import com.liceolapaz.bcdnob.ahorca_do.dao.UserDAO;
import com.liceolapaz.bcdnob.ahorca_do.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Base64;

public class UserService {
    private UserDAO userDAO= new UserDAO();

    public Optional<User> login(String nickname, String password){
        Optional<User> userOpt = userDAO.getUserByName(nickname);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (password.equals(user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public void registrarusuario(String nickname, String password, Boolean admin) throws Exception {
        if(userDAO.getUserByName(nickname).isPresent()){
        throw new Exception("This nickname is already taken");
        }

        String hashedPassword= hashPassword(password);
        User user= new User(nickname, hashedPassword, admin);
        userDAO.save(user);
    }

    public List<User> obtenerTodosLosUsuarios(){
        return userDAO.getAllUsers();
    }

    public void actualizarUsuario(User user) throws SQLException{
        userDAO.update(user);
    }

    public void actualizarUsuario(User user, String newPassword) throws SQLException{
        if(newPassword!=null && !newPassword.isEmpty()){
            user.setPassword(hashPassword(newPassword));
        }
        userDAO.update(user);
    }

    public void eliminarUsuario(User user) throws SQLException{
        userDAO.delete(user);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        }catch (NoSuchAlgorithmException e){
            throw  new RuntimeException("Hash error", e);
        }
    }
    private boolean verifyPassword (String inputPassword, String storedHash) {
        return hashPassword(inputPassword).equals(storedHash);
    }
}
