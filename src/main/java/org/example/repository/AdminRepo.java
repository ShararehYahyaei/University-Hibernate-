package org.example.repository;


import org.example.entity.AdminUser;
import org.hibernate.Session;

public class AdminRepo {
    public boolean validateLogin(Session session, String username, String password) {
        String hql = "FROM AdminUser a WHERE a.username = :username AND a.password = :password";
        AdminUser admin = session.createQuery(hql, AdminUser.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .uniqueResult();
        if (admin != null) {
            return true;
        } else {
            return false;
        }
    }
}



