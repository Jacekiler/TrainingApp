package com.example.jacek.trainingapp.timers.interval;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

/**
 * Created by Jacek on 16.04.2018.
 */

public class DeleteITDialog extends AppCompatDialogFragment
{
    private DeleteITDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Usunąć plan?")
                .setMessage("Plan zniknie z listy")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        listener.onPositiveClick(getArguments().getInt("recyclerPosition"));
                    }
                })
                .setNegativeButton("Nie", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

        return builder.create();
    }

    public interface DeleteITDialogListener
    {
        void onPositiveClick(int position);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (DeleteITDialogListener)context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+" must implement DeleteITDialogListener");
        }

    }
}
