package com.thoughtworks.todo_list.repository.user;

import com.thoughtworks.todo_list.repository.user.entity.User;

import io.reactivex.Maybe;

public interface UserRemoteDataSource{
    Maybe<User> requestUser();
}
