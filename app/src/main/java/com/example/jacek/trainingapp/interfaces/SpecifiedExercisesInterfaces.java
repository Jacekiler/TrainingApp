package com.example.jacek.trainingapp.interfaces;

import com.example.jacek.trainingapp.common.DeleteConfirmDialog;
import com.example.jacek.trainingapp.physicalActivity.exercises.AddExerciseDialog;
import com.example.jacek.trainingapp.physicalActivity.exercises.AddExerciseToWorkoutOnClickDialog;
import com.example.jacek.trainingapp.physicalActivity.exercises.EditExerciseDialog;
import com.example.jacek.trainingapp.physicalActivity.exercises.Exercise;

public interface SpecifiedExercisesInterfaces extends AddExerciseDialog.AddExerciseDialogListener,
        AddExerciseToWorkoutOnClickDialog.AddExerciseToWorkoutDialogListener, EditExerciseDialog.EditExerciseDialogListener,
        DeleteConfirmDialog.DeleteConfirmDialogListener
{
    @Override
    void onAddExerciseClick(Exercise exercise);

    @Override
    void onDeleteClick(int position);

    @Override
    void onAddExerciseToWorkoutClick(int position, String type);

    @Override
    void onUpdateExerciseClick(int position, Exercise exercise);
}
