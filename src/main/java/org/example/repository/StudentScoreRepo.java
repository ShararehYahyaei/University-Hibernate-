package org.example.repository;


import org.example.entity.Lesson;
import org.example.entity.Student;
import org.example.entity.Teacher;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.stream.Collectors;

public class StudentScoreRepo {

    public void saveMainTableRecord(Session session, Student student, Teacher teacher, Lesson lesson, double score) {
        String sql = "INSERT INTO MainTable (student_id, teacher_id, lesson_id, score) VALUES (:studentId, :teacherId, :lessonId, :score)";
        session.createNativeQuery(sql)
                .setParameter("studentId", student.getId())
                .setParameter("teacherId", teacher.getId())
                .setParameter("lessonId", lesson.getId())
                .setParameter("score", score)
                .executeUpdate();
    }
    public double getScoreForStudentTeacherLesson(Session session, Long studentId, Long teacherId, Long lessonId) {

        String sql = "SELECT score FROM MainTable " +
                "WHERE student_id = :studentId " +
                "AND teacher_id = :teacherId " +
                "AND lesson_id = :lessonId";
        Query query = session.createNativeQuery(sql);
        query.setParameter("studentId", studentId);
        query.setParameter("teacherId", teacherId);
        query.setParameter("lessonId", lessonId);
        Object result = query.getSingleResult();


        if (result == null) {
            return 0.0;
        }
        return ((Number) result).doubleValue();
    }


    public double showScoreForStudent(Session session, Long studentId,Long lessonId) {
        String sql = "SELECT score FROM MainTable " +
                "WHERE student_id = :studentId " +
                "AND lesson_id = :lessonId";
        Query query = session.createNativeQuery(sql);
        query.setParameter("studentId", studentId);
        query.setParameter("lessonId", lessonId);
        Object result = query.getSingleResult();
        return ((Number) result).doubleValue();
    }

}

