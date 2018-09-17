package com.example.jacek.trainingapp.productsAndCalories.dailyCalories;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.productsAndCalories.calories.CaloriesActivity;

public class AddDetailDialog extends AppCompatDialogFragment
{
    private EditText addName;
    private EditText addCalories;
    private TextView goToProductsList;

    private AddDetailDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_daily_detail, null);
        builder.setView(view)
                .setTitle("Dodawanie produktu")
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(!TextUtils.isEmpty(addCalories.getText()))
                        {
                            DailyCaloriesDetails dailyCaloriesDetails = new DailyCaloriesDetails(getArguments().getString("date"),
                                    addName.getText().toString(), Integer.parseInt(addCalories.getText().toString()));
                            listener.onAddDetailClick(dailyCaloriesDetails);
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

        addName = view.findViewById(R.id.addNameDailyDetailDialog);
        addCalories = view.findViewById(R.id.addCaloriesDailyDetailDialog);
        goToProductsList = view.findViewById(R.id.goToProductsListDailyDetailDialog);

        goToProductsList.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
                Intent calories_intent = new Intent(getContext(),CaloriesActivity.class);
                calories_intent.putExtra("type","addingToDailyCalories");
                calories_intent.putExtra("date",getArguments().getString("date"));
                startActivity(calories_intent);
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
            listener = (AddDetailDialogListener)context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement AddDetailDialogListener");
        }
    }

    public interface AddDetailDialogListener
    {
        void onAddDetailClick(DailyCaloriesDetails dailyCaloriesDetails);
    }
}
