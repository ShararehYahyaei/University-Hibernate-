package org.example.repository;

import org.example.config.SessionFactoryInstance;
import org.example.entity.User;
import org.hibernate.Session;

import java.util.List;

public class UserRepo {
    public User saveAdmin(Session session, User user) {
        session.persist(user);
        return user;
    }

    public List<User> getAllUsers(Session session) {
        return session.createQuery("FROM org.example.entity.User", User.class).getResultList();
    }

    public User findById(Session session, Long id) {
        return session.get(User.class, id);
    }

    public User checkUsernameAndPassword(String userName, String password) {
        try (Session session1 = SessionFactoryInstance.sessionFactory.openSession()) {
            session1.beginTransaction();
            String hql = "from User where userName=:userName ";
            User user = session1.createQuery(hql, User.class).setParameter("userName", userName).getSingleResultOrNull();
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }

        }
        return null;
    }

    public boolean checkUserNameIsExisted(Session session, String userName) {
        Long count;
        count = session.createQuery(
                        "SELECT COUNT(u.id) FROM User u WHERE u.userName = :userName", Long.class)
                .setParameter("userName", userName)
                .uniqueResult();
        if (count > 0) {
            return true;
        }
        return false;

    }
    public boolean checkPasswordIsExisted(Session session, String password) {
        Long count;
        count = session.createQuery(
                        "SELECT COUNT(u.id) FROM User u WHERE u.password = :password", Long.class)
                .setParameter("password", password)
                .uniqueResult();
        if (count > 0) {
            return true;
        }
        return false;

    }

}
