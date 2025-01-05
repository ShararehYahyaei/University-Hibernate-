package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.Name;
import org.example.entity.Student;
import org.example.entity.Type;
import org.example.entity.User;
import org.example.repository.StudentRepo;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class StudentServiceIT {
    private final StudentService studentService = new StudentService(new StudentRepo());
    @AfterEach
    public void afterAll() {
        clearAll();
    }

    @BeforeEach
    public void beforeAll() {
        clearAll();
    }

    @Test
    void deleteStudent() {
        Student student = new Student("4545", new User(new Name("s2name", "s2family"), "s2nameUserName",
                "5999", Type.Student, "+983333333333", "s2name2@gmail.com", "1111111111"));
        Student resultStudent = studentService.saveStudent(student);
        studentService.deleteStudent(resultStudent.getId());
        assertNull(studentService.findById(resultStudent.getId()));
    }
    @Test
    void updateStudent() {
        Student student = new Student("4545",
                new User( new Name("reza","Yahayei"),
                        "hadi", "5699", Type.Student,
                        "+989124577788", "ha@gmail.com", "1111111111"));
        String expectedEmail = "3ssssss@gmail.com";
        student.getUser().setEmail(expectedEmail);
        Student resultStudent = studentService.saveStudent(student);
        assertEquals(expectedEmail, resultStudent.getUser().getEmail());

    }

    @Test
    void saveStudent() {
        Name name = new Name();
        name.setFirstName("reza");
        name.setLastName("Yahayei");
        User user = new User(name, "hadi", "5699", Type.Student, "+989125478998", "ha@gmail.com", "1111111111");
        Student student = new Student("4545", user);
        Student resultStudent = studentService.saveStudent(student);
        assertEquals(student, resultStudent);

    }


    public void clearAll() {

        try (Session session = SessionFactoryInstance.sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from Student").executeUpdate();
            session.createQuery("delete from Lesson ").executeUpdate();
            session.createQuery("delete from Teacher ").executeUpdate();
            session.createQuery("delete from User ").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}