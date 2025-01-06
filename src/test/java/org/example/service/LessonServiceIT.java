package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.Lesson;
import org.example.repository.LessonRepo;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LessonServiceIT {
    private final LessonService lessonService=new LessonService(new LessonRepo());

    @AfterEach
    public void afterAll() {
        clearAll();
    }

    @BeforeEach
    public void beforeAll() {
        clearAll();
    }

    @Test
    void saveLesson() {
        Lesson lesson = new Lesson("math", 10, 20, "2026-11-15");
        Lesson res = lessonService.saveLesson(lesson);
        assertEquals(res, lesson);
    }
    @Test
    void deleteLesson() {
        Lesson lesson = new Lesson("math", 10, 20, "2026-12-18");
        Lesson resSave = lessonService.saveLesson(lesson);
        lessonService.deleteLesson(resSave.getId());
        assertNull(lessonService.findById(resSave.getId()));

    }
    public void clearAll() {

        try (Session session = SessionFactoryInstance.sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from Student").executeUpdate();
            session.createQuery("delete from Lesson ").executeUpdate();
            session.createQuery("delete from Teacher ").executeUpdate();
            session.createQuery("delete from User ").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
