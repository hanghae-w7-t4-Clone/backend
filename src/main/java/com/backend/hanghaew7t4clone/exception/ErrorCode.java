package com.backend.hanghaew7t4clone.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ErrorCode {

   //MEMBER
   MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "M001", "해당 유저를 찾을 수 없습니다."),
   AUTHOR_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "M002", "해당 작성자를 찾을 수 없습니다."),
   TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST.value(), "M003","만료된 액세스 토큰 입니다."),
   REFRESH_TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST.value(), "M004","만료된 리프레시 토큰 입니다."),
   INVALID_TOKEN(HttpStatus.BAD_REQUEST.value(), "M005","유효하지 않은 토큰 입니다."),


   //CARD
   CARD_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "C001","해당 게시물을 찾을 수 없습니다."),

   //COMMENT
   COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CM001","해당 댓글을 찾을 수 없습니다."),

   //SUBCOMMENT
   SUB_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CM001","해당 대댓글을 찾을 수 없습니다."),

   // FILE
   FILE_TYPE_INVALID(HttpStatus.BAD_REQUEST.value(), "F001","잘못된 파일 형식입니다."),
   FILE_SIZE_INVALID(HttpStatus.BAD_REQUEST.value(), "F002","파일 크기가 너무 큽니다."),



   ;

   private final int httpStatus;
   private final String code;
   private final String message;


}