package com.thoughtworks.todo_list.ui.create_task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.thoughtworks.todo_list.MainApplication;
import com.thoughtworks.todo_list.R;
import com.thoughtworks.todo_list.repository.task.entity.Task;
import com.thoughtworks.todo_list.ui.home.HomeActivity;
import com.thoughtworks.todo_list.util.StringUtils;

public class CreateTaskActivity extends AppCompatActivity {

    private TextView deadLineTextView;
    private CreateTaskViewModel createTaskViewModel;
    private EditText titleEditText;
    private Button confirmButton;
    private EditText descriptionEditText;
    private Switch isRemindSwitch;
    private Toolbar toolbar;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        loadView();
        setListener();
        obtainViewModel();
        init();
    }

    private void loadView() {
        deadLineTextView = findViewById(R.id.set_date);
        confirmButton = findViewById(R.id.confirm_button);
        titleEditText = findViewById(R.id.title_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_text);
        isRemindSwitch = findViewById(R.id.is_remind);
        toolbar = findViewById(R.id.create_task_tool_bar);
        deleteButton = findViewById(R.id.delete_button);
    }

    private void init() {
        initActionBar();
        confirmButton.setEnabled(false);
        createTaskViewModel.observerTask(this, task -> {
            confirmButton.setEnabled(StringUtils.isNotBlank(task.getDeadLine()) && StringUtils.isNotBlank(task.getTitle()));
            if (task.getDeadLine() != null) {
                deadLineTextView.setText(task.getDeadLine());
                deadLineTextView.setTextColor(getColor(R.color.basic_color));
            }
        });

        createTaskViewModel.observerTaskCreateResult(this, taskEditResult -> {
            Toast.makeText(this, taskEditResult.name(), Toast.LENGTH_SHORT).show();
            if (taskEditResult.equals(TaskEditResult.SAVE_SUCCESS) ||
                    taskEditResult.equals(TaskEditResult.DELETE_SUCCESS)) {
                backToHome();
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Task oldTask = (Task) extras.get("oldTask");
            if (oldTask != null) {
                showEditPage(oldTask);
            }
        }
    }

    private void initActionBar() {
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
    }

    private void showEditPage(Task oldTask) {
        createTaskViewModel.setTask(oldTask);
        titleEditText.getText().append(oldTask.getTitle());
        descriptionEditText.getText().append(oldTask.getDescription());
        isRemindSwitch.setChecked(oldTask.isRemind());
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTaskViewModel.updateTask();
            }
        });
        deleteButton.setVisibility(View.VISIBLE);
    }

    private void backToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void obtainViewModel() {
        createTaskViewModel = new ViewModelProvider(this).get(CreateTaskViewModel.class);
        MainApplication application = (MainApplication) getApplication();
        createTaskViewModel.setTaskRepository(application.taskRepository());
    }

    private void setListener() {
        deadLineTextView.setOnClickListener(view -> showCalendarView());

        titleEditText.setOnFocusChangeListener((view, b) ->
                createTaskViewModel.setTitle(titleEditText.getText().toString()));

        descriptionEditText.setOnFocusChangeListener((view, b) ->
                createTaskViewModel.setDescription(descriptionEditText.getText().toString()));

        isRemindSwitch.setOnCheckedChangeListener((compoundButton, b) ->
                createTaskViewModel.setRemind(b));

        confirmButton.setOnClickListener(view ->
                createTaskViewModel.saveTask());

        deleteButton.setOnClickListener(view -> createTaskViewModel.deleteTask());
    }

    private void showCalendarView() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.calendar_view_container, new CalendarViewFragment(createTaskViewModel));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}