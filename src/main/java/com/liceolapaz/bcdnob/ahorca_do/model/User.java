package com.liceolapaz.bcdnob.ahorca_do.model;

import com.liceolapaz.bcdnob.ahorca_do.database.EncryptionConverter;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    @Convert(converter = EncryptionConverter.class)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    @Convert(converter = EncryptionConverter.class)
    private String apellido;

    @Column(name = "nickname", nullable = false)
    @Convert(converter = EncryptionConverter.class)
    private String nickname;

    @Column(name = "password", nullable = false)
    @Convert(converter = EncryptionConverter.class)
    private String password;

    @Column(name = "admin", nullable = false)
    private Boolean admin = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public User() {}

    public User(String nickname, String password, Boolean admin) {
        this.nickname = nickname;
        this.password = password;
        this.admin = admin;
        this.nombre = nickname;
        this.apellido = "";
    }

    public User(Integer id, String nombre, String apellido, String nickname, String password, Boolean admin) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickname = nickname;
        this.password = password;
        this.admin = admin;
    }


}