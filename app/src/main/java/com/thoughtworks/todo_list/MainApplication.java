package com.thoughtworks.todo_list;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import com.thoughtworks.todo_list.common.GlobalNotification;
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

    private static final String CHANNEL_ID = "com.thoughtworks.todo-list";
    private static final String CHANNEL_NAME = "TO_DO_LIST";

    @Override
    public void onCreate() {
        super.onCreate();
        userRepository = new UserRepositoryImpl(userDataSource(), new UserRemoteDataSourceImpl());
        taskRepository = new TaskRepositoryImpl(dbTaskDataSource());
        createNotificationChannel();
        createNotificationBuilder();
        setNotificationManager();
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

    private void createNotificationChannel() {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
        channel.setDescription(CHANNEL_ID);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotificationBuilder(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        GlobalNotification.setNotificationCompatBuilderInstance(builder);
    }

    private void setNotificationManager() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        GlobalNotification.setNotificationManager(notificationManager);
    }
}
