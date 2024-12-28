package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.*;
import org.example.entity.dtoLesson.LessonStudentDto;
import org.example.repository.TeacherRepo;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TeacherServiceTest {
    TeacherService teacherService = new TeacherService();
    LessonService lessonService = new LessonService();
    StudentService studentService = new StudentService();

    @AfterEach
    public void afterAll() {
      clearAll();
    }
    @BeforeEach
    public void beforeAll() {
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
        Teacher teacherR = teacherService.saveTeacher(new Teacher("engineer",
                "Bachelor", "4548", user));
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

    @Test
    void fetchByUserId() {
        User user = new User(new Name("Teacher1name", "Teacher1family"),
                "Teacher1nameUsername",
                "5699", Type.Teacher,
                "+981111111111", "Teacher1name1@gmail.com", "1111111111");
        Teacher teacherR = teacherService.saveTeacher(new Teacher("engineer",
                "Bachelor", "4548", user));
        teacherService.teacherUpdate(teacherR);
        Teacher td = teacherService.fetchByUserId(user);
        assertEquals("4548", td.getEmployeeCode());
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

//    @Test
//    void putScorePerLesson() {
//        Lesson lesson = lessonService.saveLesson(new Lesson("math", 10, 20, "2024-12-29"));
//        Teacher teacherAfter = teacherService.saveTeacher(new Teacher("mohandes", "diplom", "12", new User(new Name("T1", "T1F"),
//                "hadi", "5699", Type.Teacher, "+989125555555",
//                "T1@gmail.com", "1111111111")));
//        teacherAfter.getLesson().add(lesson);
//        Teacher resultTeacher = teacherService.teacherUpdate(teacherAfter);
//
//
//        Student s1 = studentService.saveStudent(new Student("46541",
//                new User(new Name("s1name", "s1family"), "s1nameUserName", "5599",
//                Type.Student, "+982222222222", "s1name1@gmail.com", "1111111112")));
//
//
//        Student s2 = studentService.saveStudent(new Student("47441",
//                new User(new Name("s11name", "s11family"), "s11nameUserName", "55599",
//                Type.Student, "+983222222222", "s11name1@gmail.com", "2111111112")));
//
//
//        Student s3 = studentService.saveStudent(new Student("447441",
//                new User(new Name("s21name", "s21family"), "s21nameUserName", "55598",
//                Type.Student, "+983222222422", "s11name22@gmail.com", "2111111412")));
//
//
//        s1.getLesson().add(lesson);
//      Student  afterS1 = studentService.studentUpdate(s1);
//
//        s2.getLesson().add(lesson);
//        Student  afters2 = studentService.studentUpdate(s2);
//
//        s3.getLesson().add(lesson);
//      Student  afters3 = studentService.studentUpdate(s3);
//
//
//        Lesson lessons = teacherService.findById(teacherAfter.getId()).getLesson().getFirst();
//        Student studentForScore = lessons.getStudents().stream().filter(c -> c.getId().equals(afterS1.getId())).findFirst().get();
//        Student student1ForScore = lessons.getStudents().stream().filter(c -> c.getId().equals(afters2.getId())).findFirst().get();
//        Student student2ForScore = lessons.getStudents().stream().filter(c -> c.getId().equals(afters3.getId())).findFirst().get();
//
//        studentForScore.getLesson().stream().findFirst().get().setScore(new Score(10,lessons));
//        student1ForScore.getLesson().stream().findFirst().get().setScore(new Score(12,lessons));
//        student2ForScore.getLesson().stream().findFirst().get().setScore(new Score(15,lessons));
//
//
//        studentService.studentUpdate(studentForScore);
//        studentService.studentUpdate(student1ForScore);
//        studentService.studentUpdate(student2ForScore);
//        lessonService.lessonUpdate(lesson);
//
//
//    }





}