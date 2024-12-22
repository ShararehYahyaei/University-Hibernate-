package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.Student;
import org.example.entity.User;
import org.example.repository.UserRepo;
import org.example.util.Validation;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final UserRepo userrepo = new UserRepo();

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


}
