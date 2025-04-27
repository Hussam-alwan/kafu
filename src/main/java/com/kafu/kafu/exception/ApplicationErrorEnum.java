package com.kafu.kafu.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApplicationErrorEnum {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user not found."),
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "address not found."),
    CART_IS_EMPTY(HttpStatus.NOT_FOUND, "Cart is empty."),
    CART_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "Cart Item not found.");

    private final HttpStatus status;
    private final String message;

    ApplicationErrorEnum(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    }
