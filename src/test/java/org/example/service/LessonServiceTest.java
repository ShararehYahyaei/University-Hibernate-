package org.example.service;


import org.example.config.SessionFactoryInstance;
import org.example.entity.*;
import org.example.repository.StudentRepo;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class LessonServiceTest {

    LessonService lessonService = new LessonService();
    StudentRepo studentRepo= Mockito.mock(StudentRepo.class);
    StudentService studentService = new StudentService(studentRepo);
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
        Lesson lesson = new Lesson("math", 10, 20, "2026-11-15");
        Lesson res = lessonService.saveLesson(lesson);
        assertEquals(res, lesson);
    }
    @Test
    void addNewLesson() {
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson = new Lesson("math", 10, 20, "2026-01-01");
        Lesson lesson1 = new Lesson("math1", 10, 20, "2026-01-01");
        Lesson lesson2 = new Lesson("math2", 10, 20, "2026-01-01");
        Lesson lesson3 = new Lesson("math3", 10, 20, "2026-01-01");
        Lesson l = lessonService.saveLesson(lesson);
        Lesson l1 = lessonService.saveLesson(lesson1);
        Lesson l2 = lessonService.saveLesson(lesson2);
        Lesson l3 = lessonService.saveLesson(lesson3);
        lessons.add(l);
        lessons.add(l1);
        lessons.add(l2);
        lessons.add(l3);

        List<Lesson> lessonsAvailable = lessonService.getAvailableLessons();
        for (Lesson lll : lessons) {
            Stream<Lesson> lessonStream = lessonsAvailable.stream().filter(c -> c.getId().equals(lll.getId()));
            Optional<Lesson> first = lessonStream.findFirst();
            Lesson actual = first.get();
            assertEquals(lll.getCourseName(), actual.getCourseName());
            assertEquals(lll.getStartDate(), actual.getStartDate());
        }


    }
    @Test
    void addNewLessonWithGettingStudent() {
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson = new Lesson("math", 10, 3, "2024-01-01");
        Lesson l = lessonService.saveLesson(lesson);
        lessons.add(l);
        Lesson l1 = lessonService.saveLesson(new Lesson("math1", 10, 5, "2026-12-29"));
        lessons.add(l1);


        Name name = new Name();
        name.setFirstName("s1name");
        name.setLastName("s1family");
        User user = new User(name, "hadi", "5599", Type.Student, "+989125478963", "ha@gmail.com", "1111111112");
        Student student = new Student("465335", user);
        Student resultStudent = studentService.saveStudent(student);


        Name name1 = new Name();
        name.setFirstName("s2name");
        name.setLastName("s2family");
        User user1 = new User(name1, "shahla", "5999", Type.Student, "+989125478863",
                "hha@gmail.com", "2222222222");
        Student student1 = new Student("4542", user1);
        Student resultStudent1 = studentService.saveStudent(student1);


        Name name2 = new Name();
        name.setFirstName("s3name");
        name.setLastName("s3family");
        User user2 = new User(name2, "hoda", "5799", Type.Student, "+989125878963",
                "hla@gmail.com", "1111111111");
        Student student2 = new Student("4541", user2);
        Student resultStudent2 = studentService.saveStudent(student2);

        resultStudent.getLesson().add(l);
        resultStudent1.getLesson().add(l);
        resultStudent2.getLesson().add(l);


        studentService.studentUpdate(resultStudent);
        studentService.studentUpdate(resultStudent1);
        studentService.studentUpdate(resultStudent2);
        List<Lesson> lA = lessonService.getAvailableLessons();
        assertEquals(1, lessonService.getAvailableLessons().size());
        assertEquals(l1.getCourseName(), lA.get(0).getCourseName());


    }


    @Test
    void deleteLesson() {
        Lesson lesson = new Lesson("math", 10, 20, "2026-12-18");
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
        Lesson lesson2 = new Lesson("math2", 10, 10, "2026-12-30");
        Lesson lesson3 = new Lesson("math3", 10, 20, "2026-12-30");
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
        Lesson lesson2 = new Lesson("math2", 10, 20, "2026-12-30");
        Lesson lesson3 = new Lesson("math3", 10, 20, "2026-12-30");
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