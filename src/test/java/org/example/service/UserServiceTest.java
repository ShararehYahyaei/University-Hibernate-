package org.example.service;

import org.example.entity.Name;
import org.example.entity.Type;
import org.example.entity.User;
import org.example.exception.AuthenticationException;
import org.example.exception.ValidationException;
import org.example.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.example.exception.MessageException.AUTHENTICATION_EXCEPTION;
import static org.example.exception.MessageValidaton.USERNAME_DUPLICATED;
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
    void given_empty_username_then_AuthenticationException_is_expected() {
        String emptyUsername = "  ";
        AuthenticationException exception = assertThrows(AuthenticationException.class,
                () -> userService.checkUsernameAndPassword(emptyUsername, "validPassword"));
        assertEquals(AUTHENTICATION_EXCEPTION, exception.getMessage());

    }

    @Test
    void given_empty_password_then_AuthenticationException_is_expected() {
        String username = "validUsername";
        String emptyPassword = "";

        AuthenticationException exception = assertThrows(AuthenticationException.class,
                () -> userService.checkUsernameAndPassword(username, emptyPassword));
        assertEquals(AUTHENTICATION_EXCEPTION, exception.getMessage());

    }

    @Test
    void given_duplicated_username_then_getting_error_is_expected() {

        Mockito.when(userRepo.checkUserNameIsExisted(Mockito.any(), Mockito.any())).thenReturn(true);
        ValidationException exception = assertThrows(ValidationException.class,
                () -> userService.isExistedUserName("username"));
        assertEquals(USERNAME_DUPLICATED, exception.getMessage());

    }


}