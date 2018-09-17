package com.example.jacek.trainingapp.physicalActivity.workouts.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicNoNavViewActivity;
import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.interfaces.itemTouchHelper.ItemTouchHelperCallback;
import com.example.jacek.trainingapp.physicalActivity.PhysicalActivityData;
import com.example.jacek.trainingapp.physicalActivity.exercises.ExercisesActivity;
import com.example.jacek.trainingapp.interfaces.SpecifiedWorkoutsInterfaces;
import com.example.jacek.trainingapp.physicalActivity.workouts.interval.DeleteWorkoutDialog;

public class ChosenSimpleWorkoutActivity extends BasicNoNavViewActivity implements SpecifiedWorkoutsInterfaces
{
    private TextView nameTextView;
    private TextView deleteWorkout;
    private Button addExercise;
    private TextView addWorkoutToTimetable;
    private RecyclerView recyclerView;
    private ChosenSimpleWorkoutAdapter adapter;
    private PhysicalActivityData physicalActivityData;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_simple_workout);

        setToolbar();

        initialize();
        physicalActivityData = new PhysicalActivityData(this);
        physicalActivityData.open();

        adapter = new ChosenSimpleWorkoutAdapter(this,physicalActivityData, name);

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
                intent.putExtra("type","addingToSimpleWorkout");
                intent.putExtra("workoutName",name);
                startActivity(intent);
            }
        });



        deleteWorkout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openDeleteSimpleWorkoutDialog();
            }
        });

        addWorkoutToTimetable.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utils.openAddToTimetable(ChosenSimpleWorkoutActivity.this);
            }
        });



    }



    public void openDeleteSimpleWorkoutDialog()
    {
        DeleteWorkoutDialog deleteWorkoutDialog = new DeleteWorkoutDialog();
        Bundle args = new Bundle();
        args.putInt("position",getIntent().getExtras().getInt("position"));
        deleteWorkoutDialog.setArguments(args);
        deleteWorkoutDialog.show(getSupportFragmentManager(),"delete interval workout dialog");

    }

    @Override
    public void onAddTrainingToTimetableClick(String date)
    {
        physicalActivityData.addTraining(date, adapter.getWorkoutAsTraining());
    }

    @Override
    public void onDeleteClick(int position)
    {
        adapter.delete(position);
    }


    @Override
    public void onDeleteWorkoutClick(int position)
    {
        SimpleWorkoutsAdapter adapter = new SimpleWorkoutsAdapter(this,physicalActivityData, "normal");
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



    public void initialize()
    {
        nameTextView = findViewById(R.id.chosenSimpleWorkoutName);
        deleteWorkout = findViewById(R.id.deleteSimpleWorkout);
        addExercise = findViewById(R.id.addExerciseToSimpleWorkout);
        addWorkoutToTimetable = findViewById(R.id.addTrainingFromSimpleWorkout);
        recyclerView = findViewById(R.id.chosenSimpleWorkoutRecycler);
        name = getIntent().getExtras().getString("workoutName");
        nameTextView.setText(name);
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
        physicalActivityData.close();
    }
}