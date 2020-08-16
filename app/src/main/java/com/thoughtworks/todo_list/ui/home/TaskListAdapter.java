package com.thoughtworks.todo_list.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thoughtworks.todo_list.R;
import com.thoughtworks.todo_list.repository.task.entity.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListViewHolder>{

    private List<Task> tasks;

    public void setFinishedTaskCheckBoxListener(TaskListViewHolder.FinishedTaskCheckBoxListener finishedTaskCheckBoxListener) {
        this.finishedTaskCheckBoxListener = finishedTaskCheckBoxListener;
    }

    private TaskListViewHolder.FinishedTaskCheckBoxListener finishedTaskCheckBoxListener;

    public void setTasksItemClickListener(TasksItemClickListener tasksItemClickListener) {
        this.tasksItemClickListener = tasksItemClickListener;
    }

    private TasksItemClickListener tasksItemClickListener;

    public TaskListAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
        return new TaskListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
        Task task = tasks.get(position);
        if (task != null) {
            holder.setData(task);
            holder.setCheckBoxChangeListener(finishedTaskCheckBoxListener);
            holder.itemView.setOnClickListener(view -> tasksItemClickListener.onClickTasksItem(task));
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public interface TasksItemClickListener{
        void onClickTasksItem(Task task);
    }
}
