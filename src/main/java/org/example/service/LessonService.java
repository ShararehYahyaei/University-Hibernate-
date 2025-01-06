package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.*;
import org.example.repository.LessonRepo;
import org.example.util.Validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class LessonService {
    private final LessonRepo lessonRepo;

    public LessonService(LessonRepo lessonRepo) {
        this.lessonRepo = lessonRepo;
    }

    public Lesson saveLesson(Lesson lesson) {

        Validation<Lesson> studentValidation = new Validation<>();
        Set<String> studentValidationResult = studentValidation.valid(lesson);
        if (studentValidationResult.isEmpty()) {
            lessonRepo.saveLesson(lesson);
        } else {
            String validationMessage = String.join("", studentValidationResult);
            throw new org.example.exception.ValidationException(validationMessage);
        }
        return lesson;
    }

    public Lesson findById(Long id) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Lesson lesson = lessonRepo.findById(session, id);
                session.getTransaction().commit();
                return lesson;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    public List<Lesson> findAll() {
        return lessonRepo.getAllLessons();
    }

    public void deleteLesson(Long id) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                lessonRepo.deleteById(session, id);
                session.getTransaction().commit();

            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }

    }


    public List<Lesson> getAvailableLessons() {
        List<Lesson> availableLessons = new ArrayList<>();
        List<Lesson> allLessons = lessonRepo.getAllLessons();
        for (Lesson lesson : allLessons) {
            if (lesson.getStartDateAsLocalDate().isAfter(LocalDate.now()) &&
                    lesson.getStudents().size() < lesson.getCapacity()) {
                availableLessons.add(lesson);
            }
        }
        return availableLessons;

    }


    public Lesson lessonUpdate(Lesson lesson) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            session.beginTransaction();
            Validation<Lesson> lessonValidation = new Validation<>();
            Set<String> validationMassage = lessonValidation.valid(lesson);
            if (!validationMassage.isEmpty()) {
                validationMassage.forEach(System.out::println);
            }
            lessonRepo.updateLesson(session, lesson);
            return lesson;
        }
    }

    public List<Student> getStudentsByLesson(Long lessonId) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            session.beginTransaction();
            List<Student> s = lessonRepo.findStudentsByLessonId(session, lessonId);
            session.getTransaction().commit();
            return s;

        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }


}


