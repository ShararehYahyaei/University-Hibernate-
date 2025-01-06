package org.example.repository;

import org.example.config.SessionFactoryInstance;
import org.example.entity.Lesson;
import org.example.entity.Student;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;


public class LessonRepo {
    public Lesson saveLesson(Lesson lesson) {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                session.beginTransaction();
                session.persist(lesson);
                return lesson;
            } catch (Exception e) {
                session.getTransaction().rollback();
                return null;
            }
        }
    }

    public int deleteById(Session session, Long id) {
        return session.createMutationQuery(
                        "DELETE FROM Lesson l WHERE l.id = :id"
                )
                .setParameter("id", id)
                .executeUpdate();
    }


    public Lesson findById(Session session, Long id) {
        return session.get(Lesson.class, id);

    }

    public List<Lesson> getAllLessons() {
        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
            try {
                return session.createQuery("from Lesson", Lesson.class).list();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            }
        }
    }

    public Lesson updateLesson(Session session, Lesson lesson) {
        session.merge(lesson);
        session.flush();
        return session.get(Lesson.class, lesson.getId());
    }

    public List<Student> findStudentsByLessonId(Session session, Long lessonId) {
        String hql = "SELECT s FROM Student s JOIN s.lessons l WHERE l.id = :lessonId";
        Query query = session.createQuery(hql, Student.class);
        query.setParameter("lessonId", lessonId);
        List<Student> students = query.getResultList();
        return students;

    }

}


