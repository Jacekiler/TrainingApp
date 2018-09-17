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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;

public class AppColorDialog extends AppCompatDialogFragment
{
    private static final String BLUE_COLOR = "blue";
    private static final String GREEN_COLOR = "green";
    private static final String WHITE_COLOR = "white";

    private Button blueColor;
    private Button greenColor;
    private Button whiteColor;
    private RadioButton blueRadioButton;
    private RadioButton greenRadioButton;
    private RadioButton whiteRadioButton;

    private SharedPreferences sharedPreferences;
    private ChangeAppColorDialogListener listener;

    private String color;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_settings_app_color, null);
        builder.setView(view)
                .setTitle("Wybierz kolor")
                .setPositiveButton("Zapisz", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Utils.APP_COLOR, color);
                        editor.apply();
                        dismiss();
                        listener.onChangeColor();
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

        blueColor = view.findViewById(R.id.blueColorButton);
        greenColor = view.findViewById(R.id.greenColorButton);
        whiteColor = view.findViewById(R.id.whiteColorButton);
        blueRadioButton = view.findViewById(R.id.blueRadioButton);
        greenRadioButton = view.findViewById(R.id.greenRadioButton);
        whiteRadioButton = view.findViewById(R.id.whiteRadioButton);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        switch(sharedPreferences.getString(Utils.APP_COLOR,BLUE_COLOR))
        {
            case GREEN_COLOR:
            {
                greenRadioButton.setChecked(true);
                blueRadioButton.setChecked(false);
                whiteRadioButton.setChecked(false);
                break;
            }
            case BLUE_COLOR:
            {
                blueRadioButton.setChecked(true);
                greenRadioButton.setChecked(false);
                whiteRadioButton.setChecked(false);
                break;
            }
            case WHITE_COLOR:
            {
                whiteRadioButton.setChecked(true);
                blueRadioButton.setChecked(false);
                greenRadioButton.setChecked(false);
            }
            default:
            {

                break;
            }
        }

        blueColor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                blueRadioButton.setChecked(true);
            }
        });

        greenColor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                greenRadioButton.setChecked(true);
            }
        });

        whiteColor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                whiteRadioButton.setChecked(true);
            }
        });


        blueRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (buttonView.isChecked())
                {
                    color = BLUE_COLOR;
                    greenRadioButton.setChecked(false);
                    whiteRadioButton.setChecked(false);
                }
            }
        });

        greenRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (buttonView.isChecked())
                {
                    color = GREEN_COLOR;
                    blueRadioButton.setChecked(false);
                    whiteRadioButton.setChecked(false);
                }
            }
        });

        whiteRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (buttonView.isChecked())
                {
                    color = WHITE_COLOR;
                    greenRadioButton.setChecked(false);
                    blueRadioButton.setChecked(false);
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
            listener = (ChangeAppColorDialogListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement ChangeAppColorDialogListener");
        }
    }

    public interface ChangeAppColorDialogListener
    {
        void onChangeColor();
    }

}