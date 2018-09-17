package com.example.jacek.trainingapp.physicalActivity.workouts.interval;

import java.util.ArrayList;

public class IntervalWorkout
{
    private String name;
    ArrayList<IntervalWorkoutExercise> exercises;

    public IntervalWorkout(String name)
    {
        this.name = name;
        exercises = new ArrayList<>();
    }

    public IntervalWorkout(String name, ArrayList<IntervalWorkoutExercise> exercises)
    {
        this.name = name;
        this.exercises = exercises;
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<IntervalWorkoutExercise> getExercises()
    {
        return exercises;
    }

}
