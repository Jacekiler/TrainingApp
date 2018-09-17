package com.example.jacek.trainingapp.interfaces;

import com.example.jacek.trainingapp.settings.AppColorDialog;
import com.example.jacek.trainingapp.settings.DefaultTimerValuesDialog;
import com.example.jacek.trainingapp.settings.SetStartActivityDialog;

public interface SettingsInterfaces extends DefaultTimerValuesDialog.EditDefaultTimerValuesDialogListener,
        AppColorDialog.ChangeAppColorDialogListener, SetStartActivityDialog.SetStartActivityDialogListener
{
    @Override
    void onChangeColor();

    @Override
    void onUpdateDefaultTimerValuesClick();

    @Override
    void onSetStartActivity();
}
