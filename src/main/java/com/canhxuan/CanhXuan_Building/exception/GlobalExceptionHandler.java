package com.canhxuan.CanhXuan_Building.exception;

import com.canhxuan.CanhXuan_Building.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException ex) {
        ApiResponse<Void> response = new ApiResponse<>(false, ex.getMessage(), null, null);
        return ResponseEntity.ok().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        ApiResponse<Void> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage("Validation failed");
        response.setErrors(errors);
        response.setData(null);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ApiResponse<?>> handleTransactionSystemException(TransactionSystemException ex) {
        System.err.println("Transaction failed: " + ex);
        Throwable rootCause = ex.getRootCause();
        if (rootCause instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) rootCause;
            List<String> errors = cve.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ApiResponse.builder()
                    .success(false)
                    .message("Validation failed")
                    .errors(errors)
                    .build());
        }

        if (rootCause instanceof DataIntegrityViolationException) {
            return handleDataIntegrityViolation((DataIntegrityViolationException) rootCause);
        }

        if (rootCause instanceof SQLException) {
            SQLException sqlEx = (SQLException) rootCause;
            String message = parseSQLException(sqlEx);

            return ResponseEntity.ok(ApiResponse.builder()
                    .success(false)
                    .message(message)
                    .build());
        }

        String errorMessage = rootCause != null
                ? rootCause.getMessage()
                : ex.getMessage();

        return ResponseEntity.ok(ApiResponse.builder()
                .success(false)
                .message("Transaction failed: " + errorMessage)
                .build());
    }


    private String parseSQLException(SQLException e) {
        String message = e.getMessage().toLowerCase();

        if (message.contains("duplicate entry") || message.contains("unique constraint")) {
            if (message.contains("username")) {
                return "Username already exists";
            } else if (message.contains("email")) {
                return "Email already exists";
            }
            return "This value already exists in the system";
        }

        if (message.contains("foreign key constraint")) {
            return "Cannot delete because this record is being used";
        }

        if (message.contains("data too long")) {
            return "Input data is too long";
        }

        return "Database error occurred";
    }

    // Handle DataIntegrityViolationException
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        String message = "Data integrity violation";

        if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {

            Throwable cause = e.getCause();
            String causeMessage = cause.getMessage().toLowerCase();

            if (causeMessage.contains("duplicate") || causeMessage.contains("unique")) {
                message = "Đã tồn tại dữ liệu trùng lặp";
            } else if (causeMessage.contains("foreign key")) {
                message = "Không thể xóa do bản ghi này có dữ liệu liên quan";
            }
        }

        System.err.println("Data integrity violation: " +  e);

        return ResponseEntity.ok(ApiResponse.builder()
                .success(false)
                .message(message)
                .build());
    }

//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(ResponseStatusException ex) {
//        ApiResponse<Void> response = new ApiResponse<>();
//        response.setSuccess(false);
//        response.setMessage(ex.getReason());
//        response.setData(null);
//        return ResponseEntity.status(ex.getStatusCode()).body(response);
//    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentialsException(BadCredentialsException ex) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage("Invalid username or password");
        response.setData(null);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(AuthenticationException ex) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setSuccess(false);
        response.setMessage(ex.getMessage());
        response.setData(null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
