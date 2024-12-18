package org.example.service;

import org.example.entity.Name;
import org.example.entity.Student;
import org.junit.jupiter.api.AfterEach;
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
        Student student = new Student(name, "7880", "5689",
                "+989125697478", "hashem@gmail.com",
                "1111111111", "1154");
        Student resultStudent = studentService.save(student);
        studentService.deleteStudent(resultStudent.getId());
        assertEquals(Optional.empty(), studentService.findById(resultStudent.getId()));
    }

    @Test
    void updateStudent() {
        Name name = new Name();
        name.setFirstName("reza");
        name.setLastName("Yahayei");
        Student student = new Student(name, "7880", "5689",
                "+989125697478", "hashem@gmail.com",
                "1111111111", "1154");
        String expectedValue = "+989125478888";
        student.setPhoneNumber(expectedValue);
        Student res = studentService.update(student);
        assertEquals(expectedValue, res.getPhoneNumber());
        clearDatabase(res.getId());

    }


    @Test
    void saveStudent() {
        Name name = new Name();
        name.setFirstName("reza");
        name.setLastName("Yahayei");
        Student student = new Student(name, "7880", "5689",
                "+989125697478", "hashem@gmail.com",
                "1111111111", "1154");
        Student resultStudent = studentService.save(student);
        assertEquals(student, resultStudent);
        clearDatabase(resultStudent.getId());
    }

    private void clearDatabase(Long id){
        studentService.deleteStudent(id);
    }
}