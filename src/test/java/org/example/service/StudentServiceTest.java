package org.example.service;

import org.example.entity.*;
import org.example.repository.StudentRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {


    private final StudentRepo repository = Mockito.mock(StudentRepo.class);
    private final StudentService studentService = new StudentService(repository);

    @Test
    void fetchByUserId() {
        Student student = new Student("4541", new User(new Name("s3name", "s3family"), "hoda", "5799", Type.Student, "+989125878963",
                "hla@gmail.com", "1111111111"));
        when(repository.fetchStudentByUserId(Mockito.any(),Mockito.eq(student.getUser()))).thenReturn(student);
        Student std = studentService.fetchByUserId(student.getUser());
        assertEquals(student, std);

    }

    @Test
    void saveStudent() {
        Name name = new Name();
        name.setFirstName("reza");
        name.setLastName("Yahayei");
        User user = new User(name, "hadi", "5699", Type.Student, "+989125478998", "ha@gmail.com", "1111111111");
        Student student = new Student("4545", user);
        Mockito.when(repository.saveStudent(Mockito.any(),Mockito.any(Student.class))).thenReturn(student);
        assertEquals(student, studentService.saveStudent(student));
    }

    @Test
    void given_a_student_then_updated_exception_result() {
        Student student = new Student("4545",
                new User(new Name("reza", "Yahayei"),
                        "hadi", "5699", Type.Student,
                        "+989124577788", "ha@gmail.com", "1111111111"));
        String expectedEmail = "3ssssss@gmail.com";
        student.getUser().setEmail(expectedEmail);
        Mockito.when(repository.updatestudent(Mockito.any(), Mockito.any())).thenReturn(student);
        Student resultStudent = studentService.studentUpdate(student);
        assertEquals(expectedEmail, resultStudent.getUser().getEmail());

    }

    @Test
    void deleteStudent() {
        Student student = new Student("4545", new User(new Name("s2name", "s2family"), "s2nameUserName",
                "5999", Type.Student, "+983333333333", "s2name2@gmail.com", "1111111111"));
        studentService.deleteStudent(student.getId());
        verify(repository, times(1)).deleteById(Mockito.any(),Mockito.eq(student.getId()) );
    }

}