package com.example.yodreik.utils;

import android.content.Context;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Toast {

    public static void Info(Context c, String message) {
        Show(c, message, FancyToast.INFO);
    }

    public static void Success(Context c, String message) {
        Show(c, message, FancyToast.SUCCESS);
    }

    public static void Warning(Context c, String message) {
        Show(c, message, FancyToast.WARNING);
    }

    public static void Error(Context c, String message) {
        Show(c, message, FancyToast.ERROR);
    }

    private static void Show(Context c, String message, int level) {
        FancyToast.makeText(c, message, FancyToast.LENGTH_LONG, level, false).show();
    }
}
