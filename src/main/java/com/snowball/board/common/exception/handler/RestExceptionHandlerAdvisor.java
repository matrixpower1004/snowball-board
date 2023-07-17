package com.snowball.board.common.exception.handler;

import com.snowball.board.common.exception.dto.ExceptionDto;
import com.snowball.board.common.exception.model.ConflictException;
import com.snowball.board.common.exception.model.InvalidAuthException;
import com.snowball.board.common.exception.model.NoContentException;
import com.snowball.board.common.exception.model.UnauthorizedException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandlerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class, InvalidAuthException.class, IllegalStateException.class})
    public ResponseEntity<ExceptionDto> exceptionHandler(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionDto.builder().statusCode(HttpStatus.BAD_REQUEST.value())
                        .message(String.format("%s", exception.getMessage()))
                        .build());
    }

    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(ConflictException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ExceptionDto.builder().statusCode(HttpStatus.CONFLICT.value())
                        .message(String.format("%s", exception.getMessage()))
                        .build());
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(UnauthorizedException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionDto.builder().statusCode(HttpStatus.UNAUTHORIZED.value())
                        .message(String.format("%s", exception.getMessage()))
                        .build());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionDto.builder().statusCode(HttpStatus.NOT_FOUND.value())
                        .message(String.format("%s", exception.getMessage()))
                        .build());
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ExceptionDto> exceptionHandler(BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ExceptionDto.builder().statusCode(HttpStatus.FORBIDDEN.value())
                        .message(String.format("%s", exception.getMessage()))
                        .build());
    }

    @ExceptionHandler(NoContentException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDto> handleNoContentException(NoContentException exception) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ExceptionDto.builder().statusCode(HttpStatus.NO_CONTENT.value())
                        .message(String.format("%s", exception.getMessage()))
<<<<<<< HEAD
                        .build());
    }
=======
                        .build());}
>>>>>>> 843df07 (feat: #11 - add ExceptionHandlerAdvisor)
}
