package org.example.repository;

import org.example.entity.Student;
import org.example.entity.User;
import org.hibernate.Session;

public class UserRepo {
    public User saveUser(Session session, User user) {
        session.persist(user);
        return user;
    }
}
