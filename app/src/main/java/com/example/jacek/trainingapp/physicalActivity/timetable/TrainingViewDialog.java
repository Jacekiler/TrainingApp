package com.example.jacek.trainingapp.physicalActivity.timetable;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;

public class TrainingViewDialog extends AppCompatDialogFragment
{

    private EditText description;
    private String currentDescription;
    private TrainingViewDialogListener listener;
    private AlertDialog alert;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_training, null);

        description = view.findViewById(R.id.trainingDescriptionAddDialog);
        currentDescription = getArguments().getString("description");
        description.setText(currentDescription);

        builder.setView(view)
                .setTitle("Podgląd treningu")
                .setPositiveButton("Zapisz", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(!TextUtils.isEmpty(description.getText()))
                        {
                            listener.onSaveTrainingClick(getArguments().getInt(Utils.POSITION), description.getText().toString());
                        }
                        else
                        {
                            listener.onEmptyTrainingSaveClick(getArguments().getInt(Utils.POSITION));
                        }
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {}
                });




        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (TrainingViewDialogListener)context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement TrainingViewDialogListener");
        }
    }

    public interface TrainingViewDialogListener
    {
        void onSaveTrainingClick(int position, String description);
        void onEmptyTrainingSaveClick(int position);
    }
}
