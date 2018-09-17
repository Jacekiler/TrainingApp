package com.example.jacek.trainingapp.interfaces;

import com.example.jacek.trainingapp.common.DeleteConfirmDialog;
import com.example.jacek.trainingapp.timers.interval.EditITDialog;
import com.example.jacek.trainingapp.timers.interval.IntervalTimerCard;

public interface ITFavoutritesInterfaces extends EditITDialog.EditITDialogListener,
        DeleteConfirmDialog.DeleteConfirmDialogListener
{
    @Override
    void onDeleteClick(int position);

    @Override
    void onUpdateITClick(int position, IntervalTimerCard card);
}
