package com.thoughtworks.todo_list.repository.user;

import com.thoughtworks.todo_list.repository.user.entity.User;
import com.thoughtworks.todo_list.ui.login.UserRepository;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public class UserRepositoryImpl implements UserRepository {
    private UserDataSource dataSource;
    private UserRemoteDataSource userRemoteDataSource;

    public UserRepositoryImpl(UserDataSource dataSource, UserRemoteDataSource userRemoteDataSource) {
        this.userRemoteDataSource = userRemoteDataSource;
        this.dataSource = dataSource;
    }

    public Maybe<User> findByName(String name) {
        return dataSource.findByName(name)
                .switchIfEmpty(userRemoteDataSource.requestUser()
                        .doOnSuccess(user -> dataSource.save(user).subscribe()));
    }

    @Override
    public Completable save(User user) {
        return dataSource.save(user);
    }

    @Override
    public Maybe<User> findUser() {
        return dataSource.findUser();
    }
}
