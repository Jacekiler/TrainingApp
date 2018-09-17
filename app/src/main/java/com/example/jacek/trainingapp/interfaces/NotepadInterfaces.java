package com.example.jacek.trainingapp.interfaces;

import com.example.jacek.trainingapp.common.DeleteConfirmDialog;

public interface NotepadInterfaces extends DeleteConfirmDialog.DeleteConfirmDialogListener
{
    @Override
    void onDeleteClick(int position);
}
