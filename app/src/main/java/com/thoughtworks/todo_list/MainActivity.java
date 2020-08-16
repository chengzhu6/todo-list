package com.thoughtworks.todo_list;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.thoughtworks.todo_list.repository.user.entity.User;
import com.thoughtworks.todo_list.ui.home.HomeActivity;
import com.thoughtworks.todo_list.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication application = (MainApplication)getApplication();
        User user = application.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("user", "android");
            startActivity(intent);
        }
        finish();
    }
}