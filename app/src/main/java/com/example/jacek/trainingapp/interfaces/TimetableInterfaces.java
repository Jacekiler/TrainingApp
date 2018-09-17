package com.example.jacek.trainingapp.interfaces;

import com.example.jacek.trainingapp.common.DeleteConfirmDialog;
import com.example.jacek.trainingapp.physicalActivity.timetable.AddTrainingDialog;
import com.example.jacek.trainingapp.physicalActivity.timetable.TrainingViewDialog;

public interface TimetableInterfaces extends AddTrainingDialog.AddTrainingDialogListener,
        DeleteConfirmDialog.DeleteConfirmDialogListener, TrainingViewDialog.TrainingViewDialogListener
{
    @Override
    void onDeleteClick(int position);

    @Override
    void onAddTrainingClick(String description);

    @Override
    void onSaveTrainingClick(int position, String description);

    @Override
    void onEmptyTrainingSaveClick(int position);
}
