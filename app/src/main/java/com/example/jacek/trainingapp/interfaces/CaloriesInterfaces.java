package com.example.jacek.trainingapp.interfaces;

import com.example.jacek.trainingapp.common.DeleteConfirmDialog;
import com.example.jacek.trainingapp.productsAndCalories.calories.AddProductDialog.AddDialogListener;
import com.example.jacek.trainingapp.productsAndCalories.calories.AddProductToDayDialog.AddProductToDayDialogListener;
import com.example.jacek.trainingapp.productsAndCalories.calories.AddProductToDayOnClickDialog;
import com.example.jacek.trainingapp.productsAndCalories.calories.EditProductDialog;
import com.example.jacek.trainingapp.productsAndCalories.calories.Product;
import com.example.jacek.trainingapp.productsAndCalories.dailyCalories.DailyCaloriesDetails;

/**
 * Created by Jacek on 21.03.2018.
 */

public interface CaloriesInterfaces extends EditProductDialog.EditProductDialogListener,
        AddDialogListener, AddProductToDayDialogListener, AddProductToDayOnClickDialog.AddProductToDayOnClickDialogListener,
        DeleteConfirmDialog.DeleteConfirmDialogListener
{
    // DeleteProductDialog
    @Override
    void onDeleteClick(int position);

    // EditProductDialogListener
    @Override
    void onUpdateProductClick(int position, Product product);

    // AddDialogListener
    @Override
    void onAddProductClick(Product product);

    // AddProductToDayDialogListener
    @Override
    void onAddProductToDayClick(DailyCaloriesDetails dailyCaloriesDetails);

    @Override
    void onAdProductToDayOnClickClick(DailyCaloriesDetails dailyCaloriesDetails);
}
