package com.example.jacek.trainingapp.physicalActivity.exercises;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;

public class AddExerciseDialog extends AppCompatDialogFragment
{
    private EditText addName;
    private EditText addDescription;

    private AddExerciseDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_exercise, null);
        builder.setView(view)
                .setTitle("Dodawanie ćwiczenia: "+getArguments().getString("category"))
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(!TextUtils.isEmpty(addName.getText()))
                        {
                            Exercise exercise;
                            if(TextUtils.isEmpty(addDescription.getText()))
                            {
                                exercise = new Exercise(addName.getText().toString(), getArguments().getString("category"));
                            }
                            else
                            {
                                exercise = new Exercise(addName.getText().toString(), getArguments().getString("category"), addDescription.getText().toString());
                            }
                            listener.onAddExerciseClick(exercise);

                        }
                        else
                        {
                            Toast.makeText(getContext(), "Nazwa nie może być pusta", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

        addName = view.findViewById(R.id.exerciseNameAddDialog);
        addDescription = view.findViewById(R.id.exerciseDescriptionAddDialog);

        Utils.hideKeyboardOnFocusChange((ViewGroup)view,getContext());

        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (AddExerciseDialogListener)context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement AddExerciseDialogListener");
        }
    }

    public interface AddExerciseDialogListener
    {
        void onAddExerciseClick(Exercise exercise);
    }
}
