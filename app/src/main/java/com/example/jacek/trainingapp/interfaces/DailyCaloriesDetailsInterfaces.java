package com.example.jacek.trainingapp.interfaces;

import com.example.jacek.trainingapp.common.DeleteConfirmDialog;
import com.example.jacek.trainingapp.productsAndCalories.dailyCalories.AddDetailDialog;
import com.example.jacek.trainingapp.productsAndCalories.dailyCalories.DailyCaloriesDetails;
import com.example.jacek.trainingapp.productsAndCalories.dailyCalories.EditDetailDialog;

public interface DailyCaloriesDetailsInterfaces extends AddDetailDialog.AddDetailDialogListener,
        EditDetailDialog.EditDetailDialogListener, DeleteConfirmDialog.DeleteConfirmDialogListener
{
    @Override
    void onDeleteClick(int position);

    @Override
    void onAddDetailClick(DailyCaloriesDetails dailyCaloriesDetails);

    @Override
    void onUpdateDetailClick(int position, DailyCaloriesDetails dailyCaloriesDetails);
}
