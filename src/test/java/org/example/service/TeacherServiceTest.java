package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.*;
import org.example.entity.dtoLesson.LessonStudentDto;
import org.example.repository.LessonRepo;
import org.example.repository.StudentRepo;
import org.example.repository.TeacherRepo;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TeacherServiceTest {
    private final TeacherRepo repo = Mockito.mock(TeacherRepo.class);
    private final TeacherService teacherService = new TeacherService(repo);


    private final LessonRepo lesRepo = Mockito.mock(LessonRepo.class);
    private final LessonService lessonService = new LessonService(new LessonRepo());


    private final StudentRepo studentRepo = Mockito.mock(StudentRepo.class);
    private final StudentService studentService = new StudentService(studentRepo);


    @Test
    void saveTeacher() {

        Teacher teacher = new Teacher("mohandes", "diplom", "12", new User(new Name("T1", "T1F"),
                "hadi", "5699", Type.Teacher, "+989125555555",
                "T1@gmail.com", "1111111111"));
        Mockito.when(repo.saveSTeacher(teacher)).thenReturn(teacher);
        Teacher res = teacherService.saveTeacher(teacher);
        assertEquals("mohandes", res.getSpecialty());

    }

    @Test
    void updateTeacher() {

        Teacher teacher = new Teacher("engineer", "Bachelor", "4545", new User(new Name("T1", "T1F"), "T1", "5699",
                Type.Teacher, "+989126666666", "T1@gmail.com", "1111111111"));

        Mockito.when(repo.saveSTeacher(teacher)).thenReturn(teacher);
        String expectedValue = "1ssssss@gmail.com";
        teacher.getUser().setEmail(expectedValue);
        Teacher teacherAfterUpdate = teacherService.teacherUpdate(teacher);
        assertEquals(expectedValue, teacherAfterUpdate.getUser().getEmail());


    }


    @Test
    void addNewLesson() {
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson = new Lesson("math", 10, 20, "2024-12-18");
        Lesson lesson1 = new Lesson("math1", 10, 20, "2024-12-18");
        Lesson lesson2 = new Lesson("math2", 10, 20, "2024-12-18");
        lessons.add(lesson);
        lessons.add(lesson1);
        lessons.add(lesson2);
        Mockito.when(lesRepo.saveLesson(Mockito.any(), lesson)).thenReturn(lesson);

        Teacher teacher = teacherService.saveTeacher(new Teacher("engineer", "Bachelor", "4547", new User(new Name("t1name", "t1family"), "t1nameUserName", "5699", Type.Teacher, "+989125478963", "t1nameemai@gmail.com", "1111111111")));
        teacher.getLesson().addAll(lessons);
        Mockito.when(repo.saveSTeacher(teacher)).thenReturn(teacher);
        Teacher res = teacherService.saveTeacher(teacher);

        assertEquals(3, res.getLesson().size());
//todo check everything

    }

    @Test
    void getMyStudents() {
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson = new Lesson("math", 10, 20, "2026-12-29");
        Lesson lesson1 = new Lesson("math1", 10, 20, "2026-12-30");
        Lesson lesson2 = new Lesson("math2", 10, 20, "2026-12-28");
        lessons.add(lesson);
        lessons.add(lesson1);
        lessons.add(lesson2);


        User user = new User(new Name("Teacher1name", "Teacher1family"), "Teacher1nameUsername", "5699", Type.Teacher,
                "+981111111111", "Teacher1name1@gmail.com", "1111111111");

        Teacher teacher = new Teacher("engineer", "Bachelor", "4548", user);

        User userS = new User(new Name("s1name", "s1family"), "s1nameUserName", "5599",
                Type.Student, "+982222222222", "s1name1@gmail.com", "1111111112");
        Student std = new Student("46541", userS);
        std.getLesson().add(lesson);
        Mockito.when(studentRepo.saveStudent(Mockito.any(), std)).thenReturn(std);


        User userS1 = new User(new Name("s2name", "s2family"), "s2nameUserName",
                "5999", Type.Student, "+983333333333", "s2name2@gmail.com", "1111111113");
        Student std1 = new Student("46541", userS1);
        std1.getLesson().add(lesson);
        std1.getLesson().add(lesson1);
        Mockito.when(studentRepo.saveStudent(Mockito.any(), std1)).thenReturn(std1);

        lesson.getTeacher().add(teacher);
        lesson1.getTeacher().add(teacher);
        lesson2.getTeacher().add(teacher);
        lesson.getStudents().add(std);
        lesson.getStudents().add(std1);
        lesson1.getStudents().add(std1);

        Mockito.when(lesRepo.saveLesson(Mockito.any(), lesson)).thenReturn(lesson);
        teacher.getLesson().add(lesson);
        teacher.getLesson().add(lesson1);

        Mockito.when(repo.saveSTeacher(teacher)).thenReturn(teacher);
        User userS2 = new User(new Name("s3name", "s3family"), "s3nameUserName", "5799", Type.Student,
                "+984444444444", "s3name3@gmail.com", "1111111114");
        Student student2 = new Student("4545", userS2);

        Mockito.when(studentRepo.saveStudent(Mockito.any(), student2)).thenReturn(student2);

        Mockito.when(repo.findById(Mockito.any(Teacher.class))).thenReturn(teacher);

        Set<Student> myStudents = teacherService.getMyStudents(teacher);

        assertEquals(2, myStudents.size());

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

    @Test
    void putScorePerLesson() {
        Lesson lesson = lessonService.saveLesson(new Lesson("math", 10, 20, "2024-12-29"));
        Teacher teacherAfter = teacherService.saveTeacher(new Teacher("mohandes", "diplom", "12", new User(new Name("T1", "T1F"),
                "hadi", "5699", Type.Teacher, "+989125555555",
                "T1@gmail.com", "1111111111")));
        teacherAfter.getLesson().add(lesson);
        Teacher resultTeacher = teacherService.teacherUpdate(teacherAfter);


        Student s1 = studentService.saveStudent(new Student("46541",
                new User(new Name("s1name", "s1family"), "s1nameUserName", "5599",
                        Type.Student, "+982222222222", "s1name1@gmail.com", "1111111112")));


        Student s2 = studentService.saveStudent(new Student("47441",
                new User(new Name("s11name", "s11family"), "s11nameUserName", "55599",
                        Type.Student, "+983222222222", "s11name1@gmail.com", "2111111112")));


        Student s3 = studentService.saveStudent(new Student("447441",
                new User(new Name("s21name", "s21family"), "s21nameUserName", "55598",
                        Type.Student, "+983222222422", "s11name22@gmail.com", "2111111412")));


        s1.getLesson().add(lesson);
        Student afterS1 = studentService.studentUpdate(s1);

        s2.getLesson().add(lesson);
        Student afters2 = studentService.studentUpdate(s2);

        s3.getLesson().add(lesson);
        Student afters3 = studentService.studentUpdate(s3);

        List<Student> getStudents = teacherService.getMyListStudents(resultTeacher);
        List<Lesson> lessons = resultTeacher.getLesson();
        StudentsScore studentsWithScore = new StudentsScore();

//        teacherService.saveScoreForMyStudents(s2, resultTeacher, lesson, 15);
//        double sc = studentScoreService.getScore(s2.getId(), resultTeacher.getId(), lesson.getId());
//        assertEquals(15, sc);
//        clearAll();
    }


}