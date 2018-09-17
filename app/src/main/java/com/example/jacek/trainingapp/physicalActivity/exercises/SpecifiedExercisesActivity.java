package com.example.jacek.trainingapp.physicalActivity.exercises;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.jacek.trainingapp.interfaces.SpecifiedExercisesInterfaces;
import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicNoNavViewActivity;
import com.example.jacek.trainingapp.physicalActivity.PhysicalActivityData;

public class SpecifiedExercisesActivity extends BasicNoNavViewActivity implements SpecifiedExercisesInterfaces
{
    private RecyclerView recyclerView;
    private Button addExercise;
    private SpecifiedExercisesAdapter adapter;
    private PhysicalActivityData physicalActivityData;
    private String category;
    private String type;
    private String workoutName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specified_exercises_rv);

        category = getIntent().getExtras().getString("category");
        type = getIntent().getExtras().getString("type");
        workoutName = getIntent().getExtras().getString("workoutName");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addExercise = findViewById(R.id.addExerciseButtonRV);
        recyclerView = findViewById(R.id.exercisesRecyclerView);

        physicalActivityData = new PhysicalActivityData(this);
        physicalActivityData.open();


        toolbar.setTitle(category);
        adapter = new SpecifiedExercisesAdapter(this, physicalActivityData, category, type, workoutName);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addExercise.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openAddDialog();

            }
        });


    }

    public void openAddDialog()
    {
        AddExerciseDialog addExerciseDialog = new AddExerciseDialog();
        Bundle args = new Bundle();
        args.putString("category",category);
        addExerciseDialog.setArguments(args);
        addExerciseDialog.show(getSupportFragmentManager(), "add exercise dialog");
    }

    @Override
    public void onAddExerciseClick(Exercise exercise)
    {
        adapter.add(exercise);
    }

    @Override
    public void onDeleteClick(int position)
    {
        adapter.delete(position);
    }

    @Override
    public void onUpdateExerciseClick(int position, Exercise exercise)
    {
        adapter.edit(position, exercise);
    }

    @Override
    public void onAddExerciseToWorkoutClick(int position, String type)
    {
        if(type.equals("addingToIntervalWorkout"))
        {
            physicalActivityData.addExerciseToIntervalWorkout(workoutName, adapter.getItemAt(position));
        }
        else
        {
            physicalActivityData.addExerciseToSimpleWorkout(workoutName, adapter.getItemAt(position));
        }


    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        physicalActivityData.close();
    }


}
