package org.example.service;

import org.example.entity.Lesson;
import org.example.repository.LessonRepo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LessonServiceIT {
    private final LessonService lessonService=new LessonService(new LessonRepo());

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
}
