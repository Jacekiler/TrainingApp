package com.example.jacek.trainingapp.calculators;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicActivity;

public class CalculatorsActivity extends BasicActivity
{
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculators);

        setNavViewAndToolbar(R.id.calculators_layout);

        initializeUI();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                switch (parent.getItemAtPosition(position).toString())
                {
                    case "BMI":
                    {
                        CalculatorBMIFragment bmi = new CalculatorBMIFragment();
                        transaction.replace(R.id.calculator_choose_fragment_container,bmi,"bmi");
                        transaction.commit();
                        break;
                    }
                    case "BMR":
                    {
                        CalculatorBMRFragment bmr = new CalculatorBMRFragment();
                        transaction.replace(R.id.calculator_choose_fragment_container,bmr,"bmr");
                        transaction.commit();
                        break;
                    }

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });
    }

    @Override
    public void initializeUI()
    {
        spinner = findViewById(R.id.calculatorChooseSpinner);
    }

}
