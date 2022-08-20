package com.backend.hanghaew7t4clone.exception;

import com.backend.hanghaew7t4clone.shared.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException exception) {
    String errorMessage = exception.getBindingResult()
        .getAllErrors()
        .get(0)
        .getDefaultMessage();

    return new ResponseEntity<>(Message.fail("BAD_REQUEST", errorMessage), HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(InvalidTokenException.class)
  public ResponseEntity<?> handlingInvalidTokenExceptions(){
    return new ResponseEntity<>(Message.fail("INVALID_TOKEN", "token is invalid")
            , HttpStatus.UNAUTHORIZED);
  }
  @ExceptionHandler(MemberNotFoundException.class)
  public ResponseEntity<?> handlingMemberNotFoundExceptions(){
    return new ResponseEntity<>(Message.fail("NOT_FOUND", "member is not exist")
            , HttpStatus.NOT_FOUND);
  }
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<?> handlingCardNotFoundExceptions(){
    return new ResponseEntity<>(Message.fail("NOT_FOUND", "card is not exist")
            ,HttpStatus.NOT_FOUND);
  }
//  @ExceptionHandler(SubCommentNotFoundException.class)
//  public ResponseEntity<?> handlingSubCommentNotFoundExceptions(){
//    return new ResponseEntity<>(Message.fail("NOT_FOUND", "sub comment id is not exist")
//            ,HttpStatus.NOT_FOUND);
//  }
//  @ExceptionHandler(CommentNotFoundException.class)
//  public ResponseEntity<?> handlingCommentNotFoundExceptions(){
//    return new ResponseEntity<>(Message.fail("NOT_FOUND", "comment id is not exist")
//            ,HttpStatus.NOT_FOUND);
//  }
  @ExceptionHandler(NotAuthorException.class)
  public ResponseEntity<?> handlingNotAuthorExceptions(){
    return new ResponseEntity<>(Message.fail("BAD_REQUEST", "작성자가 아닙니다.")
            ,HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<?> handlingMaxUploadSizeExceededException(){
    return new ResponseEntity<>(Message.fail("FILE_SIZE_ERROR", "파일 크기가 너무 큽니다.")
            ,HttpStatus.OK);
  }
}
