package com.example.android_notes_app;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesRecycler extends RecyclerView.Adapter<NotesRecycler.NoteViewHolder> {

    List<Note> noteList = new ArrayList<>();

    public NotesRecycler(List<Note> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        NoteViewHolder noteViewHolder = new NoteViewHolder(v);

        noteViewHolder.txtDescription = v.findViewById(R.id.txtDescription);
        noteViewHolder.txtPriority = v.findViewById(R.id.txtPriority);
        noteViewHolder.txtTitle = v.findViewById(R.id.txtTitle);

        return  noteViewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        holder.txtTitle.setText(noteList.get(position).getTitle());
        holder.txtDescription.setText(noteList.get(position).getDescription());
        holder.txtPriority.setText(noteList.get(position).getPriority()+"");
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtPriority;
        TextView txtDescription;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
