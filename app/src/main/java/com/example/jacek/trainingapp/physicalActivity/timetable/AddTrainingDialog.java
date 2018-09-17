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
import android.widget.Toast;

import com.example.jacek.trainingapp.R;

public class AddTrainingDialog extends AppCompatDialogFragment
{
    private EditText addDescription;

    private AddTrainingDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_training, null);
        builder.setView(view)
                .setTitle("Nowy trening")
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(!TextUtils.isEmpty(addDescription.getText()))
                        {
                            listener.onAddTrainingClick(addDescription.getText().toString());
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Puste pole", Toast.LENGTH_SHORT).show();
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

        addDescription = view.findViewById(R.id.trainingDescriptionAddDialog);

        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (AddTrainingDialogListener)context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement AddTrainingDialogListener");
        }
    }

    public interface AddTrainingDialogListener
    {
        void onAddTrainingClick(String description);
    }
}
