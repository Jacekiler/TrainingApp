package com.example.jacek.trainingapp.physicalActivity.workouts.simple;

import android.support.annotation.NonNull;

import com.example.jacek.trainingapp.physicalActivity.exercises.Exercise;

public class SimpleWorkoutExercise implements Comparable<SimpleWorkoutExercise>
{
    private Integer id;
    private Exercise exercise;
    private int reps;

    public SimpleWorkoutExercise(int id, Exercise exercise, int reps)
    {
        this.id = id;
        this.exercise = exercise;
        this.reps = reps;
    }

    public int getId()
    {
        return id;
    }

    public Exercise getExercise()
    {
        return exercise;
    }

    public int getReps()
    {
        return reps;
    }

    public void setReps(int reps)
    {
        this.reps = reps;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof SimpleWorkoutExercise))
        {
            return false;
        }
        return id==((SimpleWorkoutExercise) obj).getId();
    }

    @Override
    public int compareTo(@NonNull SimpleWorkoutExercise o)
    {
        return id.compareTo(o.getId());
    }
}
