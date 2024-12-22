package org.example.service;

import org.example.entity.Name;
import org.example.entity.Student;
import org.example.entity.Type;
import org.example.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    StudentService studentService = new StudentService();

    @Test
    void deleteStudent() {

        Name name = new Name();
        name.setFirstName("reza");
        name.setLastName("Yahayei");
        User user = new User(name,"hadi", "5699", Type.Student, "4578", "ha@gmail.com", "2222");
        Student student = new Student( "4545", user);
        Student resultStudent = studentService.saveStudent(student);
        studentService.deleteStudent(1L);
        assertNull(studentService.findById(resultStudent.getId()));

    }

    @Test
    void updateStudent() {
        Name name = new Name();
        name.setFirstName("reza");
        name.setLastName("Yahayei");
        User user = new User(name,"hadi", "5699", Type.Student, "4578", "ha@gmail.com", "2222");
        Student student = new Student( "4545", user);
        student.getUser().setEmail("ssssss@gmail.com");
        Student resultStudent = studentService.saveStudent(student);
        assertEquals(resultStudent.getUser().getEmail(), "ssssss@gmail.com");
      clearDatabase(resultStudent.getId());

    }


    @Test
    void saveStudent() {
        Name name = new Name();
        name.setFirstName("reza");
        name.setLastName("Yahayei");
        User user = new User(name,"hadi", "5699", Type.Student, "+989125478998", "ha@gmail.com", "2222");
        Student student = new Student("4545", user);
        Student resultStudent = studentService.saveStudent(student);
        assertEquals(student, resultStudent);
    //  clearDatabase(resultStudent.getId());
    }

    private void clearDatabase(Long id) {
        studentService.deleteStudent(id);
    }
    @Test
    void saveStudentRes() {
        Name name = new Name();
        name.setFirstName("reza");
        name.setLastName("Yahayei");
        User user = new User(name,"hadi", "5699", Type.Student, "9125478998", "ha@gmail.com", "2222");
        Student student = new Student("4545", user);
        Student resultStudent = studentService.saveStudent(student);
        assertNull(resultStudent);
       // clearDatabase(resultStudent.getId());
    }
}