package com.thoughtworks.todo_list.ui.home;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.thoughtworks.todo_list.repository.task.entity.Task;
import com.thoughtworks.todo_list.ui.create_task.TaskRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Task>> tasks;
    private TaskRepository taskRepository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private MutableLiveData<List<Task>> tasks() {
        if (tasks == null) {
            tasks = new MutableLiveData<>();
        }
        return tasks;
    }

    public void observerTasks(LifecycleOwner lifecycleOwner, Observer<List<Task>> observer) {
        tasks().observe(lifecycleOwner, observer);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public void getAllTask() {
        Disposable disposable = taskRepository.getAllTask()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(tasks1 -> tasks.postValue(tasks1));
        compositeDisposable.add(disposable);
    }
}

