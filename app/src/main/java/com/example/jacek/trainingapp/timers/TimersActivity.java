package com.example.jacek.trainingapp.timers;


import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.Toast;

import com.example.jacek.trainingapp.interfaces.TimersInterfaces;
import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicActivity;
import com.example.jacek.trainingapp.common.Utilities;
import com.example.jacek.trainingapp.timers.interval.IT;
import com.example.jacek.trainingapp.timers.interval.ITCard;
import com.example.jacek.trainingapp.timers.interval.ITData;
import com.example.jacek.trainingapp.timers.simple.SimpleTimer;

public class TimersActivity extends BasicActivity implements TimersInterfaces
{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TimersFragmentPagerAdapter pagerAdapter;
    private String activityType;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timers);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);


        initializeUI();
        activityType = getIntent().getExtras().getString("type");
        pagerAdapter.addFragment(new SimpleTimer(),"Zwykły");
        pagerAdapter.addFragment(new IT(), "Interwały");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        Bundle args = new Bundle();
        args.putString("type", activityType);

        if(activityType.equals(Utilities.ACTIVITY_TYPE_NORMAL))
        {
            setNavViewAndToolbar(R.id.timers_layout);
        }
        else
        {
            args.putStringArray("exercises",getIntent().getExtras().getStringArray("exercises"));
            viewPager.setCurrentItem(1);

            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        pagerAdapter.getItem(1).setArguments(args);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if(!activityType.equals(Utilities.ACTIVITY_TYPE_NORMAL))
        {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void initializeUI()
    {
        viewPager = findViewById(R.id.timerChooseViewPager);
        tabLayout = findViewById(R.id.timersTabs);
        pagerAdapter = new TimersFragmentPagerAdapter(getSupportFragmentManager());


    }



    @Override
    public void onAddIntervalClick(ITCard card)
    {

        ITData intervalTimersData = new ITData(this);
        intervalTimersData.open();

        if(intervalTimersData.add(card))
        {
            Toast.makeText(getApplicationContext(), "Dodano plan interwałowy", Toast.LENGTH_SHORT).show();
        }

        intervalTimersData.close();

    }

    @Override
    public void onBackPressed()
    {
        if(activityType.equals(Utilities.ACTIVITY_TYPE_NORMAL))
        {
            super.onBackPressed();
        }
        else
        {
            finish();
        }
    }


}
