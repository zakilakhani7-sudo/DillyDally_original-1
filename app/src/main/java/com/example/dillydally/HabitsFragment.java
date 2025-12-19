
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

public class HabitsFragment extends Fragment implements HabitAdapter.OnHabitListener {

    private List<Habit> habitList = new ArrayList<>();
    private HabitAdapter habitAdapter;
    private TextView emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habits, container, false);

        RecyclerView recyclerHabits = view.findViewById(R.id.recyclerHabits);
        emptyView = view.findViewById(R.id.empty_view);
        recyclerHabits.setLayoutManager(new LinearLayoutManager(getContext()));
        habitAdapter = new HabitAdapter(habitList, this);
        recyclerHabits.setAdapter(habitAdapter);

        FloatingActionButton fabAdd = view.findViewById(R.id.fabAddHabit);
        fabAdd.setOnClickListener(v -> {
            showHabitDialog(null, -1);
        });

        // Add some dummy data
        if (habitList.isEmpty()) {
            habitList.add(new Habit("1", "Drink Water"));
            habitList.add(new Habit("2", "Exercise"));
        }

        updateEmptyView();

        return view;
    }

    private void showHabitDialog(@Nullable Habit habit, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(habit == null ? "New Habit" : "Edit Habit");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_habit, null);
        final TextInputEditText input = dialogView.findViewById(R.id.habit_edit_text);
        input.setText(habit == null ? "" : habit.getTitle());
        builder.setView(dialogView);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String title = input.getText().toString();
            if (habit == null) {
                // Add new habit
                habitList.add(new Habit(String.valueOf(System.currentTimeMillis()), title));
                habitAdapter.notifyItemInserted(habitList.size() - 1);
            } else {
                // Update existing habit
                habit.setTitle(title);
                habitAdapter.notifyItemChanged(position);
            }
            updateEmptyView();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void updateEmptyView() {
        if (habitList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onHabitCheck(int position, boolean isChecked) {
        if (position >= 0 && position < habitList.size()) {
            Habit habit = habitList.get(position);
            habit.setCompleted(isChecked);
            habitAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onHabitEdit(int position) {
        if (position >= 0 && position < habitList.size()) {
            showHabitDialog(habitList.get(position), position);
        }
    }

    @Override
    public void onHabitDelete(int position) {
        if (position >= 0 && position < habitList.size()) {
            habitList.remove(position);
            habitAdapter.notifyItemRemoved(position);
            updateEmptyView();
        }
    }
}
