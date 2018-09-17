package com.example.jacek.trainingapp.physicalActivity.workouts.interval;

import com.example.jacek.trainingapp.physicalActivity.exercises.Exercise;

public class IntervalWorkoutExercise
{
    private Integer id;
    private Exercise exercise;

    public IntervalWorkoutExercise(int id, Exercise exercise)
    {
        this.id = id;
        this.exercise = exercise;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Exercise getExercise()
    {
        return exercise;
    }



}
