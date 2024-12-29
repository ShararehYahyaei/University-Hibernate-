package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.*;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    StudentService studentService = new StudentService();
    LessonService lessonService = new LessonService();

    @AfterEach
    public void afterAll() {
        clearAll();
    }
    @BeforeEach
    public void beforeAll() {
        clearAll();
    }

    @Test
    void deleteStudent() {
        Student student = new Student("4545", new User(new Name("s2name", "s2family"), "s2nameUserName",
                "5999", Type.Student, "+983333333333", "s2name2@gmail.com", "1111111111"));
        Student resultStudent = studentService.saveStudent(student);
        studentService.deleteStudent(resultStudent.getId());
        assertNull(studentService.findById(resultStudent.getId()));

    }

    @Test
    void updateStudent() {
        Name name = new Name();
        name.setFirstName("reza");
        name.setLastName("Yahayei");
        User user = new User(name, "hadi", "5699", Type.Student, "+989124577788", "ha@gmail.com", "1111111111");
        Student student = new Student("4545", user);
        String expectedEmail = "3ssssss@gmail.com";
        student.getUser().setEmail(expectedEmail);
        Student resultStudent = studentService.saveStudent(student);
        assertEquals(resultStudent.getUser().getEmail(), expectedEmail);


    }

    @Test
    void saveStudent() {
        Name name = new Name();
        name.setFirstName("reza");
        name.setLastName("Yahayei");
        User user = new User(name, "hadi", "5699", Type.Student, "+989125478998", "ha@gmail.com", "1111111111");
        Student student = new Student("4545", user);
        Student resultStudent = studentService.saveStudent(student);
        assertEquals(student, resultStudent);

    }


    @Test
    void addNewLesson() {
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson = new Lesson("math", 10, 20, "2025-01-01");
        Lesson lesson1 = new Lesson("math1", 10, 20, "2025-01-01");
        Lesson lesson2 = new Lesson("math2", 10, 20, "2025-01-01");
        Lesson lesson3 = new Lesson("math3", 10, 20, "2025-01-01");
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
        Lesson lesson = new Lesson("math", 10, 3, "2025-01-01");
        Lesson l = lessonService.saveLesson(lesson);
        lessons.add(l);
        Lesson l1 = lessonService.saveLesson(new Lesson("math1", 10, 5, "2024-12-29"));
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
    void fetchByUserId() {
        Name name = new Name();
        name.setFirstName("s3name");
        name.setLastName("s3family");
        User user2 = new User(name, "hoda", "5799", Type.Student, "+989125878963",
                "hla@gmail.com", "1111111111");
        Student student2 = new Student("4541", user2);
        studentService.saveStudent(student2);
        Student std = studentService.fetchByUserId(user2);
        assertEquals("4541", std.getStudentNumber());

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