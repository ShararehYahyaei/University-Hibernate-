package org.example.service;


import org.example.config.SessionFactoryInstance;
import org.example.entity.Student;
import org.example.entity.User;
import org.example.repository.StudentRepo;
import org.example.repository.UserRepo;
import org.example.util.Validation;

import java.util.List;

public class StudentService {
    private final static StudentRepo studentRepo = new StudentRepo();
    private final static UserRepo userrepo = new UserRepo();

    public Student save(Student student) {
        return getStudent(student);
    }

    private Student getStudent(Student student) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Validation<Student> studentValidation = new Validation<>();
                if (studentValidation.valid(student).isEmpty()) {
                    User user = student.getUser();
                    //   User userResult= userrepo.saveUser(session,user);
                    //   student.setUser(userResult);
                    studentRepo.saveStudent(session, student);
                } else {
                    studentValidation.valid(student).forEach(System.out::println);
                }

                session.getTransaction().commit();
                return student;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    public Student update(Student student) {
        return getStudent(student);
    }

    public Student findById(Long id) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
              Student student = studentRepo.findById(session, id);
                session.getTransaction().commit();
                return student;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    public List<Student> findAll() {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                List<Student> students = studentRepo.getAllStudents(session);
                session.getTransaction().commit();
                return students;

            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteStudent(Long id) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Student student = studentRepo.findById(session, id);
                studentRepo.deleteByEntity(session,student);
                session.getTransaction().commit();

            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }

    }
}
