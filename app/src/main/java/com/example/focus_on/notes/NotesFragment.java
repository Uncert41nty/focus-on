package com.example.focus_on.notes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.focus_on.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotesFragment extends Fragment {
    View v;
    RecyclerView recyclerView;
    FloatingActionButton createNoteButton;
    DatabaseReference db;
    DatabaseReference noteReference;
    ArrayList<Note> notes;
    NotesAdapter adapter;
    Note note;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_notes, container, false);

        db = FirebaseDatabase.getInstance().getReference();
        noteReference = db.child("notes");

        notes = new ArrayList<>();
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(),2));

        readingFunction();
        createNoteFunction();

        return v;
    }
    private void createNoteFunction() {
        createNoteButton = v.findViewById(R.id.createNoteFloatButton);
        createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void readingFunction() {
        noteReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notes.clear();
                for (DataSnapshot noteSnapshot: snapshot.getChildren()) {
                    note = noteSnapshot.getValue(Note.class);
                    if (note != null) {
                        note.setId(noteSnapshot.getKey());
                        notes.add(note);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Failed to load notes", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void editFunction(Note note) {
        Intent intent = new Intent(getActivity(), CreateNoteActivity.class);
        intent.putExtra("noteId", note.getId());
        intent.putExtra("noteTitle", note.getNoteTitle());
        intent.putExtra("noteContent", note.getNoteText());
        startActivity(intent);
    }

}