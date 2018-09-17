package com.example.jacek.trainingapp.calculators;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;

import static android.widget.Toast.LENGTH_SHORT;


public class CalculatorBMIFragment extends Fragment
{
    private EditText weight;
    private EditText height;
    private Button calculateBMI;
    private TextView resultBMI;
    private TextView effectBMI;

    private static final int UNDERWEIGHT_LOWER_BOUND = 17;
    private static final double NORMAL_LOWER_BOUND = 18.5;
    private static final int OVERWEIGHT_LOWER_BOUND = 25;
    private static final int OBESITY_LOWER_BOUND = 30;

    public CalculatorBMIFragment()
    {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_calculator_bmi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);

        calculateBMI.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                runBMI();
            }
        });

        Utils.hideKeyboardOnFocusChange((ViewGroup)view, getContext());
    }

    public void initialize(View view)
    {
        weight = view.findViewById(R.id.weightEditTextBMI);
        height = view.findViewById(R.id.heightEditTextBMI);
        calculateBMI = view.findViewById(R.id.countButtonBMI);
        resultBMI = view.findViewById(R.id.resultTextViewBMI);
        effectBMI = view.findViewById(R.id.effectTextViewBMI);
    }

    public boolean checkEmptyFields()
    {
        if(TextUtils.isEmpty(weight.getText()))
        {
            if(TextUtils.isEmpty(height.getText()))
            {
                Toast.makeText(getContext(), R.string.setWeightHeight, LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getContext(), R.string.setWeight, LENGTH_SHORT).show();
            }
        }
        else if(TextUtils.isEmpty(height.getText()))
        {
            Toast.makeText(getContext(), R.string.setHeight, LENGTH_SHORT).show();
        }
        else
        {
            return false;
        }


        return true;
    }

    @SuppressLint("DefaultLocale")
    public void runBMI()
    {
        if(!checkEmptyFields())
        {
            double weightValue = Double.parseDouble(weight.getText().toString());
            int heightValue = Integer.parseInt(height.getText().toString());
            double result = countBMI(weightValue, heightValue);
            resultBMI.setVisibility(View.VISIBLE);
            resultBMI.setText(String.format("%.2f", result));

            effectBMI.setVisibility(View.VISIBLE);
            setEffectLabel(result);

            if(weight.hasFocus())
            {
                weight.clearFocus();
            }
            else if(height.hasFocus())
            {
                height.clearFocus();
            }
        }
        else
        {
            resultBMI.setVisibility(View.GONE);
            effectBMI.setVisibility(View.GONE);
        }
    }

    public double countBMI(double weight, int height)
    {
        return Math.round(weight / (height * height) * 10000 * 100) / 100.0;
    }

    public void setEffectLabel(double result)
    {
        effectBMI.setText(getResultTextId(result));
    }

    public int getResultTextId(double result)
    {
        if (result < UNDERWEIGHT_LOWER_BOUND)
        {
            return R.string.severely_underweight;
        }
        else if (result < NORMAL_LOWER_BOUND)
        {
            return R.string.underweight;
        }
        else if (result < OVERWEIGHT_LOWER_BOUND)
        {
            return R.string.normal_weight;
        }
        else if (result < OBESITY_LOWER_BOUND)
        {
            return R.string.overweight;
        }
        else
        {
            return R.string.obesity;
        }
    }
}