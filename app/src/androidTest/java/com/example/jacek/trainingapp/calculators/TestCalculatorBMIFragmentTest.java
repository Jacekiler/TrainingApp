package com.example.jacek.trainingapp.calculators;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jacek.trainingapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TestCalculatorBMIFragmentTest
{

    private TextView effect;
    private TextView result;
    private TextView weightTV;
    private TextView heightTV;
    private EditText weightET;
    private EditText heightET;
    private View view;

    private CalculatorsActivity calculatorsActivity;
    private CalculatorBMIFragment calculatorBMI;

    @Rule
    public ActivityTestRule<CalculatorsActivity> activityActivityTestRule = new ActivityTestRule<>(CalculatorsActivity.class);

    @Before
    public void setUp()
    {
        calculatorsActivity = activityActivityTestRule.getActivity();
        calculatorBMI = new CalculatorBMIFragment();
    }

    @Test
    public void checkEmptyFields()
    {
        // Dwa puste pola - komunikat o konieczności wypełnienia ich obu
        onView(withId(R.id.weightEditTextBMI)).perform(clearText());
        onView(withId(R.id.heightEditTextBMI)).perform(clearText());
        onView(withId(R.id.countButtonBMI)).perform(click());
        onView(withText(R.string.setWeightHeight)).inRoot(withDecorView(not(calculatorsActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        // Puste pole dotyczące wzrostu - komunikat o podaniu wzrostu
        onView(withId(R.id.weightEditTextBMI)).perform(clearText(),typeText("80"));
        onView(withId(R.id.heightEditTextBMI)).perform(clearText());
        onView(withId(R.id.countButtonBMI)).perform(click());
        onView(withText(R.string.setHeight)).inRoot(withDecorView(not(calculatorsActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));

        // Puste pole dotyczące wagi - komunikat o podaniu wagi
        onView(withId(R.id.weightEditTextBMI)).perform(clearText());
        onView(withId(R.id.heightEditTextBMI)).perform(clearText(), typeText("180"));
        onView(withId(R.id.countButtonBMI)).perform(click());
        onView(withText(R.string.setWeight)).inRoot(withDecorView(not(calculatorsActivity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
       }

//        calculatorBMI = (CalculatorBMIFragment)calculatorsActivity.getSupportFragmentManager().findFragmentByTag("bmi");
//    view = calculatorBMI.getActivity().findViewById(R.id.fragment_bmi);
//    effect = view.findViewById(R.id.effectTextViewBMI);
//    result = view.findViewById(R.id.resultTextViewBMI);
//    weightET = view.findViewById(R.id.weightEditTextBMI);
//    heightET = view.findViewById(R.id.heightEditTextBMI);
//    weightTV = view.findViewById(R.id.weightTextViewBMI);
//    heightTV = view.findViewById(R.id.heightTextViewBMI);


//    @Test
//    public void testPreCondition()
//    {
//        assertEquals(View.GONE, effect.getVisibility());
//        assertEquals(View.GONE, result.getVisibility());
//        assertEquals(View.VISIBLE, heightTV.getVisibility());
//        assertEquals(View.VISIBLE, heightET.getVisibility());
//        assertEquals(View.VISIBLE, weightTV.getVisibility());
//        assertEquals(View.VISIBLE, weightET.getVisibility());
//        assertEquals("", weightET.getText().toString());
//        assertEquals("", heightET.getText().toString());
//        assertEquals("", result.getText().toString());
//        assertEquals("", effect.getText().toString());
//
//    }


    @Test
    public void setEffectLabel()
    {
        assertEquals("", effect.getText().toString());
        calculatorBMI.setEffectLabel(24.5);
        onView(withId(R.id.effectTextViewBMI)).check(matches(withText(R.string.normal_weight)));
    }
}