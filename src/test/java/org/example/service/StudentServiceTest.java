package org.example.service;

import org.example.entity.Name;
import org.example.entity.Student;
import org.example.entity.Type;
import org.example.entity.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    StudentService studentService = new StudentService();

    @Test
    void deleteStudent() {

        Name name = new Name();
        name.setFirstName("reza");
        name.setLastName("Yahayei");
        User user = new User("hadi", "5699", Type.Student, "4578", "ha@gmail.com", "2222");
        Student student = new Student(name, "4545", user);
        Student resultStudent = studentService.save(student);
        studentService.deleteStudent(resultStudent.getId());
        assertEquals(Optional.empty(), studentService.findById(resultStudent.getId()));
    }

    @Test
    void updateStudent() {
        Name name = new Name();
        name.setFirstName("reza");
        name.setLastName("Yahayei");
        User user = new User("hadi", "5699", Type.Student, "4578", "ha@gmail.com", "2222");
        Student student = new Student(name, "4545", user);
        Student resultStudent = studentService.save(student);
      //  assertEquals(expectedValue, res.getPhoneNumber());
        clearDatabase(resultStudent.getId());

    }


    @Test
    void saveStudent() {
        Name name = new Name();

        name.setFirstName("reza");
        name.setLastName("Yahayei");
        User user = new User("hadi", "5699", Type.Student, "4578", "ha@gmail.com", "2222");
        Student student = new Student(name, "4545", user);
        Student resultStudent = studentService.save(student);
        assertEquals(student, resultStudent);
        clearDatabase(resultStudent.getId());
    }

    private void clearDatabase(Long id) {
        studentService.deleteStudent(id);
    }
}