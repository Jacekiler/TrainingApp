package com.example.jacek.trainingapp.productsAndCalories.dailyCalories;

import android.support.annotation.NonNull;

public class DailyCalories implements Comparable<DailyCalories>
{
    private String date;
    private int calories;

    public DailyCalories(String date, int calories)
    {
        this.date = date;
        this.calories = calories;
    }

    public String getDate()
    {
        return date;
    }

    public int getCalories()
    {
        return calories;
    }

    @Override
    public int compareTo(@NonNull DailyCalories o)
    {
        return -1 * date.compareTo(o.getDate());
    }
}
