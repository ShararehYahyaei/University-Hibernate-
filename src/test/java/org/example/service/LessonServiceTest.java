package org.example.service;


import org.example.config.SessionFactoryInstance;
import org.example.entity.Lesson;
import org.example.entity.Name;
import org.example.entity.Teacher;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class LessonServiceTest {

    LessonService lessonService = new LessonService();
    @AfterEach
    public void afterAll(){
        clearAll();
    }


    @BeforeEach
    public void beforeAll() {
        clearAll();
    }

    @Test
    void saveLesson() {
        Lesson lesson = new Lesson("math", 10, 20, "2024-11-15");
        Lesson res = lessonService.saveLesson(lesson);
        assertEquals(res, lesson);
    }

    @Test
    void deleteLesson() {
        Lesson lesson = new Lesson("math", 10, 20, "2024-12-18");
        Lesson resSave = lessonService.saveLesson(lesson);
        lessonService.deleteLesson(resSave.getId());
        assertNull(lessonService.findById(resSave.getId()));

    }

    @Test
    void update() {
        Lesson lesson = new Lesson("math", 10, 20, "2024-12-18");
        String expectedValue = "olom";
        lesson.setCourseName(expectedValue);
        Lesson res = lessonService.saveLesson(lesson);
        assertEquals(expectedValue, res.getCourseName());

    }

    private void clearDatabase(Long id) {
        lessonService.deleteLesson(id);
    }

    @Test
    void addNewLessonWithZeroCapacity() {
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson = new Lesson("math", 10, 0, "2024-12-01");
        Lesson lesson1 = new Lesson("math1", 10, 0, "2024-12-02");
        Lesson lesson2 = new Lesson("math2", 10, 10, "2024-12-30");
        Lesson lesson3 = new Lesson("math3", 10, 20, "2024-12-30");
        Lesson l = lessonService.saveLesson(lesson);
        Lesson l1 = lessonService.saveLesson(lesson1);
        Lesson l2 = lessonService.saveLesson(lesson2);
        Lesson l3 = lessonService.saveLesson(lesson3);
        lessons.add(l);
        lessons.add(l1);
        lessons.add(l2);
        lessons.add(l3);

        List<Lesson> lessonsAvailable = lessonService.getAvailableLessons();
        List<Lesson> expectedList = List.of(l2, l3);
        for (Lesson lll : expectedList) {
            Stream<Lesson> lessonStream = lessonsAvailable.stream().filter(c -> c.getId().equals(lll.getId()));
            Optional<Lesson> first = lessonStream.findFirst();
            if (first.isPresent()) {
                Lesson actual = first.get();
                assertEquals(lll.getCourseName(), actual.getCourseName());
                assertEquals(lll.getStartDate(), actual.getStartDate());
            }
            assertEquals(2, lessonsAvailable.size());

        }
    }
    @Test
    void addNewLessonWithPassedDate() {
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson = new Lesson("math", 10, 20, "2024-12-10");
        Lesson lesson1 = new Lesson("math1", 10, 20, "2024-12-01");
        Lesson lesson2 = new Lesson("math2", 10, 20, "2024-12-30");
        Lesson lesson3 = new Lesson("math3", 10, 20, "2024-12-30");
        Lesson l = lessonService.saveLesson(lesson);
        Lesson l1 = lessonService.saveLesson(lesson1);
        Lesson l2 = lessonService.saveLesson(lesson2);
        Lesson l3 = lessonService.saveLesson(lesson3);
        lessons.add(l);
        lessons.add(l1);
        lessons.add(l2);
        lessons.add(l3);

        List<Lesson> lessonsAvailable = lessonService.getAvailableLessons();
        List<Lesson> expectedList = List.of(l2, l3);
        for (Lesson lll : expectedList) {
            Stream<Lesson> lessonStream = lessonsAvailable.stream().filter(c -> c.getId().equals(lll.getId()));
            Optional<Lesson> first = lessonStream.findFirst();
            Lesson actual = first.get();
            assertEquals(lll.getCourseName(), actual.getCourseName());
            assertEquals(lll.getStartDate(), actual.getStartDate());
        }
        assertEquals(2, lessonsAvailable.size());

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