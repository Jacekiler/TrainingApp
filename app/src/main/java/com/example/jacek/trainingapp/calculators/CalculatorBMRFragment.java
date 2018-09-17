package com.example.jacek.trainingapp.calculators;

import android.annotation.SuppressLint;
import android.graphics.Color;
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


public class CalculatorBMRFragment extends Fragment
{
    private EditText weight;
    private EditText height;
    private EditText age;
    private TextView man;
    private TextView woman;
    private Button calculateBMR;
    private TextView resultBMR;

    public CalculatorBMRFragment()
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
        return inflater.inflate(R.layout.fragment_calculator_bmr, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        man.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                man.setTextColor(Color.BLACK);
                woman.setTextColor(Color.GRAY);
                man.setSelected(true);
                woman.setSelected(false);

            }
        });

        woman.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                woman.setTextColor(Color.BLACK);
                man.setTextColor(Color.GRAY);
                woman.setSelected(true);
                man.setSelected(false);

            }
        });

        calculateBMR.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                runBMR();

            }
        });

        Utils.hideKeyboardOnFocusChange((ViewGroup)view,getContext());

    }

    public void initialize(View view)
    {
        weight = view.findViewById(R.id.weightEditTextBMR);
        height = view.findViewById(R.id.heightEditTextBMR);
        age = view.findViewById(R.id.ageEditTextBMR);
        calculateBMR = view.findViewById(R.id.countButtonBMR);
        resultBMR = view.findViewById(R.id.resultTextViewBMR);

        man = view.findViewById(R.id.manTextViewBMR);
        woman = view.findViewById(R.id.womanTextViewBMR);

        man.setSelected(true);
        woman.setSelected(false);
    }

    @SuppressLint("DefaultLocale")
    public void runBMR()
    {
        if (TextUtils.isEmpty(weight.getText()))
        {
            if(TextUtils.isEmpty(height.getText()))
            {
                if(TextUtils.isEmpty(age.getText()))
                {
                    Toast.makeText(getContext(),"Podaj dane",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(),"Podaj wagę i wzrost",Toast.LENGTH_SHORT).show();
                }
            }
            else if(TextUtils.isEmpty(age.getText()))
            {
                Toast.makeText(getContext(),"Podaj wagę i wiek",Toast.LENGTH_SHORT).show();
            }
        }
        else if(TextUtils.isEmpty(height.getText()))
        {
            if(TextUtils.isEmpty(age.getText()))
            {
                Toast.makeText(getContext(),"Podaj wzrost i wiek",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getContext(),"Podaj wzrost",Toast.LENGTH_SHORT).show();
            }
        }
        else if(TextUtils.isEmpty(age.getText()))
        {
            Toast.makeText(getContext(),"Podaj wiek",Toast.LENGTH_SHORT).show();
        }
        else
        {

            double weightValue = Double.parseDouble(weight.getText().toString());
            int heightValue = Integer.parseInt(height.getText().toString());
            int ageValue = Integer.parseInt(age.getText().toString());
            int result = countBMR(weightValue, heightValue, ageValue, man.isSelected()); //1.2*(9.99*w + 6.25*h - 4.92*a) + (man.isSelected()?5:-161);

            resultBMR.setText(String.format("%d", result));

            if(weight.hasFocus())
            {
                weight.clearFocus();
            }
            if(height.hasFocus())
            {
                height.clearFocus();
            }
            if(age.hasFocus())
            {
                age.clearFocus();
            }
        }
    }

    public int countBMR(double weight, int height, int age, boolean man)
    {
        return (int)Math.round(1.2 * (9.99 * weight + 6.25 * height - 4.92 * age) + (man?5:-161));
    }

}
