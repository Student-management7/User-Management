package com.easyWaySolution.User_Management.Helper;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
