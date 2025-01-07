package org.example.service;


import org.example.config.SessionFactoryInstance;
import org.example.entity.*;
import org.example.entity.dto.StudentDto;
import org.example.entity.dtoLesson.LessonStudentDto;
import org.example.repository.StudentRepo;
import org.example.repository.TeacherRepo;
import org.example.util.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class TeacherService {
    private final TeacherRepo teacherRepo;
    private final StudentScoreService studentScoreService;


    public TeacherService(TeacherRepo teacherRepo, StudentScoreService studentScoreService) {
        this.teacherRepo = teacherRepo;
        this.studentScoreService = studentScoreService;
    }


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
                return teacherRepo.saveSTeacher(session, teacher);
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
            return teacherRepo.updateTeacher(session, teacher);

        }
    }

    public Set<Student> getMyStudents(Teacher teacher) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            session.beginTransaction();
            Teacher teacherResult = teacherRepo.findById(session, teacher.getId());
            Set<Student> students = teacherResult
                    .getLesson()
                    .stream()
                    .flatMap(lesson ->
                            lesson.getStudents()
                                    .stream())
                    .collect(Collectors.toSet());
            return students;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<LessonStudentDto> getLessonsStudentsDto(Teacher teacherForLesson) {
        List<Lesson> lessons = teacherForLesson.getLesson();
        List<LessonStudentDto> lessonStudentsDto = new ArrayList<>();
        for (Lesson lesson : lessons) {
            List<StudentDto> dtos = new ArrayList<>();
            List<Student> std = lesson.getStudents();
            for (Student student : std) {
                dtos.add(new StudentDto(student.getStudentNumber(), student.getUser().getName().getFirstName(), student.getUser().getName().getLastName()));
            }
            LessonStudentDto dto = new LessonStudentDto(lesson.getCourseName(), lesson.getCapacity(), dtos);
            lessonStudentsDto.add(dto);

        }
        return lessonStudentsDto;
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


    public List<Lesson> getAllLessonsForATeacher(Teacher teacher) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            session.beginTransaction();
            Teacher teacherResult = teacherRepo.findById(session, teacher.getId());
            List<Lesson> lstOfLessons = teacherResult.getLesson();
            session.getTransaction().commit();
            return lstOfLessons;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<org.example.entity.dtoTeacher.TeacherDto> getAllTeachersWithSOmeProperties() {
        List<Teacher> teachers = findAll();
        List<org.example.entity.dtoTeacher.TeacherDto> simpleTeachers = new ArrayList<org.example.entity.dtoTeacher.TeacherDto>();
        for (Teacher teacher : teachers) {
            org.example.entity.dtoTeacher.TeacherDto dtoTeacher = new org.example.entity.dtoTeacher.TeacherDto();
            dtoTeacher.setEmployeeCode(teacher.getEmployeeCode());
            dtoTeacher.setSpecialty(teacher.getSpecialty());
            dtoTeacher.setFirstName(teacher.getUser().getName().getFirstName());
            dtoTeacher.setLastName(teacher.getUser().getName().getLastName());
            simpleTeachers.add(dtoTeacher);

        }
        return simpleTeachers;
    }

    public Teacher fetchByUserId(User user) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            session.beginTransaction();
            Teacher teacher = teacherRepo.fetchTeacherByUserId(session, user);
            session.getTransaction().commit();
            return teacher;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveScoreForMyStudents(Student student, Teacher teacher, Lesson lesson, double score) {
        studentScoreService.saveScoreForStudent(student, teacher, lesson, score);
    }

    public double getScoreForStudent(Student student, Teacher teacher, Lesson lesson) {
        double score = studentScoreService.getScore(student.getId(), teacher.getId(), lesson.getId());
        return score;
    }

}
