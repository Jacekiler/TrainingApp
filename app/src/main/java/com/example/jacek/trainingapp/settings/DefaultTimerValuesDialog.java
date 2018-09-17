package com.example.jacek.trainingapp.settings;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class DefaultTimerValuesDialog extends AppCompatDialogFragment
{
    private EditText editPrepare;
    private EditText editWork;
    private EditText editRest;
    private EditText editExercises;
    private EditText editSets;
    private EditText editSetsRest;
    private EditText editCooling;

    private SharedPreferences sharedPreferences;
    private EditDefaultTimerValuesDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_settings_default_timer_values, null);
        builder.setView(view)
                .setTitle("Wartości domyślne")
                .setPositiveButton("Zapisz", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(anyFieldEmpty())
                        {
                            Toast.makeText(getContext(), "Żadne pole nie może być puste", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("defaultPrepare", Integer.parseInt(editPrepare.getText().toString()));
                            editor.putInt("defaultWork", Integer.parseInt(editWork.getText().toString()));
                            editor.putInt("defaultRest", Integer.parseInt(editRest.getText().toString()));
                            editor.putInt("defaultExercises", Integer.parseInt(editExercises.getText().toString()));
                            editor.putInt("defaultSets", Integer.parseInt(editSets.getText().toString()));
                            editor.putInt("defaultSetsRest", Integer.parseInt(editSetsRest.getText().toString()));
                            editor.putInt("defaultCooling", Integer.parseInt(editCooling.getText().toString()));
                            editor.apply();
                            listener.onUpdateDefaultTimerValuesClick();
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
        editPrepare = view.findViewById(R.id.editDefaultPrepareDialog);
        editWork = view.findViewById(R.id.editDefaultWorkDialog);
        editRest = view.findViewById(R.id.editDefaultRestDialog);
        editExercises = view.findViewById(R.id.editDefaultExercisesDialog);
        editSets = view.findViewById(R.id.editDefaultSetsDialog);
        editSetsRest = view.findViewById(R.id.editDefaultSetsRestDialog);
        editCooling = view.findViewById(R.id.editDefaultCoolingDialog);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editPrepare.setText(String.valueOf(sharedPreferences.getInt("defaultPrepare", Utils.DEFAULT_PREPARE_VALUE)));
        editWork.setText(String.valueOf(sharedPreferences.getInt("defaultWork", Utils.DEFAULT_WORK_VALUE)));
        editRest.setText(String.valueOf(sharedPreferences.getInt("defaultRest", Utils.DEFAULT_REST_VALUE)));
        editExercises.setText(String.valueOf(sharedPreferences.getInt("defaultExercises", Utils.DEFAULT_EXERCISES_VALUE)));
        editSets.setText(String.valueOf(sharedPreferences.getInt("defaultSets", Utils.DEFAULT_SETS_VALUE)));
        editSetsRest.setText(String.valueOf(sharedPreferences.getInt("defaultSetsRest", Utils.DEFAULT_SETS_REST_VALUE)));
        editCooling.setText(String.valueOf(sharedPreferences.getInt("defaultCooling", Utils.DEFAULT_COOLING_VALUE)));

        Utils.hideKeyboardOnFocusChange((ViewGroup)view,getContext());

        return builder.create();
    }

    public boolean anyFieldEmpty()
    {
        if(TextUtils.isEmpty(editPrepare.getText()) || TextUtils.isEmpty(editWork.getText()) || TextUtils.isEmpty(editRest.getText()) ||
                TextUtils.isEmpty(editExercises.getText()) || TextUtils.isEmpty(editSets.getText()) || TextUtils.isEmpty(editSetsRest.getText()) ||
                TextUtils.isEmpty(editCooling.getText()))
        {
            return true;
        }
        return false;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (EditDefaultTimerValuesDialogListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement EditDefaultTimerValuesDialogListener");
        }
    }

    public interface EditDefaultTimerValuesDialogListener
    {
        void onUpdateDefaultTimerValuesClick();
    }

}