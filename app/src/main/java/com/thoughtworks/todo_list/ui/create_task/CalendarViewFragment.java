package com.thoughtworks.todo_list.ui.create_task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thoughtworks.todo_list.R;

public class CalendarViewFragment extends Fragment {

    private CalendarView calendarView;
    private CreateTaskViewModel createTaskViewModel;

    public CalendarViewFragment() {

    }

    public CalendarViewFragment(CreateTaskViewModel createTaskViewModel) {
        this();
        this.createTaskViewModel = createTaskViewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        calendarView = getView().findViewById(R.id.calendar_view);
        calendarView.setMinDate(System.currentTimeMillis() - 1000);
        calendarView.setOnDateChangeListener((calendarView, i, i1, i2) -> {
            createTaskViewModel.setDate(String.format("%s年%s月%s日", i, i1, i2));
            getActivity().getSupportFragmentManager().popBackStack();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar_view, container, false);
    }
}