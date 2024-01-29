package com.example.shoppingservice.exception;

import org.springframework.lang.NonNull;

public class NotFoundException extends RuntimeException {

    public NotFoundException(@NonNull String message) {
        super(message);
    }
}
