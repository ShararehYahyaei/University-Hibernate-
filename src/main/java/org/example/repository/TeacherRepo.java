package org.example.repository;

import org.example.entity.Student;
import org.example.entity.Teacher;
import org.hibernate.Session;

import java.util.List;

public class TeacherRepo {
    public void saveSTeacher(Session session, Teacher teacher) {
        session.persist(teacher);
    }

    public void deleteByEntity(Session session, Teacher teacher) {
        session.remove(teacher);
    }


    public Teacher findById(Session session, Long id) {
        return session.get(Teacher.class, id);

    }

    public List<Teacher> getAllTeachers(Session session) {
        return (List<Teacher>) session.createQuery("from Teacher").list();
    }

}
