package com.example.jacek.trainingapp.timers.interval;

import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicBackOnlyActivity;
import com.example.jacek.trainingapp.common.Utils;

import java.util.ArrayList;
import java.util.Locale;

public class IntervalTimerRunner extends BasicBackOnlyActivity
{
    private final int SECOND_IN_MILLIS = 1000;

    private TextView stepName;
    private TextView stepTime;
    private TextView nextName;
    private Button stopInterval;
    private Button startInterval;

    private int prepareTime;
    private int workTime;
    private int restTime;
    private int exercisesCount;
    private int setsCount;
    private int setsRestTime;
    private int coolingTime;
    private SharedPreferences sharedPreferences;

    private ArrayList<Integer> a1;
    private ArrayList<String> stepNamesList;
    private ArrayList<Integer> stepTimesList;
    private int count;

    private long timeLeftInMilliseconds;
    private long startTime;
    private int milliseconds;
    private Handler handler;
    private Runnable updateTimeThread;
    private Runnable playSoundsThread;

    private AudioAttributes audioAttributes;
    private SoundPool soundPool;
    private int sound1, sound2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval_timer_runner);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setToolbar();

        initialize();
        initializeLists();





        setUpTimer();
        startIT();


        startInterval.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startIT();
                changeStartStopVisibility();
            }
        });


        stopInterval.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                stopIT();
                changeStartStopVisibility();
            }
        });

    }

    public void setUpTimer()
    {
        count = 0;
        milliseconds = SECOND_IN_MILLIS - 1;
        stepTime.setText(String.format("%02d", stepTimesList.get(count)));
        stepName.setText(stepNamesList.get(count));
        nextName.setText(stepNamesList.get(count+1));
        count++;

        handler = new Handler();
        setUpUpdateTimeThread();
        setUpPlaySoundsThread();
    }

    public void setUpUpdateTimeThread()
    {
        updateTimeThread = new Runnable()
        {
            @Override
            public void run()
            {
                timeLeftInMilliseconds = startTime - SystemClock.uptimeMillis();

                if(timeLeftInMilliseconds>1000)
                {
                    int seconds = (int) timeLeftInMilliseconds / SECOND_IN_MILLIS;
                    stepTime.setText(String.format(Locale.ENGLISH, "%02d", seconds));
                }

                if (timeLeftInMilliseconds < 1000) // 1010?
                {
                    if (count < a1.size() )
//                    if (count < a1size)
                    {
                        stepName.setText(stepNamesList.get(count));
                        if(count == a1.size() - 1)
                        {
                            nextName.setText("Koniec");
                        }
                        else
                        {
                            nextName.setText(stepNamesList.get(count+1));
                        }
                        startTime = SystemClock.uptimeMillis() + stepTimesList.get(count) * 1000 + 999;    // 990?
                        handler.postDelayed(this, 0);
                        handler.postDelayed(playSoundsThread,0);
                        count++;
                    }
                    else
                    {
                        stepName.setText("Koniec");
                        nextName.setText("");
                        stepTime.setText("00");
                        stopUpdatingTextThread();
                        reset();
                    }
                }
                else
                {
                    handler.postDelayed(this, 0);
                    handler.postDelayed(playSoundsThread,0);
                }
            }
        };
    }

    public void setUpPlaySoundsThread()
    {
        playSoundsThread = new Runnable()
        {
            boolean three, two, one = false;
            @Override
            public void run()
            {
                if (timeLeftInMilliseconds < 1000)   // && timeLeftInMilliseconds > 950)
                {
                    playBeep(sound2,1, true);
                    three = false;
                    two = false;
                    one = false;
                }
                else if (!one && timeLeftInMilliseconds < 2000)   // && timeLeftInMilliseconds > 1950)
                {
                    playBeep(sound1,0, false);
                    one = true;
                }
                else if (!two && timeLeftInMilliseconds < 3000)   // && timeLeftInMilliseconds > 2950)
                {
                    playBeep(sound1,0, false);
                    two = true;
                }
                else if (!three && timeLeftInMilliseconds < 4000)   // && timeLeftInMilliseconds > 3950)
                {
                    playBeep(sound1,0, false);
                    three = true;
                }

                if (timeLeftInMilliseconds < 1000 && count >= a1.size()) // 1010?
                {
                    stopBeepingThread();
                }


            }
        };
    }

    public void changeStartStopVisibility()
    {
        if(startInterval.getVisibility()==View.VISIBLE)
        {
            startInterval.setVisibility(View.INVISIBLE);
            stopInterval.setVisibility(View.VISIBLE);
        }
        else
        {
            startInterval.setVisibility(View.VISIBLE);
            stopInterval.setVisibility(View.INVISIBLE);
        }
    }

    public void reset()
    {
        count = 0;
        initializeLists();
        stopInterval.setVisibility(View.INVISIBLE);
        startInterval.setVisibility(View.VISIBLE);
    }

    public void startIT()
    {
        startTime = SystemClock.uptimeMillis() + Integer.parseInt(stepTime.getText().toString()) * SECOND_IN_MILLIS + milliseconds;

        handler.postDelayed(updateTimeThread,0);
        handler.postDelayed(playSoundsThread,0);
    }

    public void stopIT()
    {
        milliseconds = (int)timeLeftInMilliseconds%SECOND_IN_MILLIS;
        handler.removeCallbacks(updateTimeThread);
        handler.removeCallbacks(playSoundsThread);
    }

    public void stopUpdatingTextThread()
    {
        handler.removeCallbacks(updateTimeThread);
    }

    public void stopBeepingThread()
    {
        handler.removeCallbacks(playSoundsThread);
    }

    public void initialize()
    {
        stepName = findViewById(R.id.stepName);
        stepTime = findViewById(R.id.stepTime);
        nextName = findViewById(R.id.nextExerciseName);
        stopInterval = findViewById(R.id.stopInterval);
        startInterval = findViewById(R.id.startInterval);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        prepareTime = sharedPreferences.getInt("prepare",sharedPreferences.getInt("defaultPrepare", Utils.DEFAULT_PREPARE_VALUE));
        workTime = sharedPreferences.getInt("work", sharedPreferences.getInt("defaultWork", Utils.DEFAULT_WORK_VALUE));
        restTime = sharedPreferences.getInt("rest", sharedPreferences.getInt("defaultRest", Utils.DEFAULT_REST_VALUE));
        exercisesCount = sharedPreferences.getInt("exercises", sharedPreferences.getInt("defaultExercises", Utils.DEFAULT_EXERCISES_VALUE));
        setsCount = sharedPreferences.getInt("sets", sharedPreferences.getInt("defaultSets", Utils.DEFAULT_SETS_VALUE));
        setsRestTime = sharedPreferences.getInt("setsRest", sharedPreferences.getInt("defaultSetsRest", Utils.DEFAULT_SETS_REST_VALUE));
        coolingTime = sharedPreferences.getInt("cooling", sharedPreferences.getInt("defaultCooling", Utils.DEFAULT_COOLING_VALUE));

        setUpAudio();
    }

    public void initializeLists()
    {
        int c = 1;
        a1 = new ArrayList<>();
        stepNamesList = new ArrayList<>();
        stepTimesList = new ArrayList<>();

        if (prepareTime > 0)
        {
            a1.add(0);
            stepNamesList.add("Przygotowanie");
            stepTimesList.add(prepareTime);
        }

        if(getIntent().getExtras().getString("type").equals("startingWorkout"))
        {
            String[] exercises = getIntent().getExtras().getStringArray("exercises");
            for (int i = 0; i < setsCount; i++)
            {
                if (restTime > 0)
                {
                    for (int j = 0; j < exercisesCount; j++)
                    {
                        a1.add(c);
                        stepNamesList.add(exercises[j]);
                        stepTimesList.add(workTime);
                        c++;
                        a1.add(c);
                        stepNamesList.add("Przerwa");
                        stepTimesList.add(restTime);
                        c++;
                    }
                }
                else
                {
                    for (int j = 0; j < exercisesCount; j++)
                    {
                        a1.add(c);
                        stepNamesList.add(exercises[j]);
                        stepTimesList.add(workTime);
                        c++;
                    }
                }

                if (setsRestTime > 0)
                {
                    if (i < setsCount - 1)
                    {
                        a1.add(c);
                        stepNamesList.add("Przerwa między seriami");
                        stepTimesList.add(setsRestTime);
                        c++;
                    }
                }
            }
        }
        else
        {
            for (int i = 0; i < setsCount; i++)
            {
                if (restTime > 0)
                {
                    for (int j = 0; j < exercisesCount; j++)
                    {
                        a1.add(c);
                        stepNamesList.add("Praca");
                        stepTimesList.add(workTime);
                        c++;
                        a1.add(c);
                        stepNamesList.add("Przerwa");
                        stepTimesList.add(restTime);
                        c++;
                    }
                }
                else
                {
                    for (int j = 0; j < exercisesCount; j++)
                    {
                        a1.add(c);
                        stepNamesList.add("Praca");
                        stepTimesList.add(workTime);
                        c++;
                    }
                }

                if (setsRestTime > 0)
                {
                    if (i < setsCount - 1)
                    {
                        a1.add(c);
                        stepNamesList.add("Przerwa między seriami");
                        stepTimesList.add(setsRestTime);
                        c++;
                    }
                }
            }
        }

        if (coolingTime > 0)
        {
            a1.add(c);
            stepNamesList.add("Schłodzenie");
            stepTimesList.add(coolingTime);
        }

    }

    public void setUpAudio()
    {
        audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        sound1 = soundPool.load(this,R.raw.beep1,1);
        sound2 = soundPool.load(this, R.raw.beep2,1);

    }

    public void playBeep(int soundID, int priority, boolean vibrations)
    {
        soundPool.play(soundID,1,1,priority,0,1);

        if(vibrations)
        {
            if(sharedPreferences.getBoolean(Utils.VIBRATIONS_ON,true))
            {
                Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else
                {
                    vibrator.vibrate(500);
                }
            }
        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
            {
                stopIT();
                finish();
                return true;
            }
            default:
            {
                return super.onOptionsItemSelected(item);
            }
        }
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        stopIT();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }

}
