package com.example.jacek.trainingapp.stopwatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicActivity;

public class StopwatchActivity extends BasicActivity
{

    private static final int MINUTE_IN_MILLIS = 60000;
    private static final int SECOND_IN_MILLIS = 1000;

    private LinearLayout stopLap;
    private LinearLayout resumeReset;

    private int lapsCounter;
    private TextView textTime;
    private Button startButton;
    private Button stopButton;
    private Button lapButton;
    private Button resumeButton;
    private Button resetButton;
    private Handler handler;
    private long startTime;
    private long timeInMilliseconds;
    private long timeSwapBuff;
    private long updateTime;
    private LinearLayout lapsContainer;
    private Runnable updateTimeThread;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        Log.d("ap_stopwatch","onCreate");


        setNavViewAndToolbar(R.id.stopwatch_layout);

        handler = new Handler();
        updateTimeThread = new Runnable()
        {
            @Override
            public void run()
            {
                timeInMilliseconds = SystemClock.uptimeMillis()-startTime;
                updateTime = timeSwapBuff+timeInMilliseconds;

                int mins = (int)updateTime/MINUTE_IN_MILLIS;
                int secs = (int)updateTime%MINUTE_IN_MILLIS/SECOND_IN_MILLIS;
                int milliseconds = (int) updateTime%SECOND_IN_MILLIS/10;

                textTime.setText(String.format("%02d",mins)+":"+String.format("%02d",secs)+":"+String.format("%02d",milliseconds));
                handler.postDelayed(this,0);
            }
        };

        initializeUI();

        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                startTime = SystemClock.uptimeMillis();

                handler.postDelayed(updateTimeThread,0);


                startButton.setVisibility(View.INVISIBLE);
                stopLap.setVisibility(View.VISIBLE);

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                timeSwapBuff += timeInMilliseconds;

                handler.removeCallbacks(updateTimeThread);

                stopLap.setVisibility(View.INVISIBLE);
                resumeReset.setVisibility(View.VISIBLE);


            }
        });

        lapButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = inflater.inflate(R.layout.row_stopwatch,null);
                TextView txtValue = addView.findViewById(R.id.lapTimeTextViewRow);
                txtValue.setText((lapsCounter++)+". "+textTime.getText());
                lapsContainer.addView(addView);

            }
        });

        resumeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(updateTimeThread,0);

                resumeReset.setVisibility(View.INVISIBLE);
                stopLap.setVisibility(View.VISIBLE);


            }
        });

        resetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                resetStopwatch();

                resumeReset.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.VISIBLE);
            }
        });



    }

    @Override
    public void initializeUI()
    {
        textTime = findViewById(R.id.stopwatchTextView);
        startButton = findViewById(R.id.buttonStart);
        stopButton = findViewById(R.id.buttonStop);
        lapButton = findViewById(R.id.buttonLap);
        resumeButton = findViewById(R.id.buttonResume);
        resetButton = findViewById(R.id.buttonReset);
        lapsContainer = findViewById(R.id.lapsContainer);
        lapsCounter = 1;

        stopLap = findViewById(R.id.stopLapLayoutStopwatch);
        resumeReset = findViewById(R.id.resumeResetLayoutStopwatch);
    }

    public void resetStopwatch()
    {
        textTime.setText(String.format("%02d",0)+":"+String.format("%02d",0)+":"+String.format("%02d",0));
        timeSwapBuff = 0L;
        lapsContainer.removeAllViews();
        lapsCounter = 1;
    }

}
