package com.example.jacek.trainingapp.productsAndCalories.calories;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jacek.trainingapp.R;

/**
 * Created by Jacek on 21.03.2018.
 */

public class AddProductDialog extends AppCompatDialogFragment
{
    private EditText addNames;
    private EditText addCalories;
    private Spinner addCategory;
    private TextView mlUnit;
    private TextView gUnit;
    private TextView selected;

    private AddDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_product, null);
        builder.setView(view)
                .setTitle("Dodawanie produktu")
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(!TextUtils.isEmpty(addNames.getText()) && !TextUtils.isEmpty(addCalories.getText()))
                        {
                            Product product = new Product(addNames.getText().toString(), Integer.parseInt(addCalories.getText().toString()),
                                    addCategory.getSelectedItem().toString());
                            listener.onAddProductClick(product);
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Żadne pole nie może być puste", Toast.LENGTH_SHORT).show();
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

        addNames = view.findViewById(R.id.nameEditTextAddDialog);
        addCalories = view.findViewById(R.id.caloriesEditTextAddDialog);
        addCategory = view.findViewById(R.id.categorySpinnerAddDialog);
        gUnit = view.findViewById(R.id.gTextViewAddDialog);
        mlUnit = view.findViewById(R.id.mlTextViewAddDialog);
        gUnit.setSelected(true);
        mlUnit.setSelected(false);
        selected = gUnit;

        addCategory.setSelection(addCategory.getCount()-1);

        onUnitChange();

        return builder.create();
    }

    public void onUnitChange()
    {
        gUnit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gUnit.setTextColor(Color.BLACK);
                mlUnit.setTextColor(Color.GRAY);
                gUnit.setSelected(true);
                mlUnit.setSelected(false);
                selected = gUnit;
            }
        });

        mlUnit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mlUnit.setTextColor(Color.BLACK);
                gUnit.setTextColor(Color.GRAY);
                mlUnit.setSelected(true);
                gUnit.setSelected(false);
                selected = mlUnit;
            }
        });
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (AddDialogListener)context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement AddDialogListener");
        }
    }

    public interface AddDialogListener
    {
        void onAddProductClick(Product product);
    }
}
