package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.Teacher;
import org.example.repository.TeacherRepo;
import org.example.util.Validation;
import java.util.List;


public class TeacherService {
    private final static TeacherRepo teacherRepo = new TeacherRepo();

    public  Teacher saveTeacher(Teacher teacher) {
        return getTeacher(teacher);
    }

    private Teacher getTeacher(Teacher teacher) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Validation<Teacher> studentValidation = new Validation<>();
                if (studentValidation.valid(teacher).isEmpty()) {
                    teacherRepo.saveSTeacher(session, teacher);
                } else {
                    studentValidation.valid(teacher).forEach(System.out::println);
                }
                session.getTransaction().commit();
                return teacher;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    public Teacher update(Teacher teacher) {
        return getTeacher(teacher);
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
    public String deleteTeacher(Long id){
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try{
                session.beginTransaction();
                teacherRepo.deleteById(session, id);
                session.getTransaction().commit();
                return "Delete Successfully ...";

            }catch (Exception e){
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }

    }

}
