package com.example.jacek.trainingapp.productsAndCalories.calories;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.productsAndCalories.dailyCalories.DailyCaloriesDetails;

public class AddProductToDayOnClickDialog extends AppCompatDialogFragment
{
    private TextView productName;
    private EditText productAmount;
    private AddProductToDayOnClickDialogListener listener;
    private Bundle args;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        args = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_product_to_day_on_click, null);
        builder.setView(view)
                .setTitle("Produkt zostanie dodany do " + args.getString("date"))
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (!TextUtils.isEmpty(productAmount.getText()))
                        {
                            int amount = Integer.parseInt(productAmount.getText().toString());
                            int calculatedCalories = (int) Math.round(amount / 100.0 * args.getInt("productCalories"));

                            DailyCaloriesDetails dailyCaloriesDetails = new DailyCaloriesDetails(args.getString("date"), args.getString("name"),
                                    amount, calculatedCalories);
                            listener.onAdProductToDayOnClickClick(dailyCaloriesDetails);
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Należy podać ilość", Toast.LENGTH_SHORT).show();
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

        productName = view.findViewById(R.id.nameAddToDayOnClickDialog);
        productAmount = view.findViewById(R.id.amountAddToDayOnClickDialog);
        productName.setText(args.getString("name"));

        return builder.create();
    }

    public interface AddProductToDayOnClickDialogListener
    {
        void onAdProductToDayOnClickClick(DailyCaloriesDetails dailyCaloriesDetails);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (AddProductToDayOnClickDialogListener)context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+" must implement AddProductToDayOnClickDialogListener");
        }

    }

}