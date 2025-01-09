package org.example.exception;

import static org.example.exception.MessageException.AUTHENTICATION_EXCEPTION;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException( ) {
        super(AUTHENTICATION_EXCEPTION);
    }

}
