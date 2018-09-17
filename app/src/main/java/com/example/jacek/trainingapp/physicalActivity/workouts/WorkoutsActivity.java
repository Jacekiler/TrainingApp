package com.example.jacek.trainingapp.physicalActivity.workouts;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.interfaces.WorkoutsInterfaces;
import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicActivity;
import com.example.jacek.trainingapp.physicalActivity.PhysicalActivityData;
import com.example.jacek.trainingapp.physicalActivity.exercises.Exercise;
import com.example.jacek.trainingapp.physicalActivity.workouts.interval.IntervalWorkout;
import com.example.jacek.trainingapp.physicalActivity.workouts.interval.IntervalWorkoutsFragment;
import com.example.jacek.trainingapp.physicalActivity.workouts.simple.SimpleWorkout;
import com.example.jacek.trainingapp.physicalActivity.workouts.simple.SimpleWorkoutsFragment;

public class WorkoutsActivity extends BasicActivity implements WorkoutsInterfaces
{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private WorkoutFragmentPagerAdapter pagerAdapter;
    private String activityType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        activityType = getIntent().getExtras().getString("type");


        initializeUI();

        SimpleWorkoutsFragment s = new SimpleWorkoutsFragment();
        IntervalWorkoutsFragment i = new IntervalWorkoutsFragment();
        Bundle b = new Bundle();
        b.putString("type",activityType);

        if(activityType.equals(Utils.ACTIVITY_TYPE_NORMAL))
        {
            setNavViewAndToolbar(R.id.workouts_layout);


        }
        else
        {
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            b.putBundle("exerciseBundle", getIntent().getExtras().getBundle("exerciseBundle"));



        }
        s.setArguments(b);
        i.setArguments(b);
        pagerAdapter.addFragment(s,"Zwykły");
        pagerAdapter.addFragment(i,"Interwały");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
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
    public void initializeUI()
    {
        super.initializeUI();
        viewPager = findViewById(R.id.workoutChooseViewPager);
        tabLayout = findViewById(R.id.workoutsTab);
        pagerAdapter = new WorkoutFragmentPagerAdapter(getSupportFragmentManager());

    }

    @Override
    public void onAddWorkoutClick(String workout)
    {
        try
        {
            if (viewPager.getCurrentItem() == 0)
            {
                SimpleWorkoutsFragment fragment = (SimpleWorkoutsFragment) pagerAdapter.getItem(0);
                fragment.addWorkout(new SimpleWorkout(workout));
            } else
            {
                IntervalWorkoutsFragment fragment = (IntervalWorkoutsFragment) pagerAdapter.getItem(1);
                fragment.addWorkout(new IntervalWorkout(workout));
            }
        }
        catch (Exception e)
        {

        }
    }




    @Override
    public void onDeleteClick(int position)
    {

        if (viewPager.getCurrentItem() == 0)
        {
            SimpleWorkoutsFragment fragment = (SimpleWorkoutsFragment) pagerAdapter.getItem(0);
            fragment.deleteWorkout(position);
        }
        else
        {
            IntervalWorkoutsFragment fragment = (IntervalWorkoutsFragment)pagerAdapter.getItem(1);
            fragment.deleteWorkout(position);
        }
    }

    @Override
    public void onAddExerciseToWorkoutClick(String workout, Exercise exercise)
    {
        PhysicalActivityData physicalActivityData = new PhysicalActivityData(this);
        physicalActivityData.open();
        if (viewPager.getCurrentItem() == 0)
        {
            physicalActivityData.addExerciseToSimpleWorkout(workout, exercise);
            ((SimpleWorkoutsFragment)pagerAdapter.getItem(0)).setAdapter();
        }
        else
        {
            physicalActivityData.addExerciseToIntervalWorkout(workout, exercise);
            ((IntervalWorkoutsFragment)pagerAdapter.getItem(1)).setAdapter();
        }
        physicalActivityData.close();
    }

    public static void openAddExerciseToWorkoutDialog(Context context,String workout, Bundle exerciseBundle)
    {
        AddExerciseToWorkoutDialog addExerciseToWorkoutDialog = new AddExerciseToWorkoutDialog();
        Bundle args = new Bundle();
        args.putString("workout", workout);
        args.putBundle("exerciseBundle",exerciseBundle);
        addExerciseToWorkoutDialog.setArguments(args);
        addExerciseToWorkoutDialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"add exercise to workout dialog");
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


}
