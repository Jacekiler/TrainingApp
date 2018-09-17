package com.example.jacek.trainingapp.interfaces;

import com.example.jacek.trainingapp.common.DeleteConfirmDialog;
import com.example.jacek.trainingapp.physicalActivity.workouts.interval.AddWorkoutToTimetableDialog;
import com.example.jacek.trainingapp.physicalActivity.workouts.interval.DeleteWorkoutDialog;

public interface SpecifiedWorkoutsInterfaces extends DeleteConfirmDialog.DeleteConfirmDialogListener,
        DeleteWorkoutDialog.DeleteWorkoutDialogListener, AddWorkoutToTimetableDialog.AddWorkoutToTimetableDialogListener
{
    @Override
    void onDeleteClick(int position);

    @Override
    void onAddTrainingToTimetableClick(String date);

    @Override
    void onDeleteWorkoutClick(int position);
}
