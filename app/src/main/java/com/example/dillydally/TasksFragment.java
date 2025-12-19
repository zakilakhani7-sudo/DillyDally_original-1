
package com.example.dillydally;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment implements TaskAdapter.OnTaskListener {

    private List<Task> taskList = new ArrayList<>();
    private TaskAdapter taskAdapter;
    private TextView emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        RecyclerView recyclerTasks = view.findViewById(R.id.recyclerTasks);
        emptyView = view.findViewById(R.id.empty_view);
        recyclerTasks.setLayoutManager(new LinearLayoutManager(getContext()));
        taskAdapter = new TaskAdapter(taskList, this);
        recyclerTasks.setAdapter(taskAdapter);

        FloatingActionButton fabAdd = view.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            showTaskDialog(null, -1);
        });

        // Add some dummy data
        if (taskList.isEmpty()) {
            taskList.add(new Task("1", "Buy groceries"));
            taskList.add(new Task("2", "Walk the dog"));
        }

        updateEmptyView();

        return view;
    }

    private void showTaskDialog(@Nullable Task task, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(task == null ? "New Task" : "Edit Task");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_task, null);
        final TextInputEditText input = dialogView.findViewById(R.id.task_edit_text);
        input.setText(task == null ? "" : task.getTitle());
        builder.setView(dialogView);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String title = input.getText().toString();
            if (task == null) {
                // Add new task
                taskList.add(new Task(String.valueOf(System.currentTimeMillis()), title));
                taskAdapter.notifyItemInserted(taskList.size() - 1);
            } else {
                // Update existing task
                task.setTitle(title);
                taskAdapter.notifyItemChanged(position);
            }
            updateEmptyView();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void updateEmptyView() {
        if (taskList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onTaskCheck(int position, boolean isChecked) {
        if (position >= 0 && position < taskList.size()) {
            Task task = taskList.get(position);
            task.setCompleted(isChecked);
            taskAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onTaskEdit(int position) {
        if (position >= 0 && position < taskList.size()) {
            showTaskDialog(taskList.get(position), position);
        }
    }

    @Override
    public void onTaskDelete(int position) {
        if (position >= 0 && position < taskList.size()) {
            taskList.remove(position);
            taskAdapter.notifyItemRemoved(position);
            updateEmptyView();
        }
    }
}
