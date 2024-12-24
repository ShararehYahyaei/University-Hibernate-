package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.Name;
import org.example.entity.Type;
import org.example.entity.User;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    static UserService userService = new UserService();

    @AfterEach
    public void afterAll() {
        clearAll();
    }

    @Test
    void checkUsernameAndPassword() {
        String username = "Admin2";
        String passsword = "Admin2";
        User user2 = new User(new Name("Admin2", "Admin2"), username, passsword, Type.Admin,
                "Admin2", "Admin2", "Admin2");
        userService.saveAdmin(user2);
        User userExpected = userService.checkUsernameAndPassword(username, passsword);
        assertEquals(user2.getEmail(), userExpected.getEmail());

    }

    @Test
    void checkUsernameAndPasswordWithWrongPassword() {
        String username = "Admin2";
        String passsword = "Admin2";
        User user2 = new User(new Name("Admin2", "Admin2"), username, passsword, Type.Admin,
                "Admin2", "Admin2", "Admin2");
        userService.saveAdmin(user2);
        User userExpected = userService.checkUsernameAndPassword(" ", "passsword");
        assertNull( userExpected);

    }

    public void clearAll() {
        try (Session session = SessionFactoryInstance.sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from User ").executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}