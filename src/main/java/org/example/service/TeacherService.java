package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.Student;
import org.example.entity.Teacher;
import org.example.repository.StudentRepo;
import org.example.repository.TeacherRepo;

import java.util.List;
import java.util.Optional;

public class TeacherService {
  /*  private final static TeacherRepo teacherRepo = new TeacherRepo();

    public Teacher saveTeacher(Teacher teacher) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                teacherRepo.saveSTeacher(session, teacher);
                session.getTransaction().commit();
                return teacher;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }
    public Teacher update(Student student) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Student newStudent = teacherRepo.findById(session, Teacher.getId())
                        .orElseThrow(() -> new RuntimeException("Student not found"));
                newStudent.setName(student.getName());
                newStudent.setUserName(student.getUserName());
                newStudent.setEmail(student.getEmail());
                newStudent.setPhoneNumber(student.getPhoneNumber());
                newStudent.setPassword(student.getPassword());
                newStudent.setNationalCode(student.getNationalCode());
                newStudent.setStudentNumber(student.getStudentNumber());
                session.getTransaction().commit();
                return newStudent;
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }
    public Optional<Student> findById(Long id) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                Optional<Student>student= studentRepo.findById(session, id);
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
                List<Student> phoneBooks = studentRepo.getAllPhoneBook(session);
                session.getTransaction().commit();
                return phoneBooks;

            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }
    public String deleteStudent(Long id){
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try{
                session.beginTransaction();
                studentRepo.deleteById(session, id);
                session.getTransaction().commit();
                return "Delete Successfully ...";

            }catch (Exception e){
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }

    }
*/

}
