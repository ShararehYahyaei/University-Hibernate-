package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.Lesson;
import org.example.entity.Student;
import org.example.entity.StudentsScore;
import org.example.entity.Teacher;
import org.example.repository.StudentScoreRepo;
import org.hibernate.Session;


public class StudentScoreService {

    private final static StudentScoreRepo stdRepo = new StudentScoreRepo();


    public void saveScoreForStudent(Student student, Teacher teacher, Lesson lesson, double score) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                stdRepo.saveMainTableRecord(session, student, teacher, lesson, score);
                session.getTransaction().commit();

            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }


    public double getScore(Long student_id, Long teacher_id, Long lesson_id) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                double score = stdRepo.getScoreForStudentTeacherLesson(session, student_id, teacher_id, lesson_id);
                session.getTransaction().commit();
                return score;
            } catch (Exception e) {
                session.getTransaction().rollback();
                return 0;
            }
        }
    }
}