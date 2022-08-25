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
    private OnItemClickListener listener;



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

    public Note getNoteAt(int position){
        return noteList.get(position);
    }

    public void setNoteList(List<Note> list){
        this.noteList = list;
        notifyDataSetChanged();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtPriority;
        TextView txtDescription;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener !=null && getAdapterPosition() != RecyclerView.NO_POSITION){
                        listener.onItemClick(noteList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
