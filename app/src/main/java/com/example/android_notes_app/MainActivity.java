package com.example.android_notes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Snackbar snackbar = Snackbar.make(findViewById(R.id.main), "This a bar", Snackbar.LENGTH_SHORT);
        snackbar.show();


    }
}