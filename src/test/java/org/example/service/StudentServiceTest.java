package org.example.service;

import org.example.entity.*;
import org.example.repository.StudentRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StudentServiceTest {


    private final StudentRepo repository = Mockito.mock(StudentRepo.class);
    private final StudentService studentService = new StudentService(repository);

    @Test
    void fetchByUserId() {
        Student student = new Student("4541", new User(new Name("s3name", "s3family"), "hoda", "5799", Type.Student, "+989125878963",
                "hla@gmail.com", "1111111111"));
        when(repository.fetchStudentByUserId(student.getUser())).thenReturn(student);
        Student std = studentService.fetchByUserId(student.getUser());
        assertEquals("4541", std.getStudentNumber());

    }


}