package com.thoughtworks.todo_list;

import android.app.Application;
import android.os.SystemClock;

import androidx.room.Room;

import com.thoughtworks.todo_list.repository.AppDatabase;
import com.thoughtworks.todo_list.repository.task.DBTaskDataSource;
import com.thoughtworks.todo_list.repository.task.TaskRepositoryImpl;
import com.thoughtworks.todo_list.repository.user.UserDataSource;
import com.thoughtworks.todo_list.repository.user.UserRemoteDataSourceImpl;
import com.thoughtworks.todo_list.repository.user.UserRepositoryImpl;
import com.thoughtworks.todo_list.repository.user.entity.User;
import com.thoughtworks.todo_list.ui.create_task.TaskRepository;
import com.thoughtworks.todo_list.ui.login.UserRepository;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainApplication extends Application {
    private UserRepository userRepository;
    private AppDatabase db;
    private TaskRepository taskRepository;
    private User currentUser;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate() {
        super.onCreate();
        userRepository = new UserRepositoryImpl(userDataSource(), new UserRemoteDataSourceImpl());
        taskRepository = new TaskRepositoryImpl(dbTaskDataSource());
    }


    public UserDataSource userDataSource() {
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, this.getClass().getSimpleName()).build();
        return db.userDBDataSource();
    }

    public DBTaskDataSource dbTaskDataSource() {
        return db.taskDataSource();
    }

    public TaskRepository taskRepository() {
        return taskRepository;
    }

    public UserRepository userRepository() {
        return userRepository;
    }

    @Override
    public void onTerminate() {
        db.close();
        compositeDisposable.dispose();
        super.onTerminate();
    }

    public User getCurrentUser() {
        Disposable disposable = userRepository.findUser()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(compositeDisposable::add)
                .doOnSuccess(user1 -> currentUser = user1)
                .subscribe();
        compositeDisposable.add(disposable);
        SystemClock.sleep(700);
        return currentUser;
    }

}
