package com.example.jacek.trainingapp.common;

import android.os.Bundle;

import com.example.jacek.trainingapp.R;

public class AuthorContactActivity extends BasicBackOnlyActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Utils.setAppColor(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_contact);

        setToolbar();

    }

}
