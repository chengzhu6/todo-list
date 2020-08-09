package com.thoughtworks.todo_list.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.thoughtworks.todo_list.repository.task.DBTaskDataSource;
import com.thoughtworks.todo_list.repository.task.entity.Task;
import com.thoughtworks.todo_list.repository.user.entity.User;
import com.thoughtworks.todo_list.repository.user.DBUserDataSource;

@Database(entities = {User.class, Task.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DBUserDataSource userDBDataSource();

    public abstract DBTaskDataSource taskDataSource();
}