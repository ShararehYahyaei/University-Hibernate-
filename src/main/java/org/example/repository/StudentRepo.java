package org.example.repository;

import org.example.entity.Student;
import org.hibernate.Session;
import java.util.List;
import java.util.Optional;

public class StudentRepo {

    public Student saveStudent(Session session, Student student) {
        session.persist(student);
        return student;
    }

    public int deleteById(Session session, Long id) {
        return session.createMutationQuery(
                        "DELETE FROM org.example.entity.Student s WHERE s.id = :id"
                )
                .setParameter("id", id)
                .executeUpdate();
    }


    public Optional<Student> findById(Session session, Long id) {
        return session.byId(Student.class).loadOptional(id);

    }

    public List<Student> getAllStudents(Session session) {
        return (List<Student>) session.createQuery("from Student").list();
    }


}
