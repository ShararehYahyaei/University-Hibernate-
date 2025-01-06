package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.*;
import org.example.exception.ValidationException;
import org.example.repository.LessonRepo;
import org.example.util.Validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class LessonService {
    private final LessonRepo lessonRepo;

    public LessonService(LessonRepo lessonRepo) {
        this.lessonRepo = lessonRepo;
    }

    public Lesson saveLesson(Lesson lesson) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Validation<Lesson> studentValidation = new Validation<>();
                var validationErrors = studentValidation.valid(lesson);
                if (!validationErrors.isEmpty()) {
                    String errorMessage = String.join(", ", validationErrors);
                    throw new ValidationException(errorMessage);
                }

                lesson = lessonRepo.saveLesson(session, lesson);
                session.getTransaction().commit();
                return lesson;

            } catch (Exception e) {
                session.getTransaction().rollback();
                throw e;
            }
        }
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
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                return lessonRepo.getAllLessons(session);
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteLesson(Long id) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                lessonRepo.deleteById(session, id);
                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
            }
        }
    }

    public List<Lesson> getAvailableLessons() {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                List<Lesson> allLessons = lessonRepo.getAllLessons(session);
                return allLessons
                        .stream()
                        .filter(lesson ->
                                lesson.getStartDateAsLocalDate().isAfter(LocalDate.now()) &&
                                        lesson.getStudents().size() < lesson.getCapacity())
                        .collect(Collectors.toList());
            } catch (Exception e) {
                throw new RuntimeException(e);

            }
        }
    }

    public Lesson lessonUpdate(Lesson lesson) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Validation<Lesson> lessonValidation = new Validation<>();
                Set<String> validationErrors = lessonValidation.valid(lesson);
                if (!validationErrors.isEmpty()) {
                    String errorMessage = String.join(", ", validationErrors);
                    throw new ValidationException(errorMessage);
                } else {
                    Lesson updatedLesson = lessonRepo.update(session, lesson);
                    session.getTransaction().commit();
                    return updatedLesson;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}