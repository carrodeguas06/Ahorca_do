package com.liceolapaz.bcdnob.ahorca_do.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "partidas")
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpartida", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idJug1", nullable = false)
    private User idJug1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idJug2")
    private User idJug2;

    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getIdJug1() {
        return idJug1;
    }

    public void setIdJug1(User idJug1) {
        this.idJug1 = idJug1;
    }

    public User getIdJug2() {
        return idJug2;
    }

    public void setIdJug2(User idJug2) {
        this.idJug2 = idJug2;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

}