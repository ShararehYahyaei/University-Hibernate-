package org.example.repository;


import org.example.entity.Teacher;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TeacherRepo {

    public Teacher saveSTeacher(Session session, Teacher teacher) {
        session.persist(teacher);
        return teacher;

    }

    public void deleteByEntity(Session session, Teacher teacher) {
        session.remove(teacher);
        session.flush();
    }


    public Teacher findById(Session session, Long id) {
        return session.get(Teacher.class, id);

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


    public Teacher fetchTeacherByUserId(Session session, User user) {
        return session.createQuery("from Teacher where user = :user", Teacher.class)
                .setParameter("user", user).getSingleResult();
    }
}
