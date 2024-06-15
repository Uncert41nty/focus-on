package com.example.focus_on.notes;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.focus_on.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNoteActivity extends AppCompatActivity {
    TextView noteTitleTextView;
    TextView noteContentTextView;
    FloatingActionButton saveNoteButton;
    DatabaseReference db;
    DatabaseReference noteReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        db = FirebaseDatabase.getInstance().getReference();
        noteReference = db.child("notes");

        defineViews();
        mainFunction();
    }

    private void defineViews(){
        noteTitleTextView = findViewById(R.id.createNoteTitle);
        noteContentTextView = findViewById(R.id.createNoteContent);
        saveNoteButton = findViewById(R.id.saveNoteFloatButton);
    }

    private void mainFunction(){

    }
}