package com.example.jacek.trainingapp.productsAndCalories.dailyCalories;

public class DailyCaloriesDetails
{
    private int id;
    private String date;
    private String name;
    private int amount;
    private int calories;

    public DailyCaloriesDetails(String date, String name, int calories)
    {
        this.date = date;
        this.name = name;
        this.calories = calories;
    }

    public DailyCaloriesDetails(String date, String name, int amount, int calories)
    {
        this.date = date;
        this.name = name;
        this.amount = amount;
        this.calories = calories;
    }

    public DailyCaloriesDetails(int id, String date, String name, int amount, int calories)
    {
        this.id = id;
        this.date = date;
        this.name = name;
        this.amount = amount;
        this.calories = calories;
    }



    public int getId()
    {
        return id;
    }

    public String getDate()
    {
        return date;
    }

    public String getName()
    {
        return name;
    }

    public int getAmount()
    {
        return amount;
    }

    public int getCalories()
    {
        return calories;
    }

}
