package com.example.jacek.trainingapp.timers.simple;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.timers.TimersActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class SimpleTimerTest
{
    private TimersActivity timersActivity;
    private SimpleTimerFragment simpleTimer;
    private View view;

    private EditText hours;
    private Button addHours;
    @Rule
    public ActivityTestRule<TimersActivity> activityActivityTestRule = new ActivityTestRule<TimersActivity>(TimersActivity.class)
    {
        @Override
        protected Intent getActivityIntent()
        {
            Intent intent = new Intent(InstrumentationRegistry.getContext(), TimersActivity.class);
            intent.putExtra("type", Utils.ACTIVITY_TYPE_NORMAL);
            return intent;
        }
    };

    @Before
    public void setup()
    {
//        timersActivity = activityActivityTestRule.getActivity();
//        simpleTimer = (SimpleTimerFragment)timersActivity.getSupportFragmentManager().getFragments().get(0);
//        view = simpleTimer.getActivity().findViewById(R.id.simple_timer_fragment);
//
//        hours = view.findViewById(R.id.hoursSimpleTimer);
//        addHours = view.findViewById(R.id.addHourSimpleTimer);
    }

    @Test
    public void increaseText()
    {
        onView(withId(R.id.hoursSimpleTimer)).check(matches(withText("00")));
        onView(withId(R.id.addHourSimpleTimer)).perform(click());
        onView(withId(R.id.hoursSimpleTimer)).check(matches(withText("01")));

        onView(withId(R.id.minutesSimpleTimer)).check(matches(withText("01")));
        onView(withId(R.id.addMinuteSimpleTimer)).perform(click());
        onView(withId(R.id.minutesSimpleTimer)).check(matches(withText("02")));

        onView(withId(R.id.secondsSimpleTimer)).check(matches(withText("00")));
        onView(withId(R.id.addSecondSimpleTimer)).perform(click());
        onView(withId(R.id.secondsSimpleTimer)).check(matches(withText("01")));
    }

    @Test
    public void decreaseText()
    {
        onView(withId(R.id.hoursSimpleTimer)).check(matches(withText("00")));
        onView(withId(R.id.subHourSimpleTimer)).perform(click());
        onView(withId(R.id.hoursSimpleTimer)).check(matches(withText("99")));

        onView(withId(R.id.minutesSimpleTimer)).check(matches(withText("01")));
        onView(withId(R.id.subMinuteSimpleTimer)).perform(click());
        onView(withId(R.id.minutesSimpleTimer)).check(matches(withText("00")));

        onView(withId(R.id.secondsSimpleTimer)).check(matches(withText("00")));
        onView(withId(R.id.subSecondSimpleTimer)).perform(click());
        onView(withId(R.id.secondsSimpleTimer)).check(matches(withText("59")));
    }
}