package com.example.jacek.trainingapp.settings;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;

public class SetStartActivityDialog extends AppCompatDialogFragment
{

    private RadioGroup radioGroup;
    private RadioButton chosenButton;
    private SharedPreferences sharedPreferences;
    private SetStartActivityDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_set_start_activity, null);
        builder.setView(view)
                .setTitle("Wybierz ekran startowy")
                .setPositiveButton("Zapisz", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        chosenButton = view.findViewById(radioGroup.getCheckedRadioButtonId());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("startActivity",chosenButton.getText().toString());
                        editor.apply();
                        listener.onSetStartActivity();
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        radioGroup = view.findViewById(R.id.chooseStartActivityRadioGroup);

        for(int i=0; i<radioGroup.getChildCount(); i++)
        {
            if(((RadioButton)radioGroup.getChildAt(i)).getText().toString().equals(sharedPreferences.getString("startActivity", Utils.STOPWATCH_ACTIVITY_NAME)))
            {
                ((RadioButton) radioGroup.getChildAt(i)).setChecked(true);
            }
        }



        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (SetStartActivityDialogListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement SetStartActivityDialogListener");
        }
    }

    public interface SetStartActivityDialogListener
    {
        void onSetStartActivity();
    }


}