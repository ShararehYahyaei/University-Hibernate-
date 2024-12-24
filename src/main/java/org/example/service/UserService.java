package org.example.service;

import org.example.config.SessionFactoryInstance;

import org.example.entity.User;
import org.example.repository.UserRepo;

public class UserService {

    private static final UserRepo userrepo = new UserRepo();


    public void saveAdmin(User user) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                userrepo.saveAdmin(session, user);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    public User findById(Long id) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                User user = userrepo.findById(session, id);
                session.getTransaction().commit();
                return user;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }


    public User checkUsernameAndPassword(String userName, String password) {
        if (userName.trim().isEmpty() || password.trim().isEmpty()) {
            return null;
        }
        return userrepo.checkUsernameAndPassword(userName, password);

    }
}
