package org.example.repository;

import org.example.entity.Lesson;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class LessonRepo {
    public Lesson saveLesson(Session session, Lesson lesson) {
        session.persist(lesson);
        return lesson;
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

    public List<Lesson> getAllLessons(Session session) {
        return session.createQuery("from Lesson",Lesson.class).list();
    }

}
