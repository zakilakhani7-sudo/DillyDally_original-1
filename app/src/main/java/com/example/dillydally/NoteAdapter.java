
package com.example.dillydally;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> noteList;
    private final OnNoteListener onNoteListener;

    public NoteAdapter(List<Note> noteList, OnNoteListener onNoteListener) {
        this.noteList = noteList;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.noteContent.setText(note.getContent());

        holder.editNoteButton.setOnClickListener(v -> {
            onNoteListener.onNoteEdit(position);
        });

        holder.deleteNoteButton.setOnClickListener(v -> {
            onNoteListener.onNoteDelete(position);
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView noteTitle;
        TextView noteContent;
        ImageButton editNoteButton;
        ImageButton deleteNoteButton;

        public NoteViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_title);
            noteContent = itemView.findViewById(R.id.note_content);
            editNoteButton = itemView.findViewById(R.id.edit_note_button);
            deleteNoteButton = itemView.findViewById(R.id.delete_note_button);
        }
    }

    public interface OnNoteListener {
        void onNoteEdit(int position);
        void onNoteDelete(int position);
    }
}
