package com.example.todolistapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {
    private EditText editTextTask;
    private Button buttonAddTask;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private ArrayList<Task> taskList;
    private int editPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTask = findViewById(R.id.editTextTask);
        buttonAddTask = findViewById(R.id.buttonAddTask);
        recyclerView = findViewById(R.id.recyclerView);

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        buttonAddTask.setOnClickListener(v -> {
            String taskText = editTextTask.getText().toString();
            if (!TextUtils.isEmpty(taskText)) {
                if (editPosition == -1) {
                    taskList.add(new Task(taskText));
                    taskAdapter.notifyItemInserted(taskList.size() - 1);
                } else {
                    taskList.get(editPosition).setTask(taskText);
                    taskAdapter.notifyItemChanged(editPosition);
                    editPosition = -1;
                }
                editTextTask.setText("");
            }
        });
    }

    @Override
    public void onTaskEdit(int position) {
        editTextTask.setText(taskList.get(position).getTask());
        editPosition = position;
    }

    @Override
    public void onTaskDelete(int position) {
        taskList.remove(position);
        taskAdapter.notifyItemRemoved(position);
    }
}
