package com.example.dillydally;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<Task> taskList;
    private final OnTaskListener onTaskListener;

    public TaskAdapter(List<Task> taskList, OnTaskListener onTaskListener) {
        this.taskList = taskList;
        this.onTaskListener = onTaskListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        // 🔥 VERY IMPORTANT: remove listener before changing checked state
        holder.taskCheckbox.setOnCheckedChangeListener(null);

        holder.taskCheckbox.setText(task.getTitle());
        holder.taskCheckbox.setChecked(task.isCompleted());

        // ✅ attach listener AFTER binding
        holder.taskCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onTaskListener.onTaskCheck(adapterPosition, isChecked);
            }
        });

        holder.editTaskButton.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onTaskListener.onTaskEdit(adapterPosition);
            }
        });

        holder.deleteTaskButton.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onTaskListener.onTaskDelete(adapterPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // ---------------- ViewHolder ----------------

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        CheckBox taskCheckbox;
        ImageButton editTaskButton;
        ImageButton deleteTaskButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskCheckbox = itemView.findViewById(R.id.task_checkbox);
            editTaskButton = itemView.findViewById(R.id.edit_task_button);
            deleteTaskButton = itemView.findViewById(R.id.delete_task_button);
        }
    }

    // ---------------- Listener ----------------

    public interface OnTaskListener {
        void onTaskCheck(int position, boolean isChecked);
        void onTaskEdit(int position);
        void onTaskDelete(int position);
    }
}
