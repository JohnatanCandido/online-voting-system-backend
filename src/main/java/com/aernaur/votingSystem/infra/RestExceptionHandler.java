package com.aernaur.votingSystem.infra;

import com.aernaur.votingSystem.exceptions.EntityNotFoundException;
import com.aernaur.votingSystem.exceptions.ProfilePicUploadException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RestExceptionMessage> personNotFoundHandler(EntityNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestExceptionMessage(exception.getMessage()));
    }

    @ExceptionHandler(ProfilePicUploadException.class)
    public ResponseEntity<RestExceptionMessage> profilePicUploadHandler(ProfilePicUploadException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestExceptionMessage(exception.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<RestExceptionMessage.ExceptionDetail> details = parseMethodArgumentValidations(exception);
        var message = new RestExceptionMessage("Validation errors occurred", details);
        return ResponseEntity.status(status).body(message);
    }

    private List<RestExceptionMessage.ExceptionDetail> parseMethodArgumentValidations(MethodArgumentNotValidException exception) {
        List<RestExceptionMessage.ExceptionDetail> messages = new ArrayList<>();

        for (FieldError error: exception.getFieldErrors()) {
            String field = error.getField();
            String validation = switch (Objects.requireNonNull(error.getCode())) {
                case "NotBlank", "NotNull" -> "is required.";
                case "Past" -> "must be a past date.";
                default -> error.getDefaultMessage();
            };
            messages.add(new RestExceptionMessage.ExceptionDetail(field, validation));
        }
        return messages;
    }
}
