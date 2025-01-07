package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.Lesson;
import org.example.entity.Student;
import org.example.entity.Teacher;
import org.example.repository.StudentScoreRepo;


public class StudentScoreService {

    private final StudentScoreRepo stdRepo;

    public StudentScoreService(StudentScoreRepo stdRepo) {
        this.stdRepo = stdRepo;
    }

    public void saveScoreForStudent(Student student, Teacher teacher, Lesson lesson, double score) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                stdRepo.studentScore(session, student, teacher, lesson, score);
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
                double score = stdRepo.getLessonScoreByStudent(session, student_id, teacher_id, lesson_id);
                session.getTransaction().commit();
                return score;
            } catch (Exception e) {
                session.getTransaction().rollback();
                return 0;
            }
        }
    }
//    public double ShowScoreForStudent(Long studentId,Long LessonId) {
//        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
//            try {
//                session.beginTransaction();
//                double score = stdRepo.showScoreForStudent(session, studentId,LessonId);
//                session.getTransaction().commit();
//                return score;
//            } catch (Exception e) {
//                session.getTransaction().rollback();
//                return 0;
//            }
//        }
//
//    }

}