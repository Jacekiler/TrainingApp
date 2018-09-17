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

public class EditExerciseDialog extends AppCompatDialogFragment
{
    private EditText editName;
    private EditText editDescription;
    private EditExerciseDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_exercise, null);
        builder.setView(view)
                .setTitle("Edycja ćwiczenia")
                .setPositiveButton("Zmień", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(!TextUtils.isEmpty(editName.getText()))
                        {
                            Exercise exercise;
                            if(TextUtils.isEmpty(editDescription.getText()))
                            {
                                exercise = new Exercise(editName.getText().toString(), getArguments().getString("category"));
                            }
                            else
                            {
                                exercise = new Exercise(editName.getText().toString(), getArguments().getString("category"), editDescription.getText().toString());
                            }
                            listener.onUpdateExerciseClick(getArguments().getInt(Utils.POSITION), exercise);
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
        editName =  view.findViewById(R.id.nameETEditExerciseDialog);
        editDescription = view.findViewById(R.id.descriptionETEditExerciseDialog);
        editName.setText(getArguments().getString("name"));
        editDescription.setText(getArguments().getString("description"));

        Utils.hideKeyboardOnFocusChange((ViewGroup)view, getContext());

        return builder.create();
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (EditExerciseDialogListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement EditExerciseDialogListener");
        }
    }

    public interface EditExerciseDialogListener
    {
        void onUpdateExerciseClick(int position, Exercise exercise);
    }
}