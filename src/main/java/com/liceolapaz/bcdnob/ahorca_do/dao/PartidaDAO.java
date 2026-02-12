package com.liceolapaz.bcdnob.ahorca_do.dao;

import com.liceolapaz.bcdnob.ahorca_do.database.DatabaseConnection;
import com.liceolapaz.bcdnob.ahorca_do.model.Partida;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class PartidaDAO {

    public long obtenerPuntuacionTotal(int idUsuario) {
        long total = 0;
        try (Session session = DatabaseConnection.getSessionFactory().openSession()) {
            String hql = "SELECT SUM(p.puntuacion) FROM Partida p WHERE p.idJug1.id = :id";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("id", idUsuario);

            Long resultado = query.uniqueResult();
            if (resultado != null) {
                total = resultado;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    public void guardarPartida(Partida partida) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = DatabaseConnection.getSessionFactory().openSession();
            transaction = session.beginTransaction();


            if (partida.getIdJug1() != null) {
                partida.setIdJug1(session.merge(partida.getIdJug1()));
            }

            if (partida.getIdJug2() != null) {
                partida.setIdJug2(session.merge(partida.getIdJug2()));
            }

            if (partida.getGanador() != null) {
                partida.setGanador(session.merge(partida.getGanador()));
            }

            session.persist(partida);

            transaction.commit();
            System.out.println("Partida guardada correctamente.");

        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}