package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.*;
import org.example.repository.TeacherRepo;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TeacherServiceTest {
    TeacherService teacherService = new TeacherService();
    LessonService lessonService = new LessonService();
    StudentService studentService = new StudentService();
    @AfterEach
    public void afterAll(){
        clearAll();
    }

    @Test
    void saveTeacher() {

        Teacher teacher = new Teacher("mohandes", "diplom", "12", new User(new Name("T1", "T1F"),
                "hadi", "5699", Type.Teacher, "+989125555555",
                "T1@gmail.com", "1111111111"));
        Teacher resultTeacher = teacherService.saveTeacher(teacher);
        assertEquals(teacher, resultTeacher);

    }

    @Test
    void updateTeacher() {

        Teacher teacher = new Teacher("engineer", "Bachelor", "4545", new User(new Name("T1", "T1F"), "T1", "5699",
                Type.Teacher, "+989126666666", "T1@gmail.com", "1111111111"));
        Teacher teacherBeforeUpdate = teacherService.saveTeacher(teacher);
        String expectedValue = "1ssssss@gmail.com";
        teacherBeforeUpdate.getUser().setEmail(expectedValue);
        Teacher teacherAfterUpdate = teacherService.teacherUpdate(teacherBeforeUpdate);
        assertEquals(expectedValue, teacherAfterUpdate.getUser().getEmail());


    }


    @Test
    void deleteTeacher() {


        Teacher teacher = new Teacher("engineer", "Bachelor", "4546", new User(new Name("T1", "T1Family"), "T1", "5699", Type.Teacher,
                "+989124444444", "T1@gmail.com", "1111111111"));
        Teacher result = teacherService.saveTeacher(teacher);
        teacherService.deleteTeacher(result.getId());
        assertNull(teacherService.findById(result.getId()));
    }

    private void clearDatabase(Long id) {
        teacherService.deleteTeacher(id);
    }


    @Test
    void addNewLesson() {
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson = new Lesson("math", 10, 20, "2024-12-18");
        Lesson lesson1 = new Lesson("math1", 10, 20, "2024-12-18");
        Lesson lesson2 = new Lesson("math2", 10, 20, "2024-12-18");
        Lesson l = lessonService.saveLesson(lesson);
        Lesson l1 = lessonService.saveLesson(lesson1);
        Lesson l2 = lessonService.saveLesson(lesson2);
        lessons.add(l);
        lessons.add(l1);
        lessons.add(l2);


        Teacher teacher = teacherService.saveTeacher(new Teacher("engineer", "Bachelor", "4547", new User(new Name("t1name", "t1family"), "t1nameUserName", "5699", Type.Teacher, "+989125478963", "t1nameemai@gmail.com", "1111111111")));
        teacher.getLesson().addAll(lessons);
        teacherService.teacherUpdate(teacher);
        assertEquals(3, lessonService.findAll().size());



    }

    @Test
    void getMyStudents() {
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson = new Lesson("math", 10, 20, "2024-12-29");
        Lesson lesson1 = new Lesson("math1", 10, 20, "2024-12-30");
        Lesson lesson2 = new Lesson("math2", 10, 20, "2024-12-28");
        Lesson l = lessonService.saveLesson(lesson);
        Lesson l1 = lessonService.saveLesson(lesson1);
        Lesson l2 = lessonService.saveLesson(lesson2);
        lessons.add(l);
        lessons.add(l1);
        lessons.add(l2);


        User user = new User(new Name("Teacher1name", "Teacher1family"), "Teacher1nameUsername", "5699", Type.Teacher,
                "+981111111111", "Teacher1name1@gmail.com", "1111111111");
        Teacher teacherR = teacherService.saveTeacher(new Teacher("engineer", "Bachelor", "4548", user));
        teacherR.getLesson().addAll(lessons);
        Teacher teacher1 = teacherService.teacherUpdate(teacherR);


        User userS = new User(new Name("s1name", "s1family"), "s1nameUserName", "5599",
                Type.Teacher, "+982222222222", "s1name1@gmail.com", "1111111112");
        Student resultStudent = studentService.saveStudent(new Student("46541", userS));
        resultStudent.getLesson().add(l);
        studentService.studentUpdate(resultStudent);


        User userS1 = new User(new Name("s2name", "s2family"), "s2nameUserName",
                "5999", Type.Teacher, "+983333333333", "s2name2@gmail.com", "1111111113");
        Student resultStudent1 = studentService.saveStudent(new Student("4542", userS1));
        resultStudent1.getLesson().add(l1);
        resultStudent1.getLesson().add(l2);
        studentService.studentUpdate(resultStudent1);


        User userS2 = new User(new Name("s3name", "s3family"), "s3nameUserName", "5799", Type.Teacher,
                "+984444444444", "s3name3@gmail.com", "1111111114");
        Student student2 = new Student("4545", userS2);
        Student resultStudent2 = studentService.saveStudent(student2);
        Set<Student> myStudents = teacherService.getMyStudents(teacher1);
        assertEquals(2, myStudents.size());
        List<Student> expectedStudents = List.of(resultStudent, resultStudent1);

        for (Student student : myStudents) {
            Optional<Student> first = expectedStudents.stream().filter(c -> c.getId().equals(student.getId())).findFirst();
            Student expected = first.get();
            assertEquals(expected.getStudentNumber(), student.getStudentNumber());
        }


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