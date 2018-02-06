package com.example.jacek.trainingapp;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class MainActivity extends BasicActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("ap_main","onCreate");

        // Czy coś z tego da się przenieść do BasicActivity (nadklasa)
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout)findViewById(R.id.main_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // koniec


    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("ap_main","onPause");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("ap_main","onStart");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("ap_main","onResume");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.d("ap_main","onStop");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d("ap_main","onRestart");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("ap_main","onDestroy");
    }



}
