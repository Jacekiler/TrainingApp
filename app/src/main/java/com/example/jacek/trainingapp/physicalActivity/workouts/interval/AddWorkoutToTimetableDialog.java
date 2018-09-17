package com.example.jacek.trainingapp.physicalActivity.workouts.interval;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jacek.trainingapp.R;

import java.text.ParseException;
import java.util.Date;

import static com.example.jacek.trainingapp.common.Utils.DAY_IN_MILLIS;
import static com.example.jacek.trainingapp.common.Utils.shortFormatDate;

public class AddWorkoutToTimetableDialog extends AppCompatDialogFragment
{
    private TextView date;
    private Button previousDate;
    private Button nextDate;

    private AddWorkoutToTimetableDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_workout_to_timetable, null);
        builder.setView(view)
                .setTitle("Dodać to dziennika?")
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        listener.onAddTrainingToTimetableClick(date.getText().toString());
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

        date = view.findViewById(R.id.dateAddWorkoutToTimetableDialog);
        nextDate = view.findViewById(R.id.nextDateAddWorkoutToTimetableDialog);
        previousDate = view.findViewById(R.id.previousDateAddWorkoutToTimetableDialog);
        date.setText(shortFormatDate.format(new Date()));

        previousDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    date.setText(shortFormatDate.format(shortFormatDate.parse(date.getText().toString()).getTime() - DAY_IN_MILLIS));
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        });

        nextDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    date.setText(shortFormatDate.format(shortFormatDate.parse(date.getText().toString()).getTime() + DAY_IN_MILLIS));
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        });


        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (AddWorkoutToTimetableDialogListener)context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement AddWorkoutToTimetableDialogListener");
        }
    }

    public interface AddWorkoutToTimetableDialogListener
    {
        void onAddTrainingToTimetableClick(String date);
    }
}
