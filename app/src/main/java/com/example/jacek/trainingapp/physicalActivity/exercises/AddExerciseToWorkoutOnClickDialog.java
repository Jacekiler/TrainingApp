package com.example.jacek.trainingapp.physicalActivity.exercises;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

import com.example.jacek.trainingapp.common.Utils;


public class AddExerciseToWorkoutOnClickDialog extends AppCompatDialogFragment
{
    private AddExerciseToWorkoutDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Dodać ćwiczenie " + getArguments().getString("name") + "?")
                .setMessage("Ćwiczenie zostanie dodane do zestawu")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        listener.onAddExerciseToWorkoutClick(getArguments().getInt(Utils.POSITION), getArguments().getString("type"));
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

    public interface AddExerciseToWorkoutDialogListener
    {
        void onAddExerciseToWorkoutClick(int position, String type);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (AddExerciseToWorkoutDialogListener)context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+" must implement AddExerciseToWorkoutDialogListener");
        }

    }

}