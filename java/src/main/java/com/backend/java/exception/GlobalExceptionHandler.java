package com.backend.java.exception;

import com.backend.java.exception.CustomExceptions.EmailAlreadyExistsException;
import com.backend.java.exception.CustomExceptions.InvalidCredentialsException;
import com.backend.java.model.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

      // --- Missing or unreadable JSON body (400) ---
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingRequestBody(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        ErrorResponseDto response = new ErrorResponseDto();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setMessage("Request body is missing or invalid JSON");
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
public ResponseEntity<ErrorResponseDto> handleDuplicateKey(
        DataIntegrityViolationException ex, HttpServletRequest request) {

    String message = "Duplicate entry detected";

    // Optional: detect if it's specifically the email constraint
    if (ex.getMostSpecificCause().getMessage().contains("uk6dotkott2kjsp8vw4d0m25fb7")) {
        message = "Email already exists";
    }

    ErrorResponseDto response = new ErrorResponseDto();
    response.setStatus(HttpStatus.CONFLICT.value());
    response.setError(HttpStatus.CONFLICT.getReasonPhrase());
    response.setMessage(message);
    response.setPath(request.getRequestURI());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
}

    // 1️⃣ Validation errors → 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationErrors(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
          .forEach(err -> fieldErrors.put(err.getField(), err.getDefaultMessage()));

        ErrorResponseDto response = new ErrorResponseDto();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setMessage("Validation failed");
        response.setPath(request.getRequestURI());
        response.setErrors(fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 2️⃣ Business errors → 409 Conflict
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleBusinessErrors(
            EmailAlreadyExistsException ex, HttpServletRequest request) {

        ErrorResponseDto response = new ErrorResponseDto();
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setError(HttpStatus.CONFLICT.getReasonPhrase());
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // 3️⃣ Authentication errors → 401 Unauthorized
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthErrors(
           CustomExceptions.InvalidCredentialsException ex, HttpServletRequest request) {

        ErrorResponseDto response = new ErrorResponseDto();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setError(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // 4️⃣ Fallback for all other exceptions → 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleOtherExceptions(
            Exception ex, HttpServletRequest request) {

        ErrorResponseDto response = new ErrorResponseDto();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.setMessage("Something went wrong");
        response.setPath(request.getRequestURI());

        // Optional: print stack trace for debugging
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
