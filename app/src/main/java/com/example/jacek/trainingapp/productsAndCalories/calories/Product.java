package com.example.jacek.trainingapp.productsAndCalories.calories;

import android.support.annotation.NonNull;

/**
 * Created by Jacek on 17.03.2018.
 */

public class Product implements Comparable<Product>
{
    private String name;
    private int calories;
    private String category;

    public Product(String name, int calories, String category)
    {
        this.name = name;
        this.calories = calories;
        this.category = category;
    }


    public String getName()
    {
        return name;
    }

    public int getCalories()
    {
        return calories;
    }

    public String getCategory()
    {
        return category;
    }


    @Override
    public int compareTo(@NonNull Product o)
    {
        return name.compareTo(o.getName());
    }

    public boolean isFirstLetterLowercased()
    {
        return Character.isLowerCase(name.charAt(0));
    }

    public void capitalizeFirstLetter()
    {
        if(isFirstLetterLowercased())
        {
            name = name.substring(0,1).toUpperCase() + name.substring(1);
        }
    }
}
