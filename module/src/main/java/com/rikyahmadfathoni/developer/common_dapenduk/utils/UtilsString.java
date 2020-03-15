package com.rikyahmadfathoni.developer.common_dapenduk.utils;

public class UtilsString {

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String validateNull(String value) {
        return value != null ? value.trim() : "";
    }
}
