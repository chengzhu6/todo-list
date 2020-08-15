package com.thoughtworks.todo_list.ui.create_task;

import com.thoughtworks.todo_list.R;
import com.thoughtworks.todo_list.common.GlobalNotification;
import com.thoughtworks.todo_list.repository.task.entity.Task;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class MyNotification {
    private static final String NOTIFICATION_GROUP_KEY = "com.thoughtworks.todo-list";

    public static void addNotification(Task task) {
        GlobalNotification.getNotificationCompatBuilderInstance()
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(task.getTitle())
                .setContentText(task.getDescription())
                .setGroup(NOTIFICATION_GROUP_KEY)
                .build();
    }

    private void publishNotification() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

            }
        }, new Date());
    }
}
