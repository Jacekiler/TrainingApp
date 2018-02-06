package com.example.jacek.trainingapp;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StopwatchActivity extends BasicActivity
{

    int lapsCounter;
    TextView textTime;
    Button startButton;
    Button stopButton;
    Button lapButton;
    Button resumeButton;
    Button resetButton;
    Handler handler = new Handler();
    long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updateTime = 0L;
    LinearLayout lapsContainer;

    Runnable updateTimeThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis()-startTime;
            updateTime = timeSwapBuff+timeInMilliseconds;

            int milliseconds = (int)(updateTime%1000/10);   // /10 -> zmniejszenie z trzycyfrowej do dwucyfrowej -> sprobowac formatowaniem zamiast tego
            int secs = (int)(updateTime/1000);
            int mins = secs/60;
            secs %= 60;
//            mins %= 60;   // przy uzyciu %60 trzeba dodac godziny

            textTime.setText(String.format("%02d",mins)+":"+String.format("%02d",secs)+":"+String.format("%02d",milliseconds));
            handler.postDelayed(this,0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        Log.d("ap_stopwatch","onCreate");
        // Czy coś z tego da się przenieść do BasicActivity (nadklasa)
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout)findViewById(R.id.stopwatch_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // koniec


        textTime = (TextView)findViewById(R.id.textView);
        startButton = (Button)findViewById(R.id.buttonStart);
        stopButton = (Button)findViewById(R.id.buttonStop);
        lapButton = (Button)findViewById(R.id.buttonLap);
        resumeButton = (Button)findViewById(R.id.buttonResume);
        resetButton = (Button)findViewById(R.id.buttonReset);
        lapsContainer = (LinearLayout)findViewById(R.id.lapsContainer);
        lapsCounter = 1;

        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(updateTimeThread,0);
                startButton.setVisibility(View.INVISIBLE);
                stopButton.setVisibility(View.VISIBLE);
                lapButton.setVisibility(View.VISIBLE);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                timeSwapBuff += timeInMilliseconds;
                handler.removeCallbacks(updateTimeThread);

                stopButton.setVisibility(View.INVISIBLE);
                lapButton.setVisibility(View.INVISIBLE);
                resumeButton.setVisibility(View.VISIBLE);
                resetButton.setVisibility(View.VISIBLE);
            }
        });

        lapButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = inflater.inflate(R.layout.row,null);
                TextView txtValue = (TextView)addView.findViewById(R.id.textContent);
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

                resumeButton.setVisibility(View.INVISIBLE);
                resetButton.setVisibility(View.INVISIBLE);
                stopButton.setVisibility(View.VISIBLE);
                lapButton.setVisibility(View.VISIBLE);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                resetStopwatch();


                resumeButton.setVisibility(View.INVISIBLE);
                resetButton.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.VISIBLE);
            }
        });



    }

    public void resetStopwatch()
    {
        textTime.setText(String.format("%02d",0)+":"+String.format("%02d",0)+":"+String.format("%02d",0));
        startTime = 0L;
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updateTime = 0L;
        lapsContainer.removeAllViews();
        lapsCounter = 1;
    }




    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("ap_stopwatch","onPause");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("ap_stopwatch","onStart");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("ap_stopwatch","onResume");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("ap_stopwatch","onStop");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d("ap_stopwatch","onRestart");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("ap_stopwatch","onDestroy");
    }






}
