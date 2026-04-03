package com.rith.group1_spring_mini_project001.exception;

public class InvalidFileException extends RuntimeException{
    public InvalidFileException(String message) {
        super(message);
    }
}
