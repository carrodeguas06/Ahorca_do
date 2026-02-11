package com.liceolapaz.bcdnob.ahorca_do.dao;

import com.liceolapaz.bcdnob.ahorca_do.database.DatabaseConnection;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class PartidaDAO {
    public long obtenerPuntuacionTotal(int id) {
        long total = 0;
        try (Session session = DatabaseConnection.getSessionFactory().openSession()) {
            String hql = ("Select SUM(p.puntuacion) from Partida p where p.idJug1 = :id");
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("id", id);

            Long resultado = query.uniqueResult();

            if (resultado != null) {
                total = resultado;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
}
