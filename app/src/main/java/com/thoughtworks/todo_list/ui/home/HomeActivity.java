package com.thoughtworks.todo_list.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.todo_list.MainApplication;
import com.thoughtworks.todo_list.R;
import com.thoughtworks.todo_list.repository.task.entity.Task;
import com.thoughtworks.todo_list.ui.create_task.CreateTaskActivity;
import com.thoughtworks.todo_list.ui.create_task.MyNotification;

import java.time.LocalDateTime;


public class HomeActivity extends AppCompatActivity {

    private RecyclerView taskList;
    private HomeViewModel homeViewModel;
    private TextView totalNumberOfTasks;
    private TextView currentDay;
    private TextView currentMonth;
    private static final  String format = "%d个任务";
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getViews();
        obtainViewModel();
        setClickListener();
        init();

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void getViews() {
        taskList = findViewById(R.id.task_list);
        totalNumberOfTasks = findViewById(R.id.total_number_of_tasks);
        currentDay = findViewById(R.id.current_day);
        currentMonth = findViewById(R.id.current_month);
        toolbar = findViewById(R.id.my_toolbar);
    }

    private void init() {
        initToolbar();
        currentDay.setText(String.format("%s,%s", LocalDateTime.now().getDayOfWeek(),
                LocalDateTime.now().getDayOfMonth()));
        currentMonth.setText(LocalDateTime.now().getMonth().toString());
        homeViewModel.observerTasks(this, tasks -> {
            totalNumberOfTasks.setText(String.format(format, tasks.size()));
            TaskListAdapter adapter = new TaskListAdapter(tasks);
            adapter.setFinishedTaskCheckBoxListener(new TaskListViewHolder.FinishedTaskCheckBoxListener() {
                @Override
                public void onChange(Task task, boolean isDone) {
                    homeViewModel.updateTaskState(task, isDone);
                }
            });
            taskList.setAdapter(adapter);
            taskList.setLayoutManager(new LinearLayoutManager(this));
            taskList.setHasFixedSize(true);
        });
        homeViewModel.getAllTask();
    }

    private void obtainViewModel() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        MainApplication application = (MainApplication)getApplication();
        homeViewModel.setTaskRepository(application.taskRepository());
    }

    private void setClickListener() {
        findViewById(R.id.create_task)
                .setOnClickListener(view -> createCreateTaskActivity());
    }

    private void createCreateTaskActivity() {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        startActivity(intent);
    }
}