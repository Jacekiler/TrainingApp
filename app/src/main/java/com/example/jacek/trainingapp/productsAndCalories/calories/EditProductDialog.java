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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;

/**
 * Created by Jacek on 21.03.2018.
 */

public class EditProductDialog extends AppCompatDialogFragment
{
    private EditText editName;
    private EditText editCalories;
    private EditProductDialogListener listener;
    private Spinner editCategory;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_product, null);
        builder.setView(view)
                .setTitle("Edycja produktu")
                .setPositiveButton("Zmień", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(!TextUtils.isEmpty(editName.getText()) && !TextUtils.isEmpty(editCalories.getText()))
                        {
                            Product product = new Product(editName.getText().toString(), Integer.parseInt(editCalories.getText().toString()), editCategory.getSelectedItem().toString());
                            listener.onUpdateProductClick(getArguments().getInt(Utils.POSITION), product);
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
        editName =  view.findViewById(R.id.nameEditTextEditDialog);
        editCalories = view.findViewById(R.id.caloriesEditTextEditDialog);
        editName.setText(getArguments().getString("productName"));
        editCalories.setText(String.valueOf(getArguments().getInt("productCalories")));
        editCategory = view.findViewById(R.id.categorySpinnerEditDialog);


        int n = editCategory.getAdapter().getCount();
        int a = 0;
        int i = 0;
        boolean found = false;
        while(!found && a<n)
        {
            if(editCategory.getItemAtPosition(i).equals(getArguments().getString("productCategory")))
            {
                a = i;
                found = true;
            }
            i++;
        }


        editCategory.setSelection(a);

        return builder.create();
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        try
        {
            listener = (EditProductDialogListener) context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + " must implement EditProductDialogListener");
        }
    }

    public interface EditProductDialogListener
    {
        void onUpdateProductClick(int position, Product product);
    }
}
