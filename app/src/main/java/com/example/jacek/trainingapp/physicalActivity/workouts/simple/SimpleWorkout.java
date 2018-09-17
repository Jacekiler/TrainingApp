package com.example.jacek.trainingapp.physicalActivity.workouts.simple;

import java.util.ArrayList;

public class SimpleWorkout
{
    private String name;
    ArrayList<SimpleWorkoutExercise> exercises;

    public SimpleWorkout(String name)
    {
        this.name = name;
        exercises = new ArrayList<>();
    }

    public SimpleWorkout(String name, ArrayList<SimpleWorkoutExercise> exercises)
    {
        this.name = name;
        this.exercises = exercises;
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<SimpleWorkoutExercise> getExercises()
    {
        return exercises;
    }

}
