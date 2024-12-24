package org.example.service;


import jakarta.transaction.Transactional;
import org.example.config.SessionFactoryInstance;
import org.example.entity.*;
import org.example.repository.TeacherRepo;
import org.example.util.Validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TeacherService {
    private final static TeacherRepo teacherRepo = new TeacherRepo();

    @Transactional
    public Teacher saveTeacher(Teacher teacher) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Validation<User> userValidation = new Validation<>();
                Validation<Teacher> studentValidation = new Validation<>();
                Set<String> validationUser = userValidation.valid(teacher.getUser());
                validationUser.addAll(studentValidation.valid(teacher));
                if (!validationUser.isEmpty()) {
                    validationUser.forEach(System.out::println);
                    return null;
                }
                teacherRepo.saveSTeacher(session, teacher);
                return teacher;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }

    }

    public Teacher findById(Long id) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Teacher teacher = teacherRepo.findById(session, id);
                session.getTransaction().commit();
                return teacher;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    public Teacher teacherUpdate(Teacher teacher) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            session.beginTransaction();
            Validation<User> userValidation = new Validation<>();
            Validation<Teacher> teacherValidation = new Validation<>();
            Set<String> validationUser = userValidation.valid(teacher.getUser());
            validationUser.addAll(teacherValidation.valid(teacher));
            if (!validationUser.isEmpty()) {
                validationUser.forEach(System.out::println);
            }
            teacherRepo.updateTeacher(session, teacher);
            return teacher;
        }
    }

    public Set<Student> getMyStudents(Teacher teacher) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            session.beginTransaction();
            Teacher teacherResult = teacherRepo.findById(session, teacher.getId());
            Set<Student> students = new HashSet<>();
            for (Lesson l : teacherResult.getLesson()) {
                students.addAll(l.getStudents());
            }
            return students;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> putScorePerLesson(Teacher teacher, Score score) {
        List<Student> students = new ArrayList<>();
        for (Lesson l : teacher.getLesson()) {
            for (Student s : l.getStudents()) {
                students.add(s);
            }
        }
        for (Student s : students) {
            s.getLesson().get(0).setScore(score);
        }
        return students;
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

    public List<org.example.entity.dtoTeacher.TeacherDto> getAllTeachersWithSOmeProperties() {
        List<Teacher> teachers = findAll();
        List<org.example.entity.dtoTeacher.TeacherDto> simpleTeachers = new ArrayList<org.example.entity.dtoTeacher.TeacherDto>();
        for (Teacher teacher : teachers) {
            org.example.entity.dtoTeacher.TeacherDto dtoTeacher = new   org.example.entity.dtoTeacher.TeacherDto ();
            dtoTeacher.setEmployeeCode(teacher.getEmployeeCode());
            dtoTeacher.setSpecialty(teacher.getSpecialty());
            dtoTeacher.setFirstName(teacher.getUser().getName().getFirstName());
            dtoTeacher.setLastName(teacher.getUser().getName().getLastName());
            simpleTeachers.add(dtoTeacher);

        }
        return simpleTeachers;
    }

}
