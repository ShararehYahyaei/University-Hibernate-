package org.example.config;

import org.example.entity.Student;
import org.example.entity.Teacher;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class SessionFactoryInstance {
    public static SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
             .addAnnotatedClass(Student.class)
               // .addAnnotatedClass(Teacher.class)
                .buildSessionFactory();

    }
}
