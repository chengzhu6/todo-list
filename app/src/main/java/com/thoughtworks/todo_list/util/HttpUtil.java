package com.thoughtworks.todo_list.util;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpUtil {

    private static final OkHttpClient httpClient;
    private static final Request.Builder requestBuilder;

    static {
        httpClient = new OkHttpClient();
        requestBuilder = new Request.Builder();
    }

    public static @NonNull <T> T get(String url, Class<T> responseType) throws Exception {
        Request request = requestBuilder.url(url).build();
        T result = null;
        try (Response response = httpClient.newCall(request).execute()) {
            ResponseBody body = response.body();
            if (body != null) {
                result = GsonUtil.fromJson(body.string(), responseType);
            } else {
                throw new Exception();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result == null) {
            throw new Exception();
        }
        return result;
    }
}
