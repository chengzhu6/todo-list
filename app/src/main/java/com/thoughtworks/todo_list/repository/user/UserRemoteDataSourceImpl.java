package com.thoughtworks.todo_list.repository.user;

import com.thoughtworks.todo_list.repository.user.entity.User;
import com.thoughtworks.todo_list.util.HttpUtil;

import io.reactivex.Maybe;

public class UserRemoteDataSourceImpl implements UserRemoteDataSource{
    private final static String USER_INFO_URL = "https://twc-android-bootcamp.github.io/fake-data/data/user.json";

    @Override
    public Maybe<User> requestUser() {
        return Maybe.create(emitter -> {
            User user = HttpUtil.get(USER_INFO_URL, User.class);
            emitter.onSuccess(user);
        });
    }
}
