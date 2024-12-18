package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.AdminUser;

public class AdminService {
    public boolean validateLogin(String username, String password) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
