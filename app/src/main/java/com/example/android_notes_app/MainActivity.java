package com.example.android_notes_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.android_notes_app.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton button = binding.floatingAddButton;


        NotesRecycler notesRecycler = new NotesRecycler();
        recyclerView.setAdapter(notesRecycler);

        noteViewModel =  new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {

                notesRecycler.setNoteList(notes);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(i, 1);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(notesRecycler.getNoteAt(viewHolder.getAdapterPosition()));

                Snackbar.make(findViewById(R.id.main), "Note deleted", Snackbar.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        
        notesRecycler.setOnItemClickListener(new NotesRecycler.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent=new Intent(MainActivity.this, AddNoteActivity.class);
                intent.putExtra(AddNoteActivity.EXTRA_ID,note.getId());
                intent.putExtra(AddNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddNoteActivity.EXTRA_PRIORITY, note.getPriority());
                intent.putExtra(AddNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                startActivityForResult(intent, 2);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode == RESULT_OK ){
            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1 );

            Note note = new Note(title, description, priority);
            noteViewModel.insert(note);

            Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "Note saved", Snackbar.LENGTH_SHORT);
            snackbar.show();


        }else  if(requestCode==2 && resultCode == RESULT_OK ){

            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1 );
            int id = data.getIntExtra(AddNoteActivity.EXTRA_ID, -1);
            if( id == -1){
                Snackbar.make(findViewById(R.id.main), "Note can't be updated", Snackbar.LENGTH_SHORT).show();
                        return;
            }
            Note note = new Note(title, description, priority);
            note.setId(id);
            noteViewModel.update(note);

         Snackbar.make(findViewById(R.id.main), "Note updated", Snackbar.LENGTH_SHORT).show();
        }else{
          Snackbar.make(findViewById(R.id.main), "Note not saved", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteAll:
                noteViewModel.deleteAll();
                Snackbar.make(findViewById(R.id.main), "All notes deleted", Snackbar.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}