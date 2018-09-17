package com.example.jacek.trainingapp.timers.interval;

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

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;

/**
 * Created by Jacek on 14.04.2018.
 */

public class AddIntervalTimerConfirmDialog extends AppCompatDialogFragment
{
    EditText timerName;
    AddIntervalTimerDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_interval_timer, null);

        builder.setView(view)
                .setTitle("Dodawanie planu")
                .setMessage("podaj nazwę")
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        IntervalTimerCard card;
                        Bundle args = getArguments();
                        if(TextUtils.isEmpty(timerName.getText()))
                        {
                            card = new IntervalTimerCard(args.getInt("prepare"),
                                    args.getInt("work"), args.getInt("rest"), args.getInt("exercises"),
                                    args.getInt("sets"), args.getInt("setsRest"), args.getInt("cooling"));
                        }
                        else
                        {
                            card = new IntervalTimerCard(timerName.getText().toString(), args.getInt("prepare"),
                                    args.getInt("work"), args.getInt("rest"), args.getInt("exercises"),
                                    args.getInt("sets"), args.getInt("setsRest"), args.getInt("cooling"));
                        }
                        listener.onAddIntervalClick(card);
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

        timerName = view.findViewById(R.id.nameEditTextAddIntervalTimerDialog);
        Utils.hideKeyboardOnFocusChange((ViewGroup)view, getContext());
        return builder.create();
    }

    public interface AddIntervalTimerDialogListener
    {
        void onAddIntervalClick(IntervalTimerCard card);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (AddIntervalTimerDialogListener)context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+" must implement AddIntervalTimerDialogListener");
        }

    }

}
