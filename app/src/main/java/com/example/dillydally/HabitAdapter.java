package com.example.dillydally;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {

    private final List<Habit> habitList;
    private final OnHabitListener onHabitListener;

    public HabitAdapter(List<Habit> habitList, OnHabitListener onHabitListener) {
        this.habitList = habitList;
        this.onHabitListener = onHabitListener;
    }

    @NonNull
    @Override
    public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_habit, parent, false);
        return new HabitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
        Habit habit = habitList.get(position);

        // 🔥 VERY IMPORTANT: Remove listener before setting checked state
        holder.habitCheckbox.setOnCheckedChangeListener(null);

        holder.habitCheckbox.setText(habit.getTitle());
        holder.habitCheckbox.setChecked(habit.isCompleted());

        // ✅ Attach listener AFTER binding
        holder.habitCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onHabitListener.onHabitCheck(adapterPosition, isChecked);
            }
        });

        holder.editHabitButton.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onHabitListener.onHabitEdit(adapterPosition);
            }
        });

        holder.deleteHabitButton.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onHabitListener.onHabitDelete(adapterPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }

    // ---------------- ViewHolder ----------------

    static class HabitViewHolder extends RecyclerView.ViewHolder {
        CheckBox habitCheckbox;
        ImageButton editHabitButton;
        ImageButton deleteHabitButton;

        public HabitViewHolder(@NonNull View itemView) {
            super(itemView);
            habitCheckbox = itemView.findViewById(R.id.habit_checkbox);
            editHabitButton = itemView.findViewById(R.id.edit_habit_button);
            deleteHabitButton = itemView.findViewById(R.id.delete_habit_button);
        }
    }

    // ---------------- Listener ----------------

    public interface OnHabitListener {
        void onHabitCheck(int position, boolean isChecked);
        void onHabitEdit(int position);
        void onHabitDelete(int position);
    }
}
