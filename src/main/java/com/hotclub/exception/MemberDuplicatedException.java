package com.hotclub.exception;

public class MemberDuplicatedException extends RuntimeException {

    String username;

    public MemberDuplicatedException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
