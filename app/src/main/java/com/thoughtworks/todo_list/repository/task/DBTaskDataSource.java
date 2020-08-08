package com.thoughtworks.todo_list.repository.task;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.thoughtworks.todo_list.repository.task.entity.Task;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface DBTaskDataSource extends TaskDataSource {
    @Query("select * from task")
    Maybe<List<Task>> getAllTask();

    @Insert
    Single<Long> insertTask(Task task);
}