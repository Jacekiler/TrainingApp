package com.example.jacek.trainingapp.physicalActivity.workouts;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.jacek.trainingapp.R;

public class AddWorkoutDialog extends AppCompatDialogFragment
{
    private EditText addName;

    private AddWorkoutDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_workout, null);
        builder.setView(view)
                .setTitle("Podaj nazwę")
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        listener.onAddWorkoutClick(addName.getText().toString());
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

        addName = view.findViewById(R.id.nameAddWorkoutDialog);

        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (AddWorkoutDialogListener)context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement AddDialogListener");
        }
    }

    public interface AddWorkoutDialogListener
    {
        void onAddWorkoutClick(String workout);
    }
}
