package com.thoughtworks.todo_list.ui.home;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.todo_list.R;
import com.thoughtworks.todo_list.repository.task.entity.Task;

public class TaskListViewHolder extends RecyclerView.ViewHolder {

    private TextView titleTextView;
    private TextView deadlineTextView;

    public TaskListViewHolder(@NonNull View itemView) {
        super(itemView);
        this.titleTextView = itemView.findViewById(R.id.task_title);
        this.deadlineTextView = itemView.findViewById(R.id.task_deadline);
    }

    public void setData(Task task) {
        titleTextView.setText(task.getTitle());
        deadlineTextView.setText(task.getDeadLine());
    }
}
