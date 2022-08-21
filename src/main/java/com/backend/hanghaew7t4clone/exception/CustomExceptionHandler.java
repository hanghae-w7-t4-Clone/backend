package com.backend.hanghaew7t4clone.exception;

import com.backend.hanghaew7t4clone.shared.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
  @ExceptionHandler(CustomException.class)
  protected ResponseEntity<ErrorResponse> handlingCustomException(CustomException e) {
    return ErrorResponse.toResponseEntity(e.getErrorCode());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException exception) {
    String errorMessage = exception.getBindingResult()
        .getAllErrors()
        .get(0)
        .getDefaultMessage();
    return new ResponseEntity<>(Message.fail("BAD_REQUEST", errorMessage), HttpStatus.BAD_REQUEST);
  }
}
