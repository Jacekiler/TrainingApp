package com.example.jacek.trainingapp.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.jacek.trainingapp.interfaces.NotepadInterfaces;
import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicActivity;

public class NotepadActivity extends BasicActivity implements NotepadInterfaces
{
    private RecyclerView recyclerView;
    private NotepadAdapter adapter;
    private NotesData notesData;
    private Button addNote;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);

        setNavViewAndToolbar(R.id.notepad_layout);

        initializeUI();

        notesData = new NotesData(this);
        notesData.open();

        adapter = new NotepadAdapter(this, notesData);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addNote.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), NoteDetailsActivity.class);
                intent.putExtra("type","new");
                startActivityForResult(intent,2);
//                openAddExerciseToWorkoutDialog();
            }
        });

    }

    @Override
    public void onDeleteClick(int position)
    {
        adapter.delete(position);
    }

    @Override
    public void initializeUI()
    {
        super.initializeUI();
        recyclerView = findViewById(R.id.notesRecyclerView);
        addNote = findViewById(R.id.addNoteButton);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        adapter.update();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        notesData.close();
    }

}
