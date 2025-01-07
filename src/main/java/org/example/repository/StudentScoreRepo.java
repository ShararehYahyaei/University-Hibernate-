package org.example.repository;


import org.example.entity.Lesson;
import org.example.entity.Student;
import org.example.entity.StudentScore;
import org.example.entity.Teacher;
import org.hibernate.Session;

public class StudentScoreRepo {

    public void studentScore(Session session, Student student, Teacher teacher, Lesson lesson, double score) {
        session.persist(new StudentScore(student.getId(), teacher.getId(), lesson.getId(), score));
    }

    public double getLessonScoreByStudent(Session session, Long studentId, Long lessonId,Long teacherId) {

        double Score = session.createMutationQuery(
                        "Select score FROM StudentScore ss WHERE ss.student_id = :studentId AND ss.lesson_id = :lessonId   And ss.teacher_id = :teacherId"
                )
                .setParameter("student_id", studentId)
                .setParameter("lesson_id", lessonId)
                .setParameter("teacher_id", teacherId)
                .executeUpdate();
        return Score;

    }
    public double getLessonScoreByStudent(Session session, Long studentId, Long lessonId) {

        double Score = session.createMutationQuery(
                        "Select score FROM StudentScore ss WHERE ss.student_id = :studentId AND ss.lesson_id = :lessonId "
                )
                .setParameter("student_id", studentId)
                .setParameter("lesson_id", lessonId)
                .executeUpdate();
        return Score;

    }



}

