package com.snowball.board.common.exception.handler;

import com.snowball.board.common.exception.dto.ExceptionDto;
import com.snowball.board.common.exception.message.AuthExceptionMessage;
import com.snowball.board.common.exception.message.ExceptionMessage;
import com.snowball.board.common.exception.model.ConflictException;
import com.snowball.board.common.exception.model.NoContentException;
import com.snowball.board.common.exception.model.UnauthorizedException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.*;
class RestExceptionHandlerAdvisorTest {

    private final RestExceptionHandlerAdvisor restExceptionHandlerAdvisor = new RestExceptionHandlerAdvisor();

    private final RestExceptionHandlerAdvisor exceptionHandlerAdvisor = new RestExceptionHandlerAdvisor();

    @Test
    void handleConstraintViolationExceptionTest() {
        ConstraintViolationException exception = new ConstraintViolationException(ExceptionMessage.MISSING_REQUIRED_FIELD.message(), null, null);

        ResponseEntity<ExceptionDto> response = exceptionHandlerAdvisor.exceptionHandler(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatusCode());
        assertEquals(ExceptionMessage.MISSING_REQUIRED_FIELD.message(), response.getBody().getMessage());
    }

    @Test
    void handleConflictExceptionTest() {
        ConflictException exception = new ConflictException(ExceptionMessage.ALREADY_EXISTS_USER.message());

        ResponseEntity<ExceptionDto> response = exceptionHandlerAdvisor.exceptionHandler(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(HttpStatus.CONFLICT.value(), response.getBody().getStatusCode());
        assertEquals(ExceptionMessage.ALREADY_EXISTS_USER.message(), response.getBody().getMessage());
    }

    @Test
    void handleUnauthorizedExceptionTest() {
        UnauthorizedException exception = new UnauthorizedException(AuthExceptionMessage.NOT_AUTHORIZED_ACCESS.message());

        ResponseEntity<ExceptionDto> response = exceptionHandlerAdvisor.exceptionHandler(exception);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getBody().getStatusCode());
        assertEquals(AuthExceptionMessage.NOT_AUTHORIZED_ACCESS.message(), response.getBody().getMessage());
    }

    @Test
    void handleNoHandlerFoundExceptionTest() {
        NoHandlerFoundException exception = new NoHandlerFoundException("GET", "/path", null);

        ResponseEntity<Object> response = exceptionHandlerAdvisor.handleNoHandlerFoundException(exception, null, HttpStatus.NOT_FOUND, null);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());

    }

    @Test
    void handleBadCredentialsExceptionTest() {
        BadCredentialsException exception = new BadCredentialsException(AuthExceptionMessage.NOT_AUTHORIZED_ACCESS.message());

        ResponseEntity<ExceptionDto> response = exceptionHandlerAdvisor.exceptionHandler(exception);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getBody().getStatusCode());
        assertEquals(AuthExceptionMessage.NOT_AUTHORIZED_ACCESS.message(), response.getBody().getMessage());
    }

    @Test
    void handleNoContentExceptionTest() {
        NoContentException exception = new NoContentException("No Content");

        ResponseEntity<ExceptionDto> response = exceptionHandlerAdvisor.handleNoContentException(exception);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(HttpStatus.NO_CONTENT.value(), (response.getBody()).getStatusCode());
        assertEquals("No Content", (response.getBody()).getMessage());
    }
}