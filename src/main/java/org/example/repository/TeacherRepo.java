package org.example.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.entity.Teacher;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TeacherRepo {
    @PersistenceContext
    private EntityManager entityManager;

    public void saveSTeacher(Session session, Teacher teacher) {
        session.persist(teacher);
        session.merge(teacher);
        session.flush();

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


}
