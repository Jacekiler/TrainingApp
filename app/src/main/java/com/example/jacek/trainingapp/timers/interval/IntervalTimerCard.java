package com.example.jacek.trainingapp.timers.interval;

/**
 * Created by Jacek on 14.04.2018.
 */

public class IntervalTimerCard
{
    private int id;
    private String name;
    private int prepare;
    private int work;
    private int rest;
    private int exercises;
    private int sets;
    private int setsRest;
    private int cooling;

    public IntervalTimerCard(int prepare, int work, int rest, int exercises, int sets, int setsRest, int cooling)
    {
        this.prepare = prepare;
        this.work = work;
        this.rest = rest;
        this.exercises = exercises;
        this.sets = sets;
        this.setsRest = setsRest;
        this.cooling = cooling;
    }

    public IntervalTimerCard(String name, int prepare, int work, int rest, int exercises, int sets, int setsRest, int cooling)
    {
        this.name = name;
        this.prepare = prepare;
        this.work = work;
        this.rest = rest;
        this.exercises = exercises;
        this.sets = sets;
        this.setsRest = setsRest;
        this.cooling = cooling;
    }

    public IntervalTimerCard(int id, String name, int prepare, int work, int rest, int exercises, int sets, int setsRest, int cooling)
    {
        this.id = id;
        this.name = name;
        this.prepare = prepare;
        this.work = work;
        this.rest = rest;
        this.exercises = exercises;
        this.sets = sets;
        this.setsRest = setsRest;
        this.cooling = cooling;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getPrepare()
    {
        return prepare;
    }

    public int getWork()
    {
        return work;
    }

    public int getRest()
    {
        return rest;
    }

    public int getExercises()
    {
        return exercises;
    }

    public void setExercises(int exercises)
    {
        this.exercises = exercises;
    }

    public int getSets()
    {
        return sets;
    }

    public int getSetsRest()
    {
        return setsRest;
    }

    public int getCooling()
    {
        return cooling;
    }




}