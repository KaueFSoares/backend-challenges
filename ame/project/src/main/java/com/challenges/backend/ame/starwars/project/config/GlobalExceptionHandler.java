package com.challenges.backend.ame.starwars.project.config;

import com.challenges.backend.ame.starwars.project.i18n.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(ServiceException ex, WebRequest request) {
        return buildErrorResponse(ex.getHttpStatus(), request, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, request, ex.getFieldErrors().stream().map(ValidationErrorResponse::new).toList());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, WebRequest request, Object message) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    public record ValidationErrorResponse(
            String field,
            String message
    ) {
        public ValidationErrorResponse(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    public record ErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            Object message,
            String path
    ) {
    }

}
