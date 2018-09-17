package com.example.jacek.trainingapp.calculators;

import com.example.jacek.trainingapp.R;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorBMIFragmentTest
{
    private CalculatorBMIFragment calculatorBMI;

    @Before
    public void setUp() throws Exception
    {
        calculatorBMI = new CalculatorBMIFragment();
    }

    @Test
    public void countBMI()
    {
        assertEquals(26.58,calculatorBMI.countBMI(89,183),0);
    }

    @Test
    public void getResultTextId()
    {
        assertEquals(R.string.severely_underweight, calculatorBMI.getResultTextId(16.99),0);
        assertEquals(R.string.underweight, calculatorBMI.getResultTextId(17),0);
        assertEquals(R.string.underweight, calculatorBMI.getResultTextId(18.49),0);
        assertEquals(R.string.normal_weight, calculatorBMI.getResultTextId(18.5),0);
        assertEquals(R.string.normal_weight, calculatorBMI.getResultTextId(24.99),0);
        assertEquals(R.string.overweight, calculatorBMI.getResultTextId(25),0);
        assertEquals(R.string.overweight, calculatorBMI.getResultTextId(29.99),0);
        assertEquals(R.string.obesity, calculatorBMI.getResultTextId(30),0);
    }



}