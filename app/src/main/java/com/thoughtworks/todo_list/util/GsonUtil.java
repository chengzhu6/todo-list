package com.thoughtworks.todo_list.util;

import com.google.gson.Gson;

public class GsonUtil {
    private static final Gson gson;

    static {
        gson = new Gson();
    }

    public static  <T> T fromJson(String json, Class<T> type) {
        return gson.fromJson(json, type);
    }
}
