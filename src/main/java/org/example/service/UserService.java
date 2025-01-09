package org.example.service;

import org.example.config.SessionFactoryInstance;

import org.example.entity.User;
import org.example.repository.UserRepo;

public class UserService {

    private final UserRepo userrepo;

    public UserService(UserRepo userRepo) {
        this.userrepo = userRepo;
    }

    public User saveAdmin(User user) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.getTransaction().commit();
                return userrepo.saveAdmin(session, user);
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
            throw new IllegalArgumentException("Username or password cannot be null or empty");
        }
        return userrepo.checkUsernameAndPassword(userName, password);

    }
}
