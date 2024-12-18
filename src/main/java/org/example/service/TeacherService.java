package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.Teacher;
import org.example.repository.StudentRepo;

public class TeacherService {
    private StudentRepo adminrepo=new StudentRepo();

//    public Teacher save(Teacher teacher) {
//        try (var session = SessionFactoryInstance.sessionFactory.openSession()) {
//            try {
//                session.beginTransaction();
//                adminrepo.saveTeacher(session, teacher);
//                session.getTransaction().commit();
//                return teacher;
//            } catch (Exception e) {
//                session.getTransaction().rollback();
//                throw new RuntimeException(e);
//            }
//        }
//    }
}
