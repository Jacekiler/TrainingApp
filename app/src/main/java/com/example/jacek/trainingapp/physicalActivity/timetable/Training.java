package com.example.jacek.trainingapp.physicalActivity.timetable;

public class Training
{
    private int id;
    private String description;

    public Training(int id, String description)
    {
        this.id = id;
        this.description = description;
    }

    public int getId()
    {
        return id;
    }

    public String getDescription()
    {
        return description;
    }
}
