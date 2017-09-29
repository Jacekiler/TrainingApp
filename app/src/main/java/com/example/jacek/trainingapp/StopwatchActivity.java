package com.example.jacek.trainingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {

    TextView time;
    Button startButton;
    Button stopButton;
    Button lapButton;
    Button resumeButton;
    Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);



        time = (TextView)findViewById(R.id.textView);
        startButton = (Button)findViewById(R.id.buttonStart);
        stopButton = (Button)findViewById(R.id.buttonStop);
        lapButton = (Button)findViewById(R.id.buttonLap);
        resumeButton = (Button)findViewById(R.id.buttonResume);
        resetButton = (Button)findViewById(R.id.buttonReset);

        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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

            }
        });

        resumeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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
                resumeButton.setVisibility(View.INVISIBLE);
                resetButton.setVisibility(View.INVISIBLE);
                startButton.setVisibility(View.VISIBLE);
            }
        });



    }
}
