package org.example.service;

import org.example.entity.*;
import org.example.exception.ValidationException;
import org.example.repository.LessonRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;


import static org.example.exception.MessageValidaton.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class LessonServiceTest {

    private final LessonRepo lessonRepo = Mockito.mock(LessonRepo.class);
    private final LessonService lessonService = new LessonService(lessonRepo);

    @Test
    void addNewLesson() {
        Mockito.when(lessonRepo.getAllLessons(Mockito.any())).thenReturn(List.of(
                new Lesson("math", 10, 20, "2023-01-01")
                , new Lesson("math1", 10, 20, "2026-01-01")
                , new Lesson("math2", 10, 20, "2026-01-01")
                , new Lesson("math3", 10, 20, "2026-01-01")));

        List<Lesson> lessonsAvailable = lessonService.getAvailableLessons();
        assertEquals(3, lessonsAvailable.size());

    }

    @Test
    void when_there_are_two_lessons_but_one_of_them_full_of_capacity_then_only_one_is_expected() {

        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson = new Lesson("math", 10, 3, "2026-01-01");
        lessons.add(lesson);

        Lesson l1 = new Lesson("math1", 10, 5, "2026-12-29");
        lessons.add(l1);

        Mockito.when(lessonRepo.getAllLessons(Mockito.any())).thenReturn(lessons);

        Student student = new Student("465335", new User(new Name("s1name", "s1family"), "hadi", "5599", Type.Student,
                "+989125478963", "ha@gmail.com", "1111111112"));

        Student student1 = new Student("4542", new User(new Name("s2name", "s2family"), "shahla", "5999", Type.Student,
                "+989125478863",
                "hha@gmail.com", "2222222222"));


        Student student2 = new Student("4541", new User(new Name("s3name", "s3family"), "hoda", "5799", Type.Student, "+989125878963",
                "hla@gmail.com", "1111111111"));


        lesson.getStudents().add(student);
        lesson.getStudents().add(student1);
        lesson.getStudents().add(student2);
        List<Lesson> availableLessons = lessonService.getAvailableLessons();
        assertEquals(1, availableLessons.size());
        assertEquals(l1, availableLessons.get(0));

    }

    @Test
    void given_the_empty_courses_then_throw_exception_is_expected() {
        Lesson lesson = new Lesson(null, 2, 3, "2023-01-01");
        ValidationException exception = assertThrows(ValidationException.class, () -> lessonService.saveLesson(lesson));
        assertEquals(INVALID_COURSE_NAME, exception.getMessage());

    }
    @Test
    void given_the_minus_credit_then_throw_exception_is_expected() {
        Lesson lesson = new Lesson("math", -1, 3, "2023-01-01");
        ValidationException exception = assertThrows(ValidationException.class, () -> lessonService.saveLesson(lesson));
        assertEquals(invalid_credit_Lesson_with_minus, exception.getMessage());

    }
    @Test
    void given_the_invalid_credit_then_throw_exception_is_expected() {
        Lesson lesson = new Lesson("math", 12, 3, "2023-01-01");
        ValidationException exception = assertThrows(ValidationException.class, () -> lessonService.saveLesson(lesson));
        assertEquals(INVALID_CREDIT_Lesson_with_inavlid_data, exception.getMessage());

    }
    @Test
    void given_the_invalid_capacity_then_throw_exception_is_expected() {
        Lesson lesson = new Lesson("math", 3, -1, "2023-01-01");
        ValidationException exception = assertThrows(ValidationException.class, () -> lessonService.saveLesson(lesson));
        assertEquals(INVALID_CAPACITY, exception.getMessage());

    }

    @Test
    void given_a_lesson_then_updated_exception_result() {
        Lesson lesson = new Lesson("math", 2, 20, "2026-12-18");

        String expectedValue = "math1";
        lesson.setCourseName(expectedValue);
        Mockito.when(lessonRepo.update(Mockito.any(), Mockito.any())).thenReturn(lesson);
        Lesson res = lessonService.lessonUpdate(lesson);
        assertEquals(expectedValue, res.getCourseName());

    }


    @Test
    void addNewLessonWithZeroCapacity() {
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson = new Lesson("math", 10, 0, "2026-12-01");
        Lesson lesson1 = new Lesson("math1", 10, 0, "2026-12-02");
        Lesson lesson2 = new Lesson("math2", 10, 10, "2026-12-30");
        Lesson lesson3 = new Lesson("math3", 10, 20, "2026-12-30");
        lessons.add(lesson);
        lessons.add(lesson1);
        lessons.add(lesson2);
        lessons.add(lesson3);
        Mockito.when(lessonRepo.getAllLessons(Mockito.any())).thenReturn(lessons);
        assertEquals(2, lessonService.getAvailableLessons().size());
    }

    @Test
    void addNewLessonWithPassedDate() {
        List<Lesson> lessons = new ArrayList<>();
        Lesson lesson = new Lesson("math", 10, 20, "2024-12-10");
        Lesson lesson1 = new Lesson("math1", 10, 20, "2024-12-01");
        Lesson lesson2 = new Lesson("math2", 10, 20, "2026-12-30");
        Lesson lesson3 = new Lesson("math3", 10, 20, "2026-12-30");
        lessons.add(lesson);
        lessons.add(lesson1);
        lessons.add(lesson2);
        lessons.add(lesson3);
        Mockito.when(lessonRepo.getAllLessons(Mockito.any())).thenReturn(lessons);
        assertEquals(2, lessonService.getAvailableLessons().size());

    }

    @Test
    void saveLesson() {
        Lesson lesson = new Lesson("math", 3, 20, "2026-11-15");
        Mockito.when(lessonRepo.saveLesson(Mockito.any(),Mockito.any())).thenReturn(lesson);
        Lesson res = lessonService.saveLesson(lesson);
        assertEquals(lesson, res);
    }

    @Test
    void deleteLesson() {
        Lesson lesson = new Lesson("math", 3, 20, "2026-12-18");
        lessonService.deleteLesson(lesson.getId());
        verify(lessonRepo,times(1)).deleteById(Mockito.any(), Mockito.eq(lesson.getId()));

    }


}