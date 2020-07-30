package com.thoughtworks.todo_list.repository.task;

import com.thoughtworks.todo_list.repository.task.entity.Task;
import com.thoughtworks.todo_list.ui.create_task.TaskRepository;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public class TaskRepositoryImpl implements TaskRepository {
    private DBTaskDataSource dbTaskDataSource;

    public TaskRepositoryImpl(DBTaskDataSource dbTaskDataSource) {
        this.dbTaskDataSource = dbTaskDataSource;
    }

    @Override
    public Single<Long> saveTask(Task task) {
        return dbTaskDataSource.insertTask(task);
    }

    @Override
    public Maybe<List<Task>> getAllTask() {
        return dbTaskDataSource.getAllTask();
    }


}
