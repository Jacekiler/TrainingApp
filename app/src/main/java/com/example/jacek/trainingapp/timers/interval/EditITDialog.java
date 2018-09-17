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
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;

/**
 * Created by Jacek on 17.04.2018.
 */

public class EditITDialog extends AppCompatDialogFragment
{
    private EditText editName;
    private EditText editPrepare;
    private EditText editWork;
    private EditText editRest;
    private EditText editExercises;
    private EditText editSets;
    private EditText editSetsRest;
    private EditText editCooling;
    private EditITDialogListener listener;

    private LinearLayout root;
    private LinearLayout childWithEditTexts;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_interval_timer, null);
        builder.setView(view)
                .setTitle("Edycja planu")
                .setPositiveButton("Zmień", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                        if (TextUtils.isEmpty(editPrepare.getText()))
                        {
                            editPrepare.setText(String.valueOf(IntervalTimerFragment.PREPARE_REST_SETSREST_COOLING_MIN_VALUE));
                        }
                        if (TextUtils.isEmpty(editWork.getText()) || Integer.parseInt(editWork.getText().toString())< IntervalTimerFragment.WORK_EXERCISES_SETS_MIN_VALUE)
                        {
                            editWork.setText(String.valueOf(IntervalTimerFragment.WORK_EXERCISES_SETS_MIN_VALUE));
                        }
                        if (TextUtils.isEmpty(editRest.getText()))
                        {
                            editRest.setText(String.valueOf(IntervalTimerFragment.PREPARE_REST_SETSREST_COOLING_MIN_VALUE));
                        }
                        if (TextUtils.isEmpty(editExercises.getText()) || Integer.parseInt(editExercises.getText().toString())< IntervalTimerFragment.WORK_EXERCISES_SETS_MIN_VALUE)
                        {
                            editExercises.setText(String.valueOf(IntervalTimerFragment.WORK_EXERCISES_SETS_MIN_VALUE));
                        }
                        if (TextUtils.isEmpty(editSets.getText()) || Integer.parseInt(editSets.getText().toString())< IntervalTimerFragment.WORK_EXERCISES_SETS_MIN_VALUE)
                        {
                            editSets.setText(String.valueOf(IntervalTimerFragment.WORK_EXERCISES_SETS_MIN_VALUE));
                        }
                        if (TextUtils.isEmpty(editSetsRest.getText()))
                        {
                            editSetsRest.setText(String.valueOf(IntervalTimerFragment.PREPARE_REST_SETSREST_COOLING_MIN_VALUE));
                        }
                        if (TextUtils.isEmpty(editCooling.getText()))
                        {
                            editCooling.setText(String.valueOf(IntervalTimerFragment.PREPARE_REST_SETSREST_COOLING_MIN_VALUE));
                        }

                        IntervalTimerCard card = new IntervalTimerCard(getArguments().getInt("id"),editName.getText().toString(), Integer.parseInt(editPrepare.getText().toString()), Integer.parseInt(editWork.getText().toString()),
                                Integer.parseInt(editRest.getText().toString()), Integer.parseInt(editExercises.getText().toString()), Integer.parseInt(editSets.getText().toString()),
                                Integer.parseInt(editSetsRest.getText().toString()), Integer.parseInt(editCooling.getText().toString()));
                        listener.onUpdateITClick(getArguments().getInt(Utils.POSITION), card);
                    }

                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
        editName =  view.findViewById(R.id.editNameIT);
        editPrepare = view.findViewById(R.id.editPrepareIT);
        editWork = view.findViewById(R.id.editWorkIT);
        editRest = view.findViewById(R.id.editRestIT);
        editExercises = view.findViewById(R.id.editExercisesIT);
        editSets = view.findViewById(R.id.editSetsIT);
        editSetsRest = view.findViewById(R.id.editSetsRestIT);
        editCooling = view.findViewById(R.id.editCoolingIT);
        root = view.findViewById(R.id.dialogEditIntervalTimerRoot);
        childWithEditTexts = view.findViewById(R.id.dialogEditIntervalTimerChildWithEditTexts);

        editName.setText(getArguments().getString("name"));
        editPrepare.setText(String.valueOf(getArguments().getInt("prepare")));
        editWork.setText(String.valueOf(getArguments().getInt("work")));
        editRest.setText(String.valueOf(getArguments().getInt("rest")));
        editExercises.setText(String.valueOf(getArguments().getInt("exercises")));
        editSets.setText(String.valueOf(getArguments().getInt("sets")));
        editSetsRest.setText(String.valueOf(getArguments().getInt("setsRest")));
        editCooling.setText(String.valueOf(getArguments().getInt("cooling")));

        Utils.hideKeyboardOnFocusChange(childWithEditTexts,getContext());

        root.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(hasFocus)
                {
                    clearFocusOnEditTexts();
                }
            }
        });
        return builder.create();
    }

    public void clearFocusOnEditTexts()
    {
        if(editPrepare.hasFocus() || editWork.hasFocus() || editRest.hasFocus() || editExercises.hasFocus() || editSets.hasFocus() ||
                editSetsRest.hasFocus() || editCooling.hasFocus())
        {
            root.requestFocus();
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (EditITDialogListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement EditITDialogListener");
        }
    }

    public interface EditITDialogListener
    {
        void onUpdateITClick(int position, IntervalTimerCard card);
    }
}