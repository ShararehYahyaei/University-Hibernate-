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
    private final static LessonRepo lessonRepo = new LessonRepo();

    public Lesson saveLesson(Lesson lesson) {
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



    public List<Lesson> getAvailableLessons() {

        List<Lesson> availableLessons = new ArrayList<>();
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                lessonRepo.getAllLessons(session);
                List<Lesson> allLessons = lessonRepo.getAllLessons(session);
                for (Lesson lesson : allLessons) {
                    if (lesson.getStartDateAsLocalDate().isAfter(LocalDate.now()) &&
                            lesson.getStudents().size() < lesson.getCapacity()) {
                        availableLessons.add(lesson);
                    }
                }
                return availableLessons;

            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
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


