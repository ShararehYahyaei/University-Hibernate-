package org.example.repository;

import org.example.entity.Student;
import org.example.entity.Teacher;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class TeacherRepo {
    public Teacher saveSTeacher(Session session, Teacher teacher) {
        session.persist(teacher);
        return teacher;
    }

    public int deleteById(Session session, Long id) {
        return session.createMutationQuery(
                        "DELETE FROM org.example.entity.Teacher t WHERE t.id = :id"
                )
                .setParameter("id", id)
                .executeUpdate();
    }

    public void update(Session session, Teacher teacher) {
        session.persist(teacher);
    }

    public Teacher findById(Session session, Long id) {
        return session.get(Teacher.class, id);

    }

    public List<Teacher> getAllPhoneBook(Session session) {
        return (List<Teacher>) session.createQuery("from Teacher").list();
    }

}
