package com.thoughtworks.todo_list.common;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public final class GlobalNotification {

    private static NotificationCompat.Builder sGlobalNotificationCompatBuilder = null;
    private static NotificationManagerCompat globalNotificationManager = null;


    private GlobalNotification() { }

    public static void setNotificationCompatBuilderInstance (NotificationCompat.Builder builder) {
        sGlobalNotificationCompatBuilder = builder;
    }

    public static NotificationCompat.Builder getNotificationCompatBuilderInstance() {
        return sGlobalNotificationCompatBuilder;
    }

    public static void setNotificationManager(NotificationManagerCompat notificationManager) {
        globalNotificationManager = notificationManager;
    }

    public static NotificationManagerCompat getGlobalNotificationManager() {
        return globalNotificationManager;
    }
}