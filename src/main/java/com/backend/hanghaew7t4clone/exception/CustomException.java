package com.backend.hanghaew7t4clone.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends IllegalArgumentException {
    private final ErrorCode errorCode;
}
