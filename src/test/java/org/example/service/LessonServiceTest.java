package org.example.service;


import org.example.entity.Lesson;
import org.example.entity.Name;
import org.example.entity.Teacher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class LessonServiceTest {


    LessonService lessonService = new LessonService();

    @Test
    void saveLesson() {
        Lesson lesson = new Lesson("math", 10, 20, "2024-12-18");
        Lesson res = lessonService.saveLesson(lesson);
        assertEquals(res, lesson);
        clearDatabase(lesson.getId());

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
        Lesson res = lessonService.update(lesson);
        assertEquals(expectedValue, res.getCourseName());
        clearDatabase(res.getId());
    }

    private void clearDatabase(Long id) {
        lessonService.deleteLesson(id);
    }

}