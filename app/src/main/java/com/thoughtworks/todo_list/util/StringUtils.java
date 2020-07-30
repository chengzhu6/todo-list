package com.thoughtworks.todo_list.util;

public class StringUtils {

    public static boolean isNotBlank(String value) {
        return value != null && !value.isEmpty();
    }
}
