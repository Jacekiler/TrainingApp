package com.example.jacek.trainingapp.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicBackOnlyActivity;
import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.interfaces.SettingsInterfaces;

public class SettingsActivity extends BasicBackOnlyActivity implements SettingsInterfaces
{

    private TextView colorIcon;
    private TextView defaultPrepare;
    private TextView defaultWork;
    private TextView defaultRest;
    private TextView defaultExercises;
    private TextView defaultSets;
    private TextView defaultSetsRest;
    private TextView defaultCooling;
    private TextView currentStartActivity;
    private SharedPreferences sharedPreferences;
    private RelativeLayout appColors;
    private RelativeLayout defaultTimerValues;
    private RelativeLayout startActivity;
    private Switch vibrations;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        Utils.setAppColor(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setToolbar();

        initialize();

        defaultTimerValues.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openEditDefaultValuesDialog();
            }
        });

        appColors.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openChangeAppColorDialog();
            }
        });

        startActivity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openSetStartActivity();
            }
        });

        vibrations.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(isChecked)
                {
                    editor.putBoolean(Utils.VIBRATIONS_ON,true);
                }
                else
                {
                    editor.putBoolean(Utils.VIBRATIONS_ON,false);
                }
                editor.commit();
            }
        });


    }

    public void initialize()
    {

        colorIcon = findViewById(R.id.appColorSettingsIcon);
        defaultPrepare = findViewById(R.id.defaultPrepareSettings);
        defaultWork = findViewById(R.id.defaultWrokSettings);
        defaultRest = findViewById(R.id.defaultRestSettings);
        defaultExercises = findViewById(R.id.defaultExercisesSettings);
        defaultSets = findViewById(R.id.defaultSetsSettings);
        defaultSetsRest = findViewById(R.id.defaultSetsRestSettings);
        defaultCooling = findViewById(R.id.defaultCoolingSettings);
        appColors = findViewById(R.id.appColorsLayout);
        defaultTimerValues = findViewById(R.id.defaultTimerValuesLayout);
        startActivity = findViewById(R.id.startActivityLayout);
        currentStartActivity = findViewById(R.id.currentStartActivity);
        vibrations = findViewById(R.id.vibrationsSwitchSettings);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        defaultPrepare.setText(String.valueOf(sharedPreferences.getInt("defaultPrepare", Utils.DEFAULT_PREPARE_VALUE)));
        defaultWork.setText(String.valueOf(sharedPreferences.getInt("defaultWork", Utils.DEFAULT_WORK_VALUE)));
        defaultRest.setText(String.valueOf(sharedPreferences.getInt("defaultRest", Utils.DEFAULT_REST_VALUE)));
        defaultExercises.setText(String.valueOf(sharedPreferences.getInt("defaultExercises", Utils.DEFAULT_EXERCISES_VALUE)));
        defaultSets.setText(String.valueOf(sharedPreferences.getInt("defaultSets", Utils.DEFAULT_SETS_VALUE)));
        defaultSetsRest.setText(String.valueOf(sharedPreferences.getInt("defaultSetsRest", Utils.DEFAULT_SETS_REST_VALUE)));
        defaultCooling.setText(String.valueOf(sharedPreferences.getInt("defaultCooling", Utils.DEFAULT_COOLING_VALUE)));

        switch(sharedPreferences.getString("appColor","blue"))
        {
            case "green":
            {
                colorIcon.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.colorPrimaryDarkGreen));
                break;
            }
            case "blue":
            {
                colorIcon.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.colorPrimaryBlue));
                break;
            }
            case "white":
            {
                colorIcon.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.colorWhite));
                break;
            }
            default:
            {
                colorIcon.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.colorPrimaryBlue));
                break;
            }
        }

        currentStartActivity.setText(sharedPreferences.getString("startActivity", Utils.STOPWATCH_ACTIVITY_NAME));

        vibrations.setChecked(sharedPreferences.getBoolean(Utils.VIBRATIONS_ON, true));

    }

    public void openEditDefaultValuesDialog()
    {
        DefaultTimerValuesDialog defaultTimerValuesDialog = new DefaultTimerValuesDialog();
        defaultTimerValuesDialog.show(getSupportFragmentManager(),"edit default timer values dialog");
    }

    public void openChangeAppColorDialog()
    {
        AppColorDialog appColorDialog = new AppColorDialog();
        appColorDialog.show(getSupportFragmentManager(),"change app color dialog");
    }

    public void openSetStartActivity()
    {
        SetStartActivityDialog setStartActivityDialog = new SetStartActivityDialog();
        setStartActivityDialog.show(getSupportFragmentManager(),"set start activity dialog");
    }

    @Override
    public void onChangeColor()
    {
        recreate();
    }

    @Override
    public void onUpdateDefaultTimerValuesClick()
    {
        defaultPrepare.setText(String.valueOf(sharedPreferences.getInt("defaultPrepare", Utils.DEFAULT_PREPARE_VALUE)));
        defaultWork.setText(String.valueOf(sharedPreferences.getInt("defaultWork", Utils.DEFAULT_WORK_VALUE)));
        defaultRest.setText(String.valueOf(sharedPreferences.getInt("defaultRest", Utils.DEFAULT_REST_VALUE)));
        defaultExercises.setText(String.valueOf(sharedPreferences.getInt("defaultExercises", Utils.DEFAULT_EXERCISES_VALUE)));
        defaultSets.setText(String.valueOf(sharedPreferences.getInt("defaultSets", Utils.DEFAULT_SETS_VALUE)));
        defaultSetsRest.setText(String.valueOf(sharedPreferences.getInt("defaultSetsRest", Utils.DEFAULT_SETS_REST_VALUE)));
        defaultCooling.setText(String.valueOf(sharedPreferences.getInt("defaultCooling", Utils.DEFAULT_COOLING_VALUE)));
    }

    @Override
    public void onSetStartActivity()
    {
        currentStartActivity.setText(sharedPreferences.getString("startActivity", Utils.STOPWATCH_ACTIVITY_NAME));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch(item.getItemId())
        {

            case android.R.id.home:
            {
                setResult(RESULT_OK);
                finish();
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onBackPressed()
    {
        setResult(RESULT_OK);
        finish();
    }
}
