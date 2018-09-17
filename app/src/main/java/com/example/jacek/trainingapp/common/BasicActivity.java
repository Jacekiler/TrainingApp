package com.example.jacek.trainingapp.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.calculators.CalculatorsActivity;
import com.example.jacek.trainingapp.notepad.NotepadActivity;
import com.example.jacek.trainingapp.physicalActivity.exercises.ExercisesActivity;
import com.example.jacek.trainingapp.physicalActivity.timetable.TimetableActivity;
import com.example.jacek.trainingapp.physicalActivity.workouts.WorkoutsActivity;
import com.example.jacek.trainingapp.productsAndCalories.calories.CaloriesActivity;
import com.example.jacek.trainingapp.productsAndCalories.dailyCalories.DailyCaloriesActivity;
import com.example.jacek.trainingapp.settings.SettingsActivity;
import com.example.jacek.trainingapp.stopwatch.StopwatchActivity;
import com.example.jacek.trainingapp.timers.TimersActivity;

/**
 * Created by Jacek on 01.10.2017.
 */

public abstract class BasicActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    protected Toolbar toolbar;
    protected DrawerLayout drawer;
    protected boolean secondDoubleBackPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Utils.setAppColor(this);
        super.onCreate(savedInstanceState);
        Utils.setActivityOrientation(this);
    }

    public void initializeUI()
    {}

    public void setNavViewAndToolbar(int layoutId)
    {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(layoutId);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                super.onDrawerSlide(drawerView, slideOffset);
                Utils.hideKeyboard(getApplicationContext(),drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.settings:
                Intent settings_intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(settings_intent,1);
                return true;
            case R.id.contact:
                Intent author_intent = new Intent(this, AuthorContactActivity.class);
                startActivity(author_intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.nav_timers:
            {
                Intent timers_intent = new Intent(this,TimersActivity.class);
                timers_intent.putExtra("type", Utils.ACTIVITY_TYPE_NORMAL);
                startActivity(timers_intent);
                finish();
                closeDrawer();
                return true;
            }
            case R.id.nav_stopwatch:
            {
                Intent stopwatch_intent = new Intent(this, StopwatchActivity.class);
                startActivity(stopwatch_intent);
                finish();
                closeDrawer();
                return true;
            }
            case R.id.nav_calculators:
            {
                Intent calculators_intent = new Intent(this,CalculatorsActivity.class);
                startActivity(calculators_intent);
                finish();
                closeDrawer();
                return true;
            }
            case R.id.nav_calories:
            {
                Intent calories_intent = new Intent(this,CaloriesActivity.class);
                calories_intent.putExtra("type", Utils.ACTIVITY_TYPE_NORMAL);
                startActivity(calories_intent);
                finish();
                closeDrawer();
                return true;
            }
            case R.id.nav_exercises:
            {
                Intent exercises_intent = new Intent(this,ExercisesActivity.class);
                exercises_intent.putExtra("type", Utils.ACTIVITY_TYPE_NORMAL);
                startActivity(exercises_intent);
                finish();
                closeDrawer();
                return true;
            }
            case R.id.nav_timetable:
            {
                Intent timetable_intent = new Intent(this,TimetableActivity.class);
                startActivity(timetable_intent);
                finish();
                closeDrawer();
                return true;
            }
            case R.id.nav_daily_calories:
            {
                Intent daily_calories_intent = new Intent(this,DailyCaloriesActivity.class);
                startActivity(daily_calories_intent);
                finish();
                closeDrawer();
                return true;
            }
            case R.id.nav_notepad:
            {
                Intent notepad_intent = new Intent(this,NotepadActivity.class);
                startActivity(notepad_intent);
                finish();
                closeDrawer();
                return true;
            }
            case R.id.nav_workouts:
            {
                Intent workouts_intent = new Intent(this,WorkoutsActivity.class);
                workouts_intent.putExtra("type", Utils.ACTIVITY_TYPE_NORMAL);
                startActivity(workouts_intent);
                finish();
                closeDrawer();
                return true;
            }
            default:
                closeDrawer();
                return true;
        }
//        closeDrawer();
//        return true;
    }

    public void closeDrawer()
    {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                recreate();
            }
        }
        else if(requestCode == 2)
        {
            if(resultCode == RESULT_OK)
            {
                recreate();
            }
        }
    }

    @Override
    public void onBackPressed()
    {

        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            if(secondDoubleBackPressed)
            {
                super.onBackPressed();
            }

            secondDoubleBackPressed = true;
            Toast.makeText(this, "Naciśnij ponownie aby wyjść", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    secondDoubleBackPressed = false;
                }
            },2000);

        }
    }

}
