package com.example.jacek.trainingapp.physicalActivity.workouts.interval;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicNoNavViewActivity;
import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.physicalActivity.PhysicalActivityData;
import com.example.jacek.trainingapp.physicalActivity.exercises.ExercisesActivity;
import com.example.jacek.trainingapp.interfaces.SpecifiedWorkoutsInterfaces;
import com.example.jacek.trainingapp.interfaces.itemTouchHelper.ItemTouchHelperCallback;
import com.example.jacek.trainingapp.timers.TimersActivity;

public class ChosenIntervalWorkoutActivity extends BasicNoNavViewActivity implements SpecifiedWorkoutsInterfaces
{
    private TextView nameTextView;
    private TextView deleteWorkout;
    private Button addExercise;
    private Button startWorkout;
    private TextView addWorkoutToTimetable;
    private RecyclerView recyclerView;
    private ChosenIntervalWorkoutAdapter adapter;
    private PhysicalActivityData physicalActivityData;
    private String name;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_interval_workout);

        setToolbar();

        initialize();
        physicalActivityData = new PhysicalActivityData(this);
        physicalActivityData.open();
        adapter = new ChosenIntervalWorkoutAdapter(this,physicalActivityData, name);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        addExercise.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(getApplicationContext(), ExercisesActivity.class);
                intent.putExtra("type","addingToIntervalWorkout");
                intent.putExtra("workoutName",name);
                startActivity(intent);
            }
        });

        startWorkout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(adapter.getItemCount()>0)
                {
                    String[] exercises = adapter.getExercisesNames();
                    Intent intent = new Intent(getApplicationContext(), TimersActivity.class);
                    intent.putExtra("type", "startingWorkout");
                    intent.putExtra("exercises", exercises);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Brak ćwiczeń w zestawie", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addWorkoutToTimetable.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utils.openAddToTimetable(ChosenIntervalWorkoutActivity.this);
            }
        });

        deleteWorkout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDeleteWorkoutDialog();
            }
        });

    }




    public void initialize()
    {
        nameTextView = findViewById(R.id.choosenIntervalWorkoutName);
        deleteWorkout = findViewById(R.id.deleteWorkout);
        addExercise = findViewById(R.id.addExerciseToIntervalWorkout);
        addWorkoutToTimetable = findViewById(R.id.addTrainingFromIntervalWorkout);
        startWorkout = findViewById(R.id.startIntervalWorkout);
        recyclerView = findViewById(R.id.choosenIntervalWorkoutRecycler);
        name = getIntent().getExtras().getString("workoutName");
        nameTextView.setText(name);

    }


    public void openDeleteWorkoutDialog()
    {
        DeleteWorkoutDialog deleteWorkoutDialog = new DeleteWorkoutDialog();
        Bundle args = new Bundle();
        args.putInt(Utils.POSITION,getIntent().getExtras().getInt(Utils.POSITION));
        deleteWorkoutDialog.setArguments(args);
        deleteWorkoutDialog.show(getSupportFragmentManager(),"delete interval workout dialog");
    }

    @Override
    public void onDeleteClick(int position)
    {
        adapter.delete(position);

    }

    @Override
    public void onAddTrainingToTimetableClick(String date)
    {
        physicalActivityData.addTraining(date, adapter.getWorkoutAsTraining());
    }

    @Override
    public void onDeleteWorkoutClick(int position)
    {
        IntervalWorkoutsAdapter adapter = new IntervalWorkoutsAdapter(this,physicalActivityData,"normal");
        adapter.delete(position);

        // robić tak jak niżej
//        physicalActivityData.delete(name)

        if(colorChanged)
        {
            setResult(RESULT_OK);
            colorChanged = false;
        }
        finish();
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
//        adapter.updatedMovedItems();
        super.onDestroy();
        physicalActivityData.close();
    }
}
