package com.sprk.student_file_upload.exception;

public class EmailAlreadyExits extends RuntimeException {
    public EmailAlreadyExits(String message) {
        super(message);
    }
}
