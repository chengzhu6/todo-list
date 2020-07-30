package com.thoughtworks.todo_list.ui.create_task;

import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.thoughtworks.todo_list.repository.task.entity.Task;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CreateTaskViewModel extends ViewModel {
    private static final String TAG = "CreateTaskViewModel";

    private MutableLiveData<Task> task;
    private TaskRepository taskRepository;
    private MutableLiveData<TaskCreateResult> taskCreateResult;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private MutableLiveData<TaskCreateResult> getTaskCreateResult() {
        if (taskCreateResult == null) {
            taskCreateResult = new MutableLiveData<>();
        }
        return taskCreateResult;
    }

    public void observerTask(LifecycleOwner lifecycleOwner, Observer<Task> observer) {
        getTask().observe(lifecycleOwner, observer);
    }

    public void observerTaskCreateResult(LifecycleOwner lifecycleOwner, Observer<TaskCreateResult> observer) {
        getTaskCreateResult().observe(lifecycleOwner, observer);
    }

    private MutableLiveData<Task> getTask() {
        if (task == null) {
            task = new MutableLiveData<>();
        }
        return task;
    }

    public void setDate(String date) {
        Task value = task();
        value.setDeadLine(date);
        task.postValue(value);
    }

    public void setTitle(String title) {
        Task value = task();
        value.setTitle(title);
        task.postValue(value);
    }

    public void setRemind(boolean isRemind) {
        Task value = task();
        value.setRemind(isRemind);
        task.postValue(value);
    }

    public void setDescription(String description) {
        Task value = task();
        value.setDescription(description);
        task.postValue(value);
    }

    @NotNull
    private Task task() {
        Task value = task.getValue();
        if (value == null) {
            value = new Task();
        }
        return value;
    }

    public void saveTask() {
        Disposable disposable = taskRepository.saveTask(task.getValue())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(compositeDisposable::add)
                .doOnError(throwable -> {
                    Log.e(TAG, String.format("ErrorMessage: %s", throwable.getMessage()));
                    taskCreateResult.postValue(TaskCreateResult.CREATE_FAILED);
                })
                .subscribe(aLong -> taskCreateResult.postValue(TaskCreateResult.CREATE_SUCCESS));
        compositeDisposable.add(disposable);
    }

}
