package com.kafu.kafu.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException{

    private ApplicationErrorEnum applicationErrorEnum;
    public BusinessException(ApplicationErrorEnum applicationErrorEnum ) {
        super(applicationErrorEnum.getMessage());
        this.applicationErrorEnum= applicationErrorEnum;
    }
}