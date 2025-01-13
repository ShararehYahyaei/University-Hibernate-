package org.example.service;

import org.example.entity.*;
import org.example.repository.LessonRepo;
import org.example.repository.StudentRepo;
import org.example.repository.StudentScoreRepo;
import org.example.repository.TeacherRepo;
import org.junit.jupiter.api.Test;
import org.mockito.MockingDetails;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TeacherServiceTest {
    private final TeacherRepo repo = Mockito.mock(TeacherRepo.class);
    private final StudentScoreRepo stundetScoreRepo=Mockito.mock(StudentScoreRepo.class);
    private final StudentScoreService studentScoreService = new StudentScoreService(stundetScoreRepo);
    private final TeacherService teacherService = new TeacherService(repo, studentScoreService);
    private final LessonRepo lesRepo = Mockito.mock(LessonRepo.class);
    private final LessonService lessonService = new LessonService(new LessonRepo());
    private final StudentRepo studentRepo = Mockito.mock(StudentRepo.class);
    private final StudentService studentService = new StudentService(studentRepo,studentScoreService);


    @Test
    void saveTeacher() {

        Teacher teacher = new Teacher("mohandes", "diplom", "12", new User(new Name("T1", "T1F"),
                "hadi", "5699", Type.Teacher, "+989125555555",
                "T1@gmail.com", "1111111111"));
        Mockito.when(repo.saveSTeacher(Mockito.any(), Mockito.any(Teacher.class))).thenReturn(teacher);
        Teacher res = teacherService.saveTeacher(teacher);
        assertEquals(res, teacher);

    }

    @Test
    void given_a_teacher_then_updated_exception_result() {

        Teacher teacher = new Teacher("engineer", "Bachelor", "4545", new User(new Name("T1", "T1F"), "T1", "5699",
                Type.Teacher, "+989126666666", "T1@gmail.com", "1111111111"));

        String expectedValue = "1ssssss@gmail.com";
        teacher.getUser().setEmail(expectedValue);
        Mockito.when(repo.updateTeacher(Mockito.any(), Mockito.any())).thenReturn(teacher);
        Teacher teacherAfterUpdate = teacherService.teacherUpdate(teacher);
        assertEquals(expectedValue, teacherAfterUpdate.getUser().getEmail());

    }


    @Test
    void addNewLesson() {
        List<Lesson> lessons = List.of(new Lesson("math", 10, 20, "2024-12-18"),
                new Lesson("math1", 10, 20, "2024-12-18"),
                new Lesson("math2", 10, 20, "2024-12-18"));

        Teacher teacher = new Teacher("engineer", "Bachelor", "4547", new User(new Name("t1name", "t1family"), "t1nameUserName", "5699",
                Type.Teacher, "+989125478963", "t1nameemai@gmail.com",
                "1111111111"));
        teacher.getLesson().addAll(lessons);
        Mockito.when(repo.findById(Mockito.any(), Mockito.eq(teacher.getId()))).thenReturn(teacher);
        List<Lesson> allLessonsForATeacher = teacherService.getAllLessonsForATeacher(teacher);
        assertEquals(3, allLessonsForATeacher.size());


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

        Teacher teacher = new Teacher("engineer", "Bachelor", "4548", new User(new Name("Teacher1name", "Teacher1family"), "Teacher1nameUsername", "5699", Type.Teacher,
                "+981111111111", "Teacher1name1@gmail.com", "1111111111"));

        teacher.getLesson().addAll(lessons);
        Student std = new Student("46541", new User(new Name("s1name", "s1family"), "s1nameUserName", "5599",
                Type.Student, "+982222222222", "s1name1@gmail.com", "1111111112"));

        Student std1 = new Student("46541", new User(new Name("s2name", "s2family"), "s2nameUserName",
                "5999", Type.Student, "+983333333333", "s2name2@gmail.com", "1111111113"));

        lesson.getStudents().add(std);
        lesson.getStudents().add(std1);
        lesson1.getStudents().add(std1);

        Mockito.when(repo.findById(Mockito.any(), Mockito.eq(teacher.getId()))).thenReturn(teacher);
        teacherService.getMyStudents(teacher);
        assertEquals(2, teacherService.getMyStudents(teacher).size());

    }

    @Test
    void given_a_userId_then_received_user_expected() {
        User user = new User(new Name("Teacher1name", "Teacher1family"),
                "Teacher1nameUsername",
                "5699", Type.Teacher,
                "+981111111111", "Teacher1name1@gmail.com", "1111111111");
        Teacher teacherR = teacherService.saveTeacher(new Teacher("engineer",
                "Bachelor", "4548", user));

        Mockito.when(repo.fetchTeacherByUserId(Mockito.any(), Mockito.any())).thenReturn(teacherR);
        Teacher teacher = teacherService.fetchByUserId(user);
        assertEquals(teacher, teacherR);
    }

    @Test
    void putScorePerLesson() {

        Lesson lesson = new Lesson("math", 10, 20, "2026-12-29");

        Teacher teacher = new Teacher("engineer", "Bachelor", "4548", new User(new Name("Teacher1name", "Teacher1family"), "Teacher1nameUsername", "5699", Type.Teacher,
                "+981111111111", "Teacher1name1@gmail.com", "1111111111"));

        teacher.getLesson().add(lesson);

        Student std = new Student("46541", new User(new Name("s1name", "s1family"), "s1nameUserName", "5599",
                Type.Student, "+982222222222", "s1name1@gmail.com", "1111111112"));

        Mockito.when(stundetScoreRepo.getLessonScoreByStudent(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(20.00);
        lesson.getStudents().add(std);
        assertEquals(20, teacherService.getScoreForStudent(std, teacher, lesson));

    }



}