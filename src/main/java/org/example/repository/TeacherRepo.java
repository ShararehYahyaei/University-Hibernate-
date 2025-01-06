package org.example.repository;


import org.example.config.SessionFactoryInstance;
import org.example.entity.Teacher;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TeacherRepo {

    public Teacher saveSTeacher(Teacher teacher) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.persist(teacher);
                session.getTransaction().commit();
                return teacher;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void deleteByEntity(Teacher teacher) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.remove(teacher);
                session.flush();
            } catch (Exception e) {
            }
        }
    }


    public Teacher findById(Teacher teacher) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            session.beginTransaction();
            return session.byId(Teacher.class).load(teacher);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Teacher updateTeacher(Session session, Teacher teacher) {
        session.merge(teacher);
        session.flush();
        return session.get(Teacher.class, teacher.getId());
    }

    public List<Teacher> getAllTeachers(Session session) {
        String hql = "from Teacher";
        Query<Teacher> query = session.createQuery(hql, Teacher.class);
        return query.getResultList();
    }


    public Teacher fetchTeacherByUserId(User user) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            session.beginTransaction();
            session.getTransaction().commit();
            return session.createQuery("from Teacher where user = :user", Teacher.class)
                    .setParameter("user", user).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}