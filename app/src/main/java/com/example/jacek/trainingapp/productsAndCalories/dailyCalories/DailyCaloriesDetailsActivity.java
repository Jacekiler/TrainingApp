package com.example.jacek.trainingapp.productsAndCalories.dailyCalories;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jacek.trainingapp.interfaces.DailyCaloriesDetailsInterfaces;
import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicNoNavViewActivity;
import com.example.jacek.trainingapp.productsAndCalories.ProductsAndCaloriesData;

public class DailyCaloriesDetailsActivity extends BasicNoNavViewActivity implements DailyCaloriesDetailsInterfaces
{
    private TextView dateTextView;
    private RecyclerView recyclerView;
    private Button add;
    private String date;
    private DailyCaloriesDetailsAdapter adapter;
    private ProductsAndCaloriesData productsAndCaloriesData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calories_details);


        setToolbar();
        initialize();

        date = getIntent().getExtras().getString("date");


        dateTextView.setText(date);

        productsAndCaloriesData = new ProductsAndCaloriesData(this);
        productsAndCaloriesData.open();

        adapter = new DailyCaloriesDetailsAdapter(this, productsAndCaloriesData, date);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openAddDetailDialog();
            }
        });

    }

    public void initialize()
    {
        dateTextView = findViewById(R.id.dateDailyCaloriesDetails);
        recyclerView = findViewById(R.id.listDailyCaloriesDetails);
        add = findViewById(R.id.addDailyCaloriesDetails);
    }

    public void openAddDetailDialog()
    {
        AddDetailDialog addDetailDialog = new AddDetailDialog();
        Bundle args = new Bundle();
        args.putString("date",date);
        addDetailDialog.setArguments(args);
        addDetailDialog.show(getSupportFragmentManager(),"add detail to day dialog");
    }

    @Override
    public void onAddDetailClick(DailyCaloriesDetails dailyCaloriesDetails)
    {
        adapter.add(dailyCaloriesDetails);
    }

    @Override
    public void onDeleteClick(int position)
    {
        adapter.delete(position);
    }

    @Override
    public void onUpdateDetailClick(int position, DailyCaloriesDetails dailyCaloriesDetails)
    {
        adapter.edit(position,dailyCaloriesDetails);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        adapter.update();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        productsAndCaloriesData.close();
    }
}
