package com.example.jacek.trainingapp.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.settings.SettingsActivity;

public abstract class BasicNoNavViewActivity extends AppCompatActivity
{
    protected Toolbar toolbar;
    protected static boolean colorChanged = false;

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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
            {

                if(colorChanged)
                {
                    setResult(RESULT_OK);
                    colorChanged = false;
                }
                finish();
                return true;
            }
            case R.id.settings:
            {
                Intent settings_intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(settings_intent,1);

                return true;
            }
            case R.id.contact:
            {
                Intent author_intent = new Intent(this, AuthorContactActivity.class);
                startActivity(author_intent);
                return true;
            }
            default:
            {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                colorChanged = true;
                recreate();
            }
        }
    }

    @Override
    public void onBackPressed()
    {

        if (colorChanged)
        {
            setResult(RESULT_OK);
            colorChanged = false;
            finish();
        }
        else
        {
            super.onBackPressed();
        }

    }

    public boolean isColorChanged()
    {
        return colorChanged;
    }

    public void setColorChanged(boolean colorChanged)
    {
        BasicNoNavViewActivity.colorChanged = colorChanged;
    }
}
