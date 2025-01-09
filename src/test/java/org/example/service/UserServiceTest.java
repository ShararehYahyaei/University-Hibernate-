package org.example.service;

import org.example.config.SessionFactoryInstance;
import org.example.entity.Name;
import org.example.entity.Type;
import org.example.entity.User;
import org.example.repository.UserRepo;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private final UserRepo userRepo = Mockito.mock(UserRepo.class);
    private final UserService userService = new UserService(userRepo);

    @Test
    void check_username_and_Password_with_correct_format() {
        String username = "Admin2";
        String passsword = "Admin2";
        User user2 = new User(new Name("Admin2", "Admin2"), username, passsword, Type.Admin,
                "Admin2", "Admin2", "Admin2");
        Mockito.when(userRepo.checkUsernameAndPassword(Mockito.any(), Mockito.any())).thenReturn(user2);
        User userExpected = userService.checkUsernameAndPassword(username, passsword);
        assertEquals(user2.getEmail(), userExpected.getEmail());

    }


    @Test
    void check_username_and_Password_with_wrong_format() {
        String emptyUsername = "  ";
        String emptyPassword = "";

        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class,
                () -> userService.checkUsernameAndPassword(emptyUsername, "validPassword"));
        assertEquals("Username or password cannot be null or empty", exception1.getMessage());
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class,
                () -> userService.checkUsernameAndPassword("validUsername", emptyPassword));
        assertEquals("Username or password cannot be null or empty", exception2.getMessage());

    }


}