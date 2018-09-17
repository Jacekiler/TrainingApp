package com.example.jacek.trainingapp.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.jacek.trainingapp.R;

public abstract class BasicBackOnlyActivity extends AppCompatActivity
{
    protected Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        Utils.setAppColor(this);
        super.onCreate(savedInstanceState);
        Utils.setActivityOrientation(this);

    }

    public void setToolbar()
    {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
            {
                finish();
                return true;
            }
            default:
            {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
