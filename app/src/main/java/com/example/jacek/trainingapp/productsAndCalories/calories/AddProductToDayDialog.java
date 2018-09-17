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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.productsAndCalories.dailyCalories.DailyCaloriesDetails;

import java.util.Date;

import static com.example.jacek.trainingapp.common.Utils.DAY_IN_MILLIS;

public class AddProductToDayDialog extends AppCompatDialogFragment
{
    private TextView dateTextView;
    private Button nextDay;
    private Button previousDay;
    private TextView productName;
    private EditText productAmount;
    private Date date;

    private AddProductToDayDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_product_to_day, null);
        builder.setView(view)
                .setTitle("Dodawanie produktu do dnia")
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(!TextUtils.isEmpty(productAmount.getText()))
                        {
                            int amount = Integer.parseInt(productAmount.getText().toString());
                            int calculatedCalories = (int) Math.round(amount / 100.0 * getArguments().getInt("productCalories"));

                            DailyCaloriesDetails dailyCaloriesDetails = new DailyCaloriesDetails(dateTextView.getText().toString(), productName.getText().toString(),
                                    amount, calculatedCalories);
                            listener.onAddProductToDayClick(dailyCaloriesDetails);
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

        dateTextView = view.findViewById(R.id.dateAddToDayDialog);
        nextDay = view.findViewById(R.id.nextDayAddToDayDialog);
        previousDay = view.findViewById(R.id.previousDayAddToDayDialog);
        productName = view.findViewById(R.id.nameAddToDayDialog);
        productAmount = view.findViewById(R.id.amountAddToDayDialog);
        productName.setText(getArguments().getString("productName"));

        date = new Date();
        dateTextView.setText(Utils.shortFormatDate.format(date));

        nextDay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!previousDay.isEnabled())
                {
                    previousDay.setEnabled(true);
                    dateTextView.setText(Utils.shortFormatDate.format(date));
                }
                else
                {
                    nextDay.setEnabled(false);
                    dateTextView.setText(Utils.shortFormatDate.format(date.getTime() + DAY_IN_MILLIS));
                }
            }
        });

        previousDay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!nextDay.isEnabled())
                {
                    nextDay.setEnabled(true);
                    dateTextView.setText(Utils.shortFormatDate.format(date));
                }
                else
                {
                    previousDay.setEnabled(false);
//                    date = new Date(date.getTime() - DAY_IN_MILLIS);
                    dateTextView.setText(Utils.shortFormatDate.format(date.getTime() - DAY_IN_MILLIS));
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
            listener = (AddProductToDayDialogListener)context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement AddProductToDayDialogListener");
        }
    }

    public interface AddProductToDayDialogListener
    {
        void onAddProductToDayClick(DailyCaloriesDetails dailyCaloriesDetails);
    }
}
