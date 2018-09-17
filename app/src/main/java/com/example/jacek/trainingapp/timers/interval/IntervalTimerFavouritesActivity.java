package com.example.jacek.trainingapp.timers.interval;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jacek.trainingapp.interfaces.ITFavoutritesInterfaces;
import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicNoNavViewActivity;

public class IntervalTimerFavouritesActivity extends BasicNoNavViewActivity implements ITFavoutritesInterfaces
{
    private RecyclerView recycler;
    private IntervalTimerFavouritesAdapter adapter;
    private IntervalTimerData cardsData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval_timers_favourites);

        setToolbar();

        initializeUI();

        cardsData = new IntervalTimerData(this);
        cardsData.open();
        adapter = new IntervalTimerFavouritesAdapter(this, cardsData);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));


    }



    public void initializeUI()
    {
        recycler = findViewById(R.id.intervalTimersRecyclerView);
    }




    @Override
    public void onUpdateITClick(int position, IntervalTimerCard card)
    {
        adapter.edit(position, card);
    }

    @Override
    public void onDeleteClick(int position)
    {
        adapter.delete(position);
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        cardsData.close();
    }
}
