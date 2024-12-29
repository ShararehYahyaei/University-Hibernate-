package org.example.repository;

import org.example.entity.Student;
import org.example.entity.StudentsScore;
import org.example.entity.Teacher;
import org.example.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class StudentRepo {

    public void saveStudent(Session session, Student student) {
        session.persist(student);
    }

    public void deleteByEntity(Session session, Student student) {
        session.remove(student);
    }


    public Student findById(Session session, Long id) {
        return session.get(Student.class, id);
    }

    public List<Student> getAllStudents(Session session) {
        return session.createQuery("from Student", Student.class).list();
    }

    public Student updatestudent(Session session, Student student) {
        session.merge(student);
        session.flush();
        return session.get(Student.class, student.getId());
    }


    public Student fetchStudentByUserId(Session session, User user) {
        Student student = session.createQuery("from Student where user = :user",Student.class)
                .setParameter("user", user).getSingleResult();

        return student;
    }


}
