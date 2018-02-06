package com.example.jacek.trainingapp;


import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class TimersActivity extends BasicActivity
{

    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timers);


        // Czy coś z tego da się przenieść do BasicActivity (nadklasa)
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout)findViewById(R.id.timers_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // koniec


        tabLayout = (TabLayout)findViewById(R.id.tabs);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                switch (tab.getPosition())
                {
                    case 0:
                    {
                        SimpleTimer simpleTimer = new SimpleTimer();
                        transaction.replace(R.id.timer_choose_fragment_container, simpleTimer);
                        transaction.commit();
                        break;
                    }
                    case 1:
                    {
                        FreestyleTimer freestyleTimer = new FreestyleTimer();
                        transaction.replace(R.id.timer_choose_fragment_container, freestyleTimer);
                        transaction.commit();
                        break;
                    }
                    case 2:
                    {
                        IntervalTimer intervalTimer = new IntervalTimer();
                        transaction.replace(R.id.timer_choose_fragment_container, intervalTimer);
                        transaction.commit();
                        break;
                    }

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        tabLayout.getTabAt(0).select();


    }
}
