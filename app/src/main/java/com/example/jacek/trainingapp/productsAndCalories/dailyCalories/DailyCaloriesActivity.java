package com.example.jacek.trainingapp.productsAndCalories.dailyCalories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.jacek.trainingapp.interfaces.DailyCaloriesInterfaces;
import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicActivity;
import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.productsAndCalories.ProductsAndCaloriesData;

import java.util.Date;

public class DailyCaloriesActivity extends BasicActivity implements DailyCaloriesInterfaces
{
    private RecyclerView recyclerView;
    private DailyCaloriesAdapter adapter;
    private ProductsAndCaloriesData productsAndCaloriesData;
    private Button addDay;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calories);

        setNavViewAndToolbar(R.id.daily_calories_layout);

        initializeUI();

        productsAndCaloriesData = new ProductsAndCaloriesData(this);
        productsAndCaloriesData.open();

        adapter = new DailyCaloriesAdapter(this, productsAndCaloriesData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        date = Utils.shortFormatDate.format(new Date());

        checkIfDayExists();

        addDay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), DailyCaloriesDetailsActivity.class);
                intent.putExtra("date",date);
                startActivityForResult(intent, 2);
            }
        });



    }


    @Override
    public void onDeleteClick(int position)
    {
        adapter.delete(position);
        checkIfDayExists();
    }

    @Override
    public void initializeUI()
    {
        super.initializeUI();
        recyclerView = findViewById(R.id.dailyCaloriesRecyclerView);
        addDay = findViewById(R.id.addDayButton);
    }

    public void checkIfDayExists()
    {
        if(adapter.hasDay(date))
        {
            addDay.setEnabled(false);
            addDay.setVisibility(View.INVISIBLE);
        }
        else
        {
            addDay.setEnabled(true);
            addDay.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        adapter.update();
        checkIfDayExists();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        productsAndCaloriesData.close();

    }


}
