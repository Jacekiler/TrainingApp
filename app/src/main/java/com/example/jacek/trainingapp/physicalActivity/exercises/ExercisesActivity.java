package com.example.jacek.trainingapp.physicalActivity.exercises;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicActivity;
import com.example.jacek.trainingapp.common.Utils;

public class ExercisesActivity extends BasicActivity
{
    private RelativeLayout arms;
    private RelativeLayout legs;
    private RelativeLayout abs;
    private RelativeLayout back;
    private RelativeLayout other;
    private Intent intent;

    private String activityType;
    private String workoutName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);


            activityType = getIntent().getExtras().getString("type");
            workoutName = getIntent().getExtras().getString("workoutName");

        if(activityType.equals(Utils.ACTIVITY_TYPE_NORMAL))
        {
            setNavViewAndToolbar(R.id.exercises_layout);
        }
        else
        {
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeUI();
        arms.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(getApplicationContext(), SpecifiedExercisesActivity.class);
                intent.putExtra("category","Ręce");
                intent.putExtra("type", activityType);
                intent.putExtra("workoutName", workoutName);
                startActivityForResult(intent, 2);
            }
        });

        legs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(getApplicationContext(), SpecifiedExercisesActivity.class);
                intent.putExtra("category","Nogi");
                intent.putExtra("type", activityType);
                intent.putExtra("workoutName", workoutName);
                startActivityForResult(intent, 2);
            }
        });

        abs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(getApplicationContext(), SpecifiedExercisesActivity.class);
                intent.putExtra("category","Abs");
                intent.putExtra("type", activityType);
                intent.putExtra("workoutName", workoutName);
                startActivityForResult(intent, 2);
            }
        });

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(getApplicationContext(), SpecifiedExercisesActivity.class);
                intent.putExtra("category","Plecy");
                intent.putExtra("type", activityType);
                intent.putExtra("workoutName", workoutName);
                startActivityForResult(intent, 2);
            }
        });

        other.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(getApplicationContext(), SpecifiedExercisesActivity.class);
                intent.putExtra("category","Inne");
                intent.putExtra("type", activityType);
                intent.putExtra("workoutName", workoutName);
                startActivityForResult(intent, 2);
            }
        });



    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if(!activityType.equals(Utils.ACTIVITY_TYPE_NORMAL))
        {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
            {
                finish();
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        if(activityType.equals(Utils.ACTIVITY_TYPE_NORMAL))
        {
            super.onBackPressed();
        }
        else
        {
            finish();
        }
    }

    @Override
    public void initializeUI()
    {
        super.initializeUI();
        arms = findViewById(R.id.armsCategoryLayout);
        legs = findViewById(R.id.legsCategoryLayout);
        abs = findViewById(R.id.absCategoryLayout);
        back = findViewById(R.id.backCategoryLayout);
        other = findViewById(R.id.otherCategoryLayout);

    }
}
