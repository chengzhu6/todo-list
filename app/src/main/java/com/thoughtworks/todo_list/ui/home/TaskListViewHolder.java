package com.thoughtworks.todo_list.ui.home;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.todo_list.R;
import com.thoughtworks.todo_list.repository.task.entity.Task;

public class TaskListViewHolder extends RecyclerView.ViewHolder {

    private static final String FINISHED_TASK_TITLE_FONT_COLOR = "#F2EEEE";

    private TextView titleTextView;
    private TextView deadlineTextView;
    private CheckBox finishedTaskCheckBox;
    private Task data;

    public interface FinishedTaskCheckBoxListener{
        void onChange(Task task, boolean isDone);
    }

    public TaskListViewHolder(@NonNull View itemView) {
        super(itemView);
        this.titleTextView = itemView.findViewById(R.id.task_title);
        this.deadlineTextView = itemView.findViewById(R.id.task_deadline);
        this.finishedTaskCheckBox = itemView.findViewById(R.id.finished_task);
    }

    public void setData(Task task) {
        this.data = task;
        if (task.isDone()) {
            titleTextView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            titleTextView.setTextColor(Color.parseColor(FINISHED_TASK_TITLE_FONT_COLOR));
        }
        titleTextView.setText(task.getTitle());
        deadlineTextView.setText(task.getDeadLine());
        finishedTaskCheckBox.setChecked(task.isDone());
    }

    public void setCheckBoxChangeListener(FinishedTaskCheckBoxListener listener) {
        this.finishedTaskCheckBox.setOnCheckedChangeListener((compoundButton, b) -> listener.onChange(data, b));
    }
}
