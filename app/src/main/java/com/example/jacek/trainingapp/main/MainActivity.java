package com.example.jacek.trainingapp.main;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.example.jacek.trainingapp.calculators.CalculatorsActivity;
import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.notepad.NotepadActivity;
import com.example.jacek.trainingapp.physicalActivity.exercises.ExercisesActivity;
import com.example.jacek.trainingapp.physicalActivity.timetable.TimetableActivity;
import com.example.jacek.trainingapp.physicalActivity.workouts.WorkoutsActivity;
import com.example.jacek.trainingapp.productsAndCalories.calories.CaloriesActivity;
import com.example.jacek.trainingapp.productsAndCalories.dailyCalories.DailyCaloriesActivity;
import com.example.jacek.trainingapp.stopwatch.StopwatchActivity;
import com.example.jacek.trainingapp.timers.TimersActivity;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        String startActivity = PreferenceManager.getDefaultSharedPreferences(this).getString("startActivity","Stopwatch");
        Intent intent;
        switch (startActivity)
        {
            case Utils.STOPWATCH_ACTIVITY_NAME:
            {
                intent = new Intent(this, StopwatchActivity.class);
                break;
            }
            case Utils.TIMERS_ACTIVITY_NAME:
            {
                intent = new Intent(this,TimersActivity.class);
                intent.putExtra("type","normal");
                break;
            }
            case Utils.CALCULATORS_ACTIVITY_NAME:
            {
                intent = new Intent(this,CalculatorsActivity.class);
                break;
            }
            case Utils.CALORIES_ACTIVITY_NAME:
            {
                intent = new Intent(this,CaloriesActivity.class);
                intent.putExtra("type","normal");
                break;
            }
            case Utils.DAILY_CALORIES_ACTIVITY_NAME:
            {
                intent = new Intent(this,DailyCaloriesActivity.class);
                break;
            }
            case Utils.EXERCISES_ACTIVITY_NAME:
            {
                intent = new Intent(this,ExercisesActivity.class);
                intent.putExtra("type","normal");
                break;
            }
            case Utils.WORKOUTS_ACTIVITY_NAME:
            {
                intent = new Intent(this,WorkoutsActivity.class);
                intent.putExtra("type","normal");
                break;
            }
            case Utils.TIMETABLE_ACTIVITY_NAME:
            {
                intent = new Intent(this,TimetableActivity.class);
                break;
            }
            case Utils.NOTEPAD_ACTIVITY_NAME:
            {
                intent = new Intent(this,NotepadActivity.class);
                break;
            }
            default:
            {
                intent = new Intent(this, StopwatchActivity.class);
                break;
            }

        }

        startActivity(intent);
        finish();
    }


}
