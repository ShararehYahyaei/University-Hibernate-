package org.example.service;


import org.example.config.SessionFactoryInstance;
import org.example.entity.Student;
import org.example.entity.User;
import org.example.entity.dto.StudentDto;
import org.example.repository.StudentRepo;
import org.example.util.Validation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StudentService {
    private final StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public Student saveStudent(Student student) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Validation<User> userValidation = new Validation<>();
                Validation<Student> studentValidation = new Validation<>();
                Set<String> validationUser = userValidation.valid(student.getUser());
                validationUser.addAll(studentValidation.valid(student));
                if (!validationUser.isEmpty()) {
                    validationUser.forEach(System.out::println);
                    return null;
                }
                studentRepo.saveStudent(session, student);
                return student;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }

    }

    public Student findById(Long id) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Student student = studentRepo.findById(session, id);
                session.getTransaction().commit();
                return student;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    public List<Student> findAll() {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                List<Student> students = studentRepo.getAllStudents(session);
                session.getTransaction().commit();
                return students;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    public List<StudentDto> getStudentSimpleWithSomeProperties() {
        List<Student> students = findAll();
        List<StudentDto> simpleStudentDto = new ArrayList<>();
        for (Student std : students) {
            simpleStudentDto.add(new StudentDto(std.getStudentNumber()
                    , std.getUser().getName().getFirstName()
                    , std.getUser().getName().getLastName()));
        }
        return simpleStudentDto;

    }

    public Student fetchByUserId(User user) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            session.beginTransaction();
            Student student=studentRepo.fetchStudentByUserId( session, user);
            session.getTransaction().commit();
            return student;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteStudent(Long id) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Student student = studentRepo.findById(session, id);
                studentRepo.deleteByEntity(session, student);
                session.getTransaction().commit();

            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }

    }

    public Student studentUpdate(Student student) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            session.beginTransaction();
            Validation<User> userValidation = new Validation<>();
            Validation<Student> studentValidation = new Validation<>();
            Set<String> validationUser = userValidation.valid(student.getUser());
            validationUser.addAll(studentValidation.valid(student));
            if (!validationUser.isEmpty()) {
                validationUser.forEach(System.out::println);
            }
            studentRepo.updatestudent(session, student);
            return student;
        }
    }

}
