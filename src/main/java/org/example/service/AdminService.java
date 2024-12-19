package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.repository.AdminRepo;

public class AdminService {
    private final static AdminRepo adminRepo = new AdminRepo();

    public boolean validateLogin(String username, String password) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                boolean res = adminRepo.validateLogin(session, username, password);
                if (res) {
                    return true;
                } else {
                    session.getTransaction().rollback();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}