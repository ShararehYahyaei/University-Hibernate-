package org.example.service;

import org.example.config.SessionFactoryInstance;

import org.example.entity.User;
import org.example.exception.AuthenticationException;
import org.example.exception.ValidationException;
import org.example.repository.UserRepo;

import static org.example.exception.MessageValidaton.PASSWORD_DUPLICATED;
import static org.example.exception.MessageValidaton.USERNAME_DUPLICATED;

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
            throw new AuthenticationException();
        }
        return userrepo.checkUsernameAndPassword(userName, password);
    }

    public boolean isExistedUserName(String username) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            session.beginTransaction();
            boolean count = userrepo.checkUserNameIsExisted(session, username);
            if (count) {
                throw new ValidationException(USERNAME_DUPLICATED);
            }else {
                return false;
            }

        }

    }
    public boolean isExistedPassword(String password) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            session.beginTransaction();
            boolean count = userrepo.checkPasswordIsExisted(session, password);
            if (count) {
                throw new ValidationException(PASSWORD_DUPLICATED);
            }else {
                return false;
            }

        }

    }

}
