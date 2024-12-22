package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.Student;
import org.example.entity.Teacher;
import org.example.entity.User;
import org.example.repository.TeacherRepo;
import org.example.util.Validation;
import java.util.List;


public class TeacherService {
    private final static TeacherRepo teacherRepo = new TeacherRepo();

    public Teacher saveTeacher(Teacher teacher) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Validation<User> userValidation = new Validation<>();
                Validation<Teacher> teacherValidation = new Validation<>();
                if (!userValidation.valid(teacher.getUser()).isEmpty()) {
                    userValidation.valid(teacher.getUser()).forEach(System.out::println);
                    return null;
                }
                if (!teacherValidation.valid(teacher).isEmpty()) {
                    teacherValidation.valid(teacher).forEach(System.out::println);
                    return null;
                } else {
                    teacherRepo.saveSTeacher(session, teacher);
                    return teacher;
                }
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }

    }

    public  Teacher findById(Long id) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
              Teacher teacher= teacherRepo.findById(session, id);
                session.getTransaction().commit();
                return teacher;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }
    public List<Teacher> findAll() {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                List<Teacher> teachers = teacherRepo.getAllTeachers(session);
                session.getTransaction().commit();
                return teachers;

            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }
    public void deleteTeacher(Long id) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Teacher teacher = teacherRepo.findById(session, id);
                teacherRepo.deleteByEntity(session, teacher);
                session.getTransaction().commit();

            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }

    }

}
