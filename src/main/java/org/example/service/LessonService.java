package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.Lesson;
import org.example.repository.LessonRepo;
import org.example.util.Validation;
import java.util.List;


public class LessonService {
    private final static LessonRepo lessonRepo = new LessonRepo();

    public Lesson saveLesson(Lesson Lesson) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Validation<Lesson> studentValidation = new Validation<>();
                if (studentValidation.valid(Lesson).isEmpty()) {
                    lessonRepo.saveLesson(session, Lesson);
                } else {
                    studentValidation.valid(Lesson).forEach(System.out::println);
                }
                session.getTransaction().commit();
                return Lesson;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    public Lesson update(Lesson lesson) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Validation<Lesson> studentValidation = new Validation<>();
                if (studentValidation.valid(lesson).isEmpty()) {
                    lessonRepo.saveLesson(session, lesson);
                } else {
                    studentValidation.valid(lesson).forEach(System.out::println);
                }
                session.getTransaction().commit();
                return lesson;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
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
                session.beginTransaction();
                List<Lesson> lessons = lessonRepo.getAllLessons(session);
                session.getTransaction().commit();
                return lessons;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    public String deleteLesson(Long id) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                lessonRepo.deleteById(session, id);
                session.getTransaction().commit();
                return "Delete Successfully ...";

            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }

    }
}
