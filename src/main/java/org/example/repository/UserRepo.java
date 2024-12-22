package org.example.repository;

import org.example.entity.Student;
import org.example.entity.User;
import org.hibernate.Session;

import java.util.List;

public class UserRepo {
    public User saveUser(Session session, User user) {
        session.persist(user);
        return user;
    }

    public List<User> getAllUsers(Session session) {
        return session.createQuery("FROM org.example.entity.User", User.class).getResultList();
    }

    public User findById(Session session, Long id) {
        return session.get(User.class, id);
    }

}
