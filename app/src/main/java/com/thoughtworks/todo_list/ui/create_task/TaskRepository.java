package com.thoughtworks.todo_list.ui.create_task;

import com.thoughtworks.todo_list.repository.task.entity.Task;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public interface TaskRepository {
    Single<Long> saveTask(Task task);

    Maybe<List<Task>> getAllTask();
}