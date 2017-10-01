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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    Toolbar toolbar;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



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
