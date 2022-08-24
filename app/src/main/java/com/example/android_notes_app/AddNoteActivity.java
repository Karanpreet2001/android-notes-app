package com.example.android_notes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.android_notes_app.databinding.ActivityAddNoteBinding;

public class AddNoteActivity extends AppCompatActivity {

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




    }
}