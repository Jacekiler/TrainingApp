package com.example.jacek.trainingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    Toolbar toolbar;
    DrawerLayout drawer;

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





        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

    @Override
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.settings:

                return true;
            case R.id.author:

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
            case R.id.nav_home:
            {
                Intent home_intent = new Intent(this,MainActivity.class);
                startActivity(home_intent);
                closeDrawer();
                return true;
            }
            case R.id.nav_timers:
            {
                closeDrawer();
                return true;
            }
            case R.id.nav_stopwatch:
                Intent stopwatch_intent = new Intent(this,StopwatchActivity.class);
                startActivity(stopwatch_intent);
                closeDrawer();
                return true;
            case R.id.nav_calculators:
            {
                closeDrawer();
                return true;
            }
            case R.id.nav_calories:
            {
                closeDrawer();
                return true;
            }
            case R.id.nav_5:
            {
                closeDrawer();
                return true;
            }
            case R.id.nav_6:
            {
                closeDrawer();
                return true;
            }
            default:
                closeDrawer();
                return true;
        }

    }

    public void closeDrawer()
    {
        drawer.closeDrawer(GravityCompat.START);
    }


}
