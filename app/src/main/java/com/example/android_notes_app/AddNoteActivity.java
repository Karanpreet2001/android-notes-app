package com.example.android_notes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.android_notes_app.databinding.ActivityAddNoteBinding;
import com.google.android.material.snackbar.Snackbar;

public class AddNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID="com.example.android_notes_app.EXTRA_ID";

    public static final String EXTRA_TITLE="com.example.android_notes_app.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION="com.example.android_notes_app.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY="com.example.android_notes_app.EXTRA_PRIORITY";

    EditText editNoteTitle, editNoteDescription;
    NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAddNoteBinding binding= ActivityAddNoteBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        editNoteTitle = binding.editTitle;
        editNoteDescription = binding.editDescription;
        numberPickerPriority = binding.noteNumberPicker;

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");
            editNoteTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editNoteDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
        }else{
            setTitle("Add Note");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void  saveNote(){
        String title= editNoteTitle.getText().toString();
        String description= editNoteDescription.getText().toString();
        int priority= numberPickerPriority.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Snackbar.make(findViewById(R.id.add_note), "Please insert title and description",Snackbar.LENGTH_SHORT ).show();

            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);


        int id = getIntent().getIntExtra(EXTRA_ID, -1);

        if(id!=-1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();


    }
}