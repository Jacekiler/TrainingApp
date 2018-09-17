package com.example.jacek.trainingapp.interfaces;

import com.example.jacek.trainingapp.common.DeleteConfirmDialog;
import com.example.jacek.trainingapp.physicalActivity.exercises.Exercise;
import com.example.jacek.trainingapp.physicalActivity.workouts.AddExerciseToWorkoutDialog;
import com.example.jacek.trainingapp.physicalActivity.workouts.AddWorkoutDialog;

public interface WorkoutsInterfaces extends AddWorkoutDialog.AddWorkoutDialogListener, DeleteConfirmDialog.DeleteConfirmDialogListener,
        AddExerciseToWorkoutDialog.AddExerciseToWorkoutDialogListener
{
    @Override
    void onDeleteClick(int position);

    @Override
    void onAddExerciseToWorkoutClick(String workout, Exercise exercise);

    @Override
    void onAddWorkoutClick(String workout);
}
