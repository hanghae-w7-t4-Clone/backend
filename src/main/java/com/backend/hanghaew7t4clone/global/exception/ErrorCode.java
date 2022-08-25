package com.backend.hanghaew7t4clone.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //MEMBER
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "M001", "해당 유저를 찾을 수 없습니다."),
    NOT_AUTHOR(HttpStatus.BAD_REQUEST.value(), "M002", "작성자가 아닙니다."),
    TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST.value(), "M003", "만료된 액세스 토큰 입니다."),
    REFRESH_TOKEN_IS_EXPIRED(HttpStatus.BAD_REQUEST.value(), "M004", "만료된 리프레시 토큰 입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST.value(), "M005", "유효하지 않은 토큰 입니다."),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST.value(),"M006","이미 사용되고 있는 닉네임입니다."),
    INVALID_MEMBER_INFO(HttpStatus.BAD_REQUEST.value(),"M007" ,"잘못된 사용자 정보입니다."),
    UNAUTHORIZED(HttpStatus.BAD_REQUEST.value(),"M008" ,"로그인이 필요합니다."),


    //CARD
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "C001", "해당 게시물을 찾을 수 없습니다."),

    //COMMENT
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CM001", "해당 댓글을 찾을 수 없습니다."),

    //SUB-COMMENT
    SUB_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "CM001", "해당 대댓글을 찾을 수 없습니다."),

    // FILE
    FILE_TYPE_INVALID(HttpStatus.BAD_REQUEST.value(), "F001", "잘못된 파일 형식입니다."),
    FILE_SIZE_INVALID(HttpStatus.BAD_REQUEST.value(), "F002", "파일 크기가 너무 큽니다.");



    private final int httpStatus;
    private final String code;
    private final String message;


}
