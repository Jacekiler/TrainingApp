package com.example.jacek.trainingapp.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.physicalActivity.workouts.interval.AddWorkoutToTimetableDialog;

import java.text.SimpleDateFormat;


public class Utils
{
    /*
    Flags used to recognize how activity shoud behave
     */
    public static final String ACTIVITY_TYPE_NORMAL = "normal";
    public static final String ACTIVITY_TYPE_EDIT = "edit";


    /*
    SharedPreferences or Intent extras names
     */
    public static final String VIBRATIONS_ON = "vibrationsOn";
    public static final String POSITION = "position";
    public static final String APP_COLOR = "appColor";

    /*
    Date formats
     */
    public static final SimpleDateFormat shortFormatDate = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat longFormatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    public static final long DAY_IN_MILLIS = 86400000;

    /*
    Default interval timer values used in SahredPreferences
     */
    public static final int DEFAULT_PREPARE_VALUE = 10;
    public static final int DEFAULT_WORK_VALUE = 20;
    public static final int DEFAULT_REST_VALUE = 10;
    public static final int DEFAULT_EXERCISES_VALUE = 8;
    public static final int DEFAULT_SETS_VALUE = 4;
    public static final int DEFAULT_SETS_REST_VALUE = 0;
    public static final int DEFAULT_COOLING_VALUE = 0;

    /*
    Activities names used in SharedPreferences
     */
    public static final String STOPWATCH_ACTIVITY_NAME = "Stoper";
    public static final String TIMERS_ACTIVITY_NAME = "Timery";
    public static final String CALCULATORS_ACTIVITY_NAME = "Kalkulatory";
    public static final String CALORIES_ACTIVITY_NAME = "Tabele kalorii";
    public static final String DAILY_CALORIES_ACTIVITY_NAME = "Dziennik kalorii";
    public static final String EXERCISES_ACTIVITY_NAME = "Ćwiczenia";
    public static final String WORKOUTS_ACTIVITY_NAME = "Zestawy ćwiczeń";
    public static final String TIMETABLE_ACTIVITY_NAME = "Dziennik treningowy";
    public static final String NOTEPAD_ACTIVITY_NAME = "Notatnik";


    public static void hideKeyboard(Context context, View view)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideKeyboardOnFocusChange(ViewGroup parent, final Context context)
    {
        for (int i = 0; i < parent.getChildCount(); i++)
        {
            if (parent.getChildAt(i) instanceof EditText)
            {
                parent.getChildAt(i).setOnFocusChangeListener(new View.OnFocusChangeListener()
                {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus)
                    {
                        if (!hasFocus)
                        {
                            hideKeyboard(context, v);
                        }
                    }
                });
            }
        }
    }




    public static void setAppColor(AppCompatActivity activity)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String color = sharedPreferences.getString(APP_COLOR,"blue");
        if(color.equals("green"))
        {
            activity.setTheme(R.style.AppThemeGreen);
        }
        else if(color.equals("blue"))
        {
            activity.setTheme(R.style.AppThemeBlue);
        }
        else if(color.equals("white"))
        {
            activity.setTheme(R.style.AppThemeWhite);
        }
    }

    public static void showPopupDelete(final Context context, View view, final int position)
    {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.deleteExerciseFromWorkout:
                    {
                        openDeleteConfirmDialog(context, position);
                        return true;
                    }
                    default:
                    {
                        return false;
                    }
                }
            }
        });

        popupMenu.inflate(R.menu.delete_popup_menu);
        popupMenu.show();

    }

    public static void openDeleteConfirmDialog(Context context, int position)
    {
        DeleteConfirmDialog deleteExerciseFromWorkoutDialog = new DeleteConfirmDialog();
        Bundle args = new Bundle();
        args.putInt("position",position);
        deleteExerciseFromWorkoutDialog.setArguments(args);
        deleteExerciseFromWorkoutDialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"delete exercise from workout dialog");
    }

    public static void openAddToTimetable(Context context)
    {
        AddWorkoutToTimetableDialog addWorkoutToTimetableDialog = new AddWorkoutToTimetableDialog();
        addWorkoutToTimetableDialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "add interval workout to timetable");
    }

    public static void setActivityOrientation(AppCompatActivity activity)
    {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


}