package org.example.service;

import org.example.entity.Name;
import org.example.entity.Student;
import org.example.entity.Teacher;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TeacherServiceTest {
    TeacherService teacherService = new TeacherService();

    @Test
    void saveTeacher() {
        Name name = new Name();
        name.setFirstName("reza");
        name.setLastName("yahyaee");
        Teacher teacher = new Teacher(name, "mohandes", "diplom", "12");
        Teacher resultTeacher = teacherService.saveTeacher(teacher);
        assertEquals(teacher, resultTeacher);
        clearDatabase(resultTeacher.getId());

    }

    @Test
    void update() {
        Name name = new Name();
        name.setFirstName("reza");
        name.setLastName("Yahayei");
        Teacher teacher = new Teacher(name, "mohandes", "diplom", "12");
        String expectedValue = "+989125478888";
    //    teacher.setPhoneNumber(expectedValue);
        Teacher res = teacherService.update(teacher);
       // assertEquals(expectedValue, res.getPhoneNumber());
        clearDatabase(res.getId());
    }

    @Test
    void findById() {
        Name name = new Name();
        name.setFirstName("reza");
        name.setLastName("Yahayei");
        Teacher teacher = new Teacher(name, "mohandes", "diplom", "12");
        Teacher teacher1 = teacherService.saveTeacher(teacher);
        Teacher res = teacherService.findById(teacher1.getId());
        assertEquals(res, teacher1);
        clearDatabase(teacher1.getId());
    }

    @Test
    void deleteTeacher() {
        Name name = new Name();
        name.setFirstName("reza");
        name.setLastName("yahyaee");
        Teacher teacher = new Teacher(name, "mohandes", "diplom", "12");
        Teacher reasultTeacher = teacherService.saveTeacher(teacher);
        teacherService.deleteTeacher(reasultTeacher.getId());
        assertNull(teacherService.findById(reasultTeacher.getId()));
    }

    private void clearDatabase(Long id) {
        teacherService.deleteTeacher(id);
    }
}