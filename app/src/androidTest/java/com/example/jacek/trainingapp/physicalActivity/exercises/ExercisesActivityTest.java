package com.example.jacek.trainingapp.physicalActivity.exercises;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExercisesActivityTest
{
    private ExercisesActivity exercisesActivity;
    private RelativeLayout category;
    @Rule
    public ActivityTestRule<ExercisesActivity> exercisesActivityActivityTestRule = new ActivityTestRule<ExercisesActivity>(ExercisesActivity.class)
    {
        @Override
        protected Intent getActivityIntent()
        {
            Intent intent = new Intent(InstrumentationRegistry.getContext(), ExercisesActivity.class);
            intent.putExtra("type", Utils.ACTIVITY_TYPE_NORMAL);
            return intent;
        }
    };

    @Before public void setUp()
    {
        exercisesActivity = exercisesActivityActivityTestRule.getActivity();
        category = exercisesActivity.findViewById(R.id.armsCategoryLayout);
    }

    @Test public void testDisplay()
    {
//        onView(withId(R.id.armsCategoryLayout)).check(matches(isDisplayed()));
        assertEquals(View.VISIBLE,category.getVisibility());
    }
}