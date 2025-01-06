package org.example.service;


import jakarta.transaction.Transactional;
import org.example.config.SessionFactoryInstance;
import org.example.entity.*;
import org.example.entity.dto.StudentDto;
import org.example.entity.dtoLesson.LessonStudentDto;
import org.example.repository.TeacherRepo;
import org.example.util.Validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TeacherService {
    private final TeacherRepo teacherRepo;

    public TeacherService(TeacherRepo teacherRepo) {
        this.teacherRepo = teacherRepo;
    }

    @Transactional
    public Teacher saveTeacher(Teacher teacher) {
        Validation<User> userValidation = new Validation<>();
        Validation<Teacher> studentValidation = new Validation<>();
        Set<String> validationUser = userValidation.valid(teacher.getUser());
        validationUser.addAll(studentValidation.valid(teacher));
        if (!validationUser.isEmpty()) {
            teacherRepo.saveSTeacher(teacher);
            return teacher;
        } else {
            String validationMessage = String.join("", validationUser);
            throw new org.example.exception.ValidationException(validationMessage);

        }
    }

    public Teacher findById(Teacher teacher) {
        return teacherRepo.findById(teacher);

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
        Teacher teacherResult = teacherRepo.findById(teacher);
        List<Lesson>lessons=teacherResult.getLesson();
        Set<Student> students = new HashSet<>();
        for (int i = 0; i < lessons.size(); i++) {
          lessons.get(i).getStudents().forEach(student -> students.add(student));
        }
        return students;
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

    public void deleteTeacher(Teacher teacher) {
        Teacher res = findById(teacher);
        teacherRepo.deleteByEntity(res);
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
        Teacher teacher = teacherRepo.fetchTeacherByUserId(user);
        return teacher;
    }

    public List<Student> getMyListStudents(Teacher teacher) {
        Teacher teacherResult = teacherRepo.findById(teacher);
        List<Student> students = new ArrayList<>();
        for (Lesson l : teacherResult.getLesson()) {
            students.addAll(l.getStudents());
        }
        return students;
    }
}

//    public void saveScoreForMyStudents(Student student, Teacher teacher, Lesson lesson, double score) {
//        stdScore.saveScoreForStudent(student, teacher, lesson, score);
//    }