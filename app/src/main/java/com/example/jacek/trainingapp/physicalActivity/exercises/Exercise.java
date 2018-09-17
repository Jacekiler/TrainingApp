package com.example.jacek.trainingapp.physicalActivity.exercises;

import android.support.annotation.NonNull;

public class Exercise implements Comparable<Exercise>
{
    private String name;
    private String category;
    private String description;

    public Exercise(String name, String category)
    {
        this.name = name;
        this.category = category;
    }

    public Exercise(String name, String category, String description)
    {
        this.name = name;
        this.category = category;
        this.description = description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCategory()
    {
        return category;
    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public int compareTo(@NonNull Exercise o)
    {
        return name.compareTo(o.getName());
    }
}
