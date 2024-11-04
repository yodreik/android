package com.example.yodreik.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Validator {
    public static boolean Email(String s) {
        String pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return s.matches(pattern);
    }

    public static boolean Date(String s) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(s);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean Duration(String durationStr) {
        if (durationStr == null || durationStr.isEmpty()) return false;

        try {
            int duration = Integer.parseInt(durationStr);
            return duration >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
