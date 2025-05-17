package com.kafu.kafu.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApplicationErrorEnum {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user not found."),
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "address not found."),
    INVALID_PROBLEM_STATUS(HttpStatus.BAD_REQUEST, "INVALID PROBLEM STATUS."),
    REJECTION_REASON_REQUIRED(HttpStatus.BAD_REQUEST, "REJECTION REASON REQUIRED."),
    DONATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "DONATION NOT FOUND."),
    GOV_NOT_FOUND(HttpStatus.BAD_REQUEST, "GOV NOT FOUND."),
    PROBLEM_NOT_FOUND(HttpStatus.BAD_REQUEST, "PROBLEM NOT FOUND."),
    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "CATEGORY NOT FOUND."),
    PHOTO_NOT_FOUND(HttpStatus.BAD_REQUEST, "PHOTO NOT FOUND."),
    PROGRESS_NOT_FOUND(HttpStatus.BAD_REQUEST, "PROGRESS NOT FOUND."),
    SOLUTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "SOLUTION NOT FOUND.")

    ;

    private final HttpStatus status;
    private final String message;

    ApplicationErrorEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    }
