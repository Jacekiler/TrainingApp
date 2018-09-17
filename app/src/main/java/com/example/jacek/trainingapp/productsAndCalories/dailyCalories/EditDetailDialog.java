package com.example.jacek.trainingapp.productsAndCalories.dailyCalories;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.productsAndCalories.calories.Product;
import com.example.jacek.trainingapp.productsAndCalories.ProductsAndCaloriesData;

public class EditDetailDialog extends AppCompatDialogFragment
{
    private TextView amountTV;
    private TextView kcals;

    private EditText editName;
    private EditText editAmount;
    private EditText editCalories;
    private EditDetailDialogListener listener;
    private ProductsAndCaloriesData productsAndCaloriesData;
    private DailyCaloriesDetails dailyCaloriesDetails;
    private Bundle args;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_detail, null);
        builder.setView(view)
                .setTitle("Edycja produktu")
                .setPositiveButton("Zmień", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {


                        if(TextUtils.isEmpty(editAmount.getText()))
                        {
                            editAmount.setText("0");
                        }

                        if(TextUtils.isEmpty(editCalories.getText()))
                        {
                            editCalories.setText("0");
                        }

                        DailyCaloriesDetails updatedDailyCaloriesDetails = new DailyCaloriesDetails(
                                dailyCaloriesDetails.getId(),
                                dailyCaloriesDetails.getDate(),
                                editName.getText().toString(),
                                Integer.parseInt(editAmount.getText().toString()),
                                Integer.parseInt(editCalories.getText().toString()));
                        listener.onUpdateDetailClick(args.getInt(Utils.POSITION), updatedDailyCaloriesDetails);

                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

        editName =  view.findViewById(R.id.nameETEditDetailDialog);
        editAmount = view.findViewById(R.id.amountETEditDetailDialog);
        editCalories = view.findViewById(R.id.caloriesETEdidDetailDialog);

        amountTV = view.findViewById(R.id.amountTVEditDetailDialog);
        kcals = view.findViewById(R.id.kcalPerUnitEditDetail);


        productsAndCaloriesData = new ProductsAndCaloriesData(getContext());
        productsAndCaloriesData.open();
        args = getArguments();
        dailyCaloriesDetails = productsAndCaloriesData.getDetail(args.getInt("id"));

        editCalories.setText(String.valueOf(dailyCaloriesDetails.getCalories()));

        if(dailyCaloriesDetails.getAmount()!=0)
        {
            editAmount.setText(String.valueOf(dailyCaloriesDetails.getAmount()));
            editName.setEnabled(false);
            editCalories.setEnabled(false);
            final Product product = productsAndCaloriesData.getProduct(dailyCaloriesDetails.getName());
            editName.setText(dailyCaloriesDetails.getName());
            kcals.setText("(" + product.getCalories() + "kcal/100g)");


            editAmount.addTextChangedListener(new TextWatcher()
            {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after)
                {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count)
                {
                    int calculatedCalories = 0;
                    if(!TextUtils.isEmpty(editAmount.getText()))
                    {
                        int amount = Integer.parseInt(editAmount.getText().toString());
                        calculatedCalories = (int)Math.round(amount / 100.0 * product.getCalories());
                    }

                    editCalories.setText(String.valueOf(calculatedCalories));
                }

                @Override
                public void afterTextChanged(Editable s)
                {

                }
            });
        }
        else
        {
            kcals.setVisibility(View.GONE);
            editName.setText(dailyCaloriesDetails.getName());
            editAmount.setEnabled(false);
            editAmount.setVisibility(View.GONE);
            amountTV.setVisibility(View.GONE);
        }

        Utils.hideKeyboardOnFocusChange((ViewGroup)view, getContext());

        return builder.create();
    }





    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (EditDetailDialogListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement EditDetailDialogListener");
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        productsAndCaloriesData.close();
    }

    public interface EditDetailDialogListener
    {
        void onUpdateDetailClick(int position, DailyCaloriesDetails dailyCaloriesDetails);
    }
}