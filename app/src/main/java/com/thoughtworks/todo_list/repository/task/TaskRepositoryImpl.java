package com.thoughtworks.todo_list.repository.task;

import com.thoughtworks.todo_list.repository.task.entity.Task;
import com.thoughtworks.todo_list.ui.create_task.TaskRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

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

    @Override
    public void updateTaskState(Task task) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                dbTaskDataSource.updateTaskState(task);
            }
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    @Override
    public Completable updateTask(Task task) {
        return dbTaskDataSource.updateTask(task);
    }


}
