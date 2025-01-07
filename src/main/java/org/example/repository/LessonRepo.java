package org.example.repository;

import org.example.entity.Lesson;
import org.hibernate.Session;

import java.util.List;


public class LessonRepo {
    public Lesson saveLesson(Session session,Lesson lesson) {
                session.persist(lesson);
                return lesson;
            }

    public void deleteById(Session session,Long id) {
                session.createMutationQuery(
                                "DELETE FROM Lesson l WHERE l.id = :id"
                        )
                        .setParameter("id", id)
                        .executeUpdate();

            }
    public Lesson findById(Session session, Long id) {
        return session.get(Lesson.class, id);

    }

    public List<Lesson> getAllLessons(Session session) {
                return session.createQuery("from Lesson", Lesson.class).list();

        }

    public Lesson update(Session session, Lesson lesson) {
            session.merge(lesson);
            session.flush();
            return session.get(Lesson.class, lesson.getId());
    }
//
//    public List<Student> findStudentsByLessonId(Long lessonId) {
//        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
//            session.beginTransaction();
//            String hql = "SELECT s FROM Student s JOIN s.lessons l WHERE l.id = :lessonId";
//            Query query = session.createQuery(hql, Student.class);
//            query.setParameter("lessonId", lessonId);
//            List<Student> students = query.getResultList();
//            session.getTransaction().commit();
//            return students;
//
//        } catch (Exception e) {
//            e.getStackTrace();
//            throw new RuntimeException(e);
//        }
//
//    }
}

