package com.example.jacek.trainingapp.physicalActivity.workouts;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

import com.example.jacek.trainingapp.physicalActivity.exercises.Exercise;

public class AddExerciseToWorkoutDialog extends AppCompatDialogFragment
{
    private AddExerciseToWorkoutDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Dodać ćwiczenie ?")
                .setMessage("Ćwiczenie zostanie dodane do zestawu")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Bundle exerciseBundle = getArguments().getBundle("exerciseBundle");
                        Exercise exercise = new Exercise(exerciseBundle.getString("name"), exerciseBundle.getString("category"),
                                exerciseBundle.getString("description"));
                        listener.onAddExerciseToWorkoutClick(getArguments().getString("workout"),exercise);
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
        void onAddExerciseToWorkoutClick(String workout, Exercise exercise);
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