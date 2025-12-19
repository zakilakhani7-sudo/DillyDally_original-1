
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

public class NotesFragment extends Fragment implements NoteAdapter.OnNoteListener {

    private List<Note> noteList = new ArrayList<>();
    private NoteAdapter noteAdapter;
    private TextView emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        RecyclerView recyclerNotes = view.findViewById(R.id.recyclerNotes);
        emptyView = view.findViewById(R.id.empty_view);
        recyclerNotes.setLayoutManager(new LinearLayoutManager(getContext()));
        noteAdapter = new NoteAdapter(noteList, this);
        recyclerNotes.setAdapter(noteAdapter);

        FloatingActionButton fabAdd = view.findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            showNoteDialog(null, -1);
        });

        // Add some dummy data
        if (noteList.isEmpty()) {
            noteList.add(new Note("1", "Meeting Notes", "- Discuss Q3 goals"));
            noteList.add(new Note("2", "Shopping List", "- Milk\n- Bread"));
        }

        updateEmptyView();

        return view;
    }

    private void showNoteDialog(@Nullable Note note, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(note == null ? "New Note" : "Edit Note");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_note, null);
        final TextInputEditText titleInput = dialogView.findViewById(R.id.titleEditText);
        final TextInputEditText contentInput = dialogView.findViewById(R.id.contentEditText);

        if (note != null) {
            titleInput.setText(note.getTitle());
            contentInput.setText(note.getContent());
        }

        builder.setView(dialogView);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String title = titleInput.getText().toString();
            String content = contentInput.getText().toString();
            if (note == null) {
                // Add new note
                noteList.add(new Note(String.valueOf(System.currentTimeMillis()), title, content));
                noteAdapter.notifyItemInserted(noteList.size() - 1);
            } else {
                // Update existing note
                note.setTitle(title);
                note.setContent(content);
                noteAdapter.notifyItemChanged(position);
            }
            updateEmptyView();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void updateEmptyView() {
        if (noteList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNoteEdit(int position) {
        if (position >= 0 && position < noteList.size()) {
            showNoteDialog(noteList.get(position), position);
        }
    }

    @Override
    public void onNoteDelete(int position) {
        if (position >= 0 && position < noteList.size()) {
            noteList.remove(position);
            noteAdapter.notifyItemRemoved(position);
            updateEmptyView();
        }
    }
}
