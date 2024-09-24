package com.example.yodreik.utils;

public class Validator {
    public static boolean Email(String email) {
        String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(pattern);
    }
}
