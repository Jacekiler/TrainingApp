package com.example.jacek.trainingapp.interfaces;

import com.example.jacek.trainingapp.notepad.ExitNoteDetailsDialog;
import com.example.jacek.trainingapp.physicalActivity.workouts.interval.AddWorkoutToTimetableDialog;

public interface NoteDetailInterfaces extends ExitNoteDetailsDialog.ExitNoteDetailsDialogListener,
        AddWorkoutToTimetableDialog.AddWorkoutToTimetableDialogListener
{
    @Override
    void onSaveClick();

    @Override
    void onNotSaveClick();

    @Override
    void onAddTrainingToTimetableClick(String date);
}
