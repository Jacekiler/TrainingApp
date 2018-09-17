package com.example.jacek.trainingapp.notepad;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

public class ExitNoteDetailsDialog extends AppCompatDialogFragment
{
    private ExitNoteDetailsDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Zapisać zmiany przed wyjściem?")
                .setMessage("Niezapisane zmiany zostaną utracone")
                .setPositiveButton("Zapisz", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        listener.onSaveClick();
                    }
                })
                .setNegativeButton("Wyjdź", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        listener.onNotSaveClick();

                    }
                })
                .setNeutralButton("Anuluj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

        return builder.create();
    }

    public interface ExitNoteDetailsDialogListener
    {
        void onSaveClick();
        void onNotSaveClick();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (ExitNoteDetailsDialogListener)context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+" must implement ExitNoteDetailsDialogListener");
        }

    }
}