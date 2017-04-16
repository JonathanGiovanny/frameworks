/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author RA302
 */
public class HibernateUtil {

    private static Session session = null;

    public static void start() {
        if (session == null) {
            session = new Configuration().configure().buildSessionFactory().openSession();
            session.beginTransaction();
        } else {
            if (!session.isOpen()) {
                session.beginTransaction();
            }
        }
    }

    public static void close() {
        if (session != null) {
            session.close();
        }
    }

    public static void commit() {
        session.getTransaction().commit();
        System.out.println("Haciendo Commit");

    }

    public static Session getSession() {
        return session;
    }
    
    public static void setSession(){
        session = null;
    }

}
