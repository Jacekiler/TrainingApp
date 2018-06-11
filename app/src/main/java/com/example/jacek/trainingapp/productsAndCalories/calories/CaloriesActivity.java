package com.example.jacek.trainingapp.productsAndCalories.calories;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.interfaces.CaloriesInterfaces;
import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicActivity;
import com.example.jacek.trainingapp.productsAndCalories.ProductsAndCaloriesData;
import com.example.jacek.trainingapp.productsAndCalories.dailyCalories.DailyCaloriesDetails;

public class CaloriesActivity extends BasicActivity implements CaloriesInterfaces
{
    private Button addButton;
    private SearchView searchView;
    private Spinner categorySpinner;

    private RecyclerView recycler;
    private CaloriesAdapter recyclerAdapter;
    private ProductsAndCaloriesData productsAndCaloriesData;

    private String activityType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories);
        activityType = getIntent().getExtras().getString("type");

        initializeUI();

        productsAndCaloriesData = new ProductsAndCaloriesData(this);
        productsAndCaloriesData.open();



        if(activityType.equals(Utils.ACTIVITY_TYPE_NORMAL))
        {
            setNavViewAndToolbar(R.id.calories_layout);
            recyclerAdapter = new CaloriesAdapter(this, productsAndCaloriesData, activityType);
        }
        else
        {
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            recyclerAdapter = new CaloriesAdapter(this, productsAndCaloriesData, activityType, getIntent().getExtras().getString("date"));
        }

        recycler.setAdapter(recyclerAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        searchView.onActionViewExpanded();
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                recyclerAdapter.search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                recyclerAdapter.search(newText);
                categorySpinner.setSelection(0);
                return true;
            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                recyclerAdapter.filterByCategory(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openAddDialog();

            }
        });

    }

    @Override
    public void initializeUI()
    {
        addButton = findViewById(R.id.addProductButt);
        searchView = findViewById(R.id.productSearchView);

        recycler = findViewById(R.id.productsRecyclerView);

        categorySpinner = findViewById(R.id.categoryFilterSpinner);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if(!activityType.equals(Utils.ACTIVITY_TYPE_NORMAL))
        {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onUpdateProductClick(int position, Product product)
    {
        recyclerAdapter.edit(position, product);
        productsAndCaloriesData.updateDailyDetails(product);
    }

    @Override
    public void onAddProductClick(Product product)
    {
        recyclerAdapter.add(product);
    }

    @Override
    public void onAddProductToDayClick(DailyCaloriesDetails dailyCaloriesDetails)
    {
        productsAndCaloriesData.addProductToDay(dailyCaloriesDetails);
    }

    @Override
    public void onAdProductToDayOnClickClick(DailyCaloriesDetails dailyCaloriesDetails)
    {
        productsAndCaloriesData.addProductToDay(dailyCaloriesDetails);
    }

    public void openAddDialog()
    {
        AddProductDialog addProductDialog = new AddProductDialog();
        addProductDialog.show(getSupportFragmentManager(), "addToDay product dialog");
    }

    @Override
    public void onDeleteClick(int position)
    {
        recyclerAdapter.delete(position);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
            {
                finish();
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed()
    {
        if(activityType.equals(Utils.ACTIVITY_TYPE_NORMAL))
        {
            super.onBackPressed();
        }
        else
        {
            finish();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        productsAndCaloriesData.close();
    }





}
