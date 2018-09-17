package com.example.jacek.trainingapp.calculators;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Spinner;

import com.example.jacek.trainingapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CalculatorsActivityTest
{
    private CalculatorsActivity calculatorsActivity;
    private Spinner spinner;

    @Rule
    public ActivityTestRule<CalculatorsActivity> activityActivityTestRule = new ActivityTestRule<>(CalculatorsActivity.class);

    @Before
    public void setup()
    {
        calculatorsActivity = activityActivityTestRule.getActivity();
        spinner = calculatorsActivity.findViewById(R.id.calculatorChooseSpinner);
    }

    @Test
    public void initializeUI()
    {
        assertEquals(R.id.calculatorChooseSpinner, spinner.getId());
    }
}