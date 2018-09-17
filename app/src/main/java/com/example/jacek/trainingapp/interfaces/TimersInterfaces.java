package com.example.jacek.trainingapp.interfaces;

import com.example.jacek.trainingapp.timers.interval.AddIntervalTimerConfirmDialog;
import com.example.jacek.trainingapp.timers.interval.IntervalTimerCard;

public interface TimersInterfaces extends AddIntervalTimerConfirmDialog.AddIntervalTimerDialogListener
{
    @Override
    void onAddIntervalClick(IntervalTimerCard card);
}
