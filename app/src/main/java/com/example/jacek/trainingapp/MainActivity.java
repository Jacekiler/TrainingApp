package com.example.jacek.trainingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button toStopwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toStopwatch = (Button)findViewById(R.id.button);

        toStopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopwatchIntent = new Intent(v.getContext(),StopwatchActivity.class);
                startActivity(stopwatchIntent);
            }
        });


    }
}
