package com.example.jacek.trainingapp.timers.interval;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.jacek.trainingapp.R;

import java.util.ArrayList;

/**
 * Created by Jacek on 16.04.2018.
 */

public class PreviewIntervalTimerDialog extends AppCompatDialogFragment
{
    private RecyclerView recycler;
    private PreviewIntervalTimerAdapter adapter;
    private int[] itValuesArray;
    private ArrayList<String> recyclerArrayList;

    private boolean hasPrepare;
    private boolean hasRest;
    private boolean hasSetsRest;
    private boolean hasCooling;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_preview_interval_timer, null);
        builder.setView(view)
                .setTitle("Podgląd")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

        itValuesArray = getArguments().getIntArray("IT_values");


        preparePreviewList();

        recycler = view.findViewById(R.id.previewRecyclerView);
        adapter = new PreviewIntervalTimerAdapter(getContext(),recyclerArrayList);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        return builder.create();
    }

    public void preparePreviewList()
    {
        hasPrepare = itValuesArray[0] > 0;
        hasRest = itValuesArray[2] > 0;
        hasSetsRest = itValuesArray[5] > 0;
        hasCooling = itValuesArray[6] > 0;

        recyclerArrayList = new ArrayList<>();
        int c = 1;

        if(hasPrepare)
        {
            recyclerArrayList.add("0. Przygotowanie: " + itValuesArray[0]);
        }

        for (int i=0; i<itValuesArray[4]; i++)
        {
            if (hasRest)
            {
                for (int j = 0; j < itValuesArray[3]; j++)
                {
                    recyclerArrayList.add(c + ". Praca: " + itValuesArray[1]);
                    c++;
                    recyclerArrayList.add(c + ". Przerwa: " + itValuesArray[2]);
                    c++;
                }
            }
            else
            {
                for (int j = 0; j < itValuesArray[3]; j++)
                {
                    recyclerArrayList.add(c + ". Praca: " + itValuesArray[1]);
                    c++;
                }
            }

            if(hasSetsRest && i < itValuesArray[4] - 1)
            {
                recyclerArrayList.add(c + ". Przerwa między seriami: " + itValuesArray[5]);
                c++;
            }
        }

        if(hasCooling)
        {
            recyclerArrayList.add(c + ". Schłodzenie: " + itValuesArray[6]);
        }
    }

}

