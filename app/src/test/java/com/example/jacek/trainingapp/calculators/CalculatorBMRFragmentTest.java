package com.example.jacek.trainingapp.calculators;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorBMRFragmentTest
{
    private CalculatorBMRFragment calculatorBMR;
    @Before
    public void setUp() throws Exception
    {
        calculatorBMR = new CalculatorBMRFragment();
    }

    @Test
    public void countBMR()
    {
        assertEquals(2309,calculatorBMR.countBMR(89,183,23,true),0);
        assertEquals(1822,calculatorBMR.countBMR(69,177,29,false),0);
    }
}