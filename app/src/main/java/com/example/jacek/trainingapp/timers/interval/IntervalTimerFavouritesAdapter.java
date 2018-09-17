package com.example.jacek.trainingapp.timers.interval;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicNoNavViewActivity;
import com.example.jacek.trainingapp.common.Utils;

import java.util.ArrayList;

/**
 * Created by Jacek on 15.04.2018.
 */

public class IntervalTimerFavouritesAdapter extends RecyclerView.Adapter<IntervalTimerFavouritesAdapter.ITfavouritesViewHolder>
{
    private Context context;
    private ArrayList<IntervalTimerCard> cardsList;
    private IntervalTimerData cardsData;

    public IntervalTimerFavouritesAdapter(Context context, IntervalTimerData cardsData)
    {
        this.context = context;
        this.cardsData = cardsData;
        cardsList = this.cardsData.getAll();
    }

    @Override
    public ITfavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_view_interval_timer, parent, false);
        return new ITfavouritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ITfavouritesViewHolder holder, int position)
    {
        holder.name.setText(cardsList.get(position).getName());
        holder.work.setText(String.format("Praca: %d",cardsList.get(position).getWork()));
        holder.rest.setText(String.format("Przerwa: %d",cardsList.get(position).getRest()));
        holder.exercises.setText(String.format("Ćwiczenia: %d",cardsList.get(position).getExercises()));
        holder.sets.setText(String.format("Serie: %d",cardsList.get(position).getSets()));
    }

    @Override
    public int getItemCount()
    {
        return cardsList.size();
    }

    public void delete(int position)
    {
        cardsData.delete(cardsList.get(position));
        cardsList.remove(position);
        notifyItemRemoved(position);
    }

    public void edit(int position, IntervalTimerCard card)
    {
        cardsData.edit(cardsList.get(position), card);
        cardsList.set(position, card);
        notifyItemChanged(position);
    }

    public class ITfavouritesViewHolder extends RecyclerView.ViewHolder //implements View.OnLongClickListener
    {
        private TextView name;
        private TextView work;
        private TextView rest;
        private TextView exercises;
        private TextView sets;
        private Button delete;
        private Button edit;
        private Button preview;


        public ITfavouritesViewHolder(View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.cardViewITName);
            work = itemView.findViewById(R.id.cardViewITWork);
            rest = itemView.findViewById(R.id.cardViewITRest);
            exercises = itemView.findViewById(R.id.cardViewITExercises);
            sets = itemView.findViewById(R.id.cardViewITSets);
            delete = itemView.findViewById(R.id.cardViewITDelete);
            edit = itemView.findViewById(R.id.cardViewITEdit);
            preview = itemView.findViewById(R.id.cardViewITPreview);

            delete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Utils.openDeleteConfirmDialog(context,getAdapterPosition());
                }
            });

            edit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    openEditDialog(getAdapterPosition());
                }
            });

            preview.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    openPreviewDialog(getAdapterPosition());
                }
            });

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    IntervalTimerCard card = cardsList.get(getAdapterPosition());
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("prepare",card.getPrepare());
                    editor.putInt("work",card.getWork());
                    editor.putInt("rest",card.getRest());
                    editor.putInt("exercises",card.getExercises());
                    editor.putInt("sets",card.getSets());
                    editor.putInt("setsRest",card.getSetsRest());
                    editor.putInt("cooling",card.getCooling());
                    editor.apply();

                    if(((BasicNoNavViewActivity)context).isColorChanged())
                    {
                        ((BasicNoNavViewActivity) context).setResult(Activity.RESULT_OK);
                        ((BasicNoNavViewActivity) context).setColorChanged(false);
                    }
                    ((BasicNoNavViewActivity)context).finish();
                }
            });

        }


        public void openEditDialog(int position)
        {
            EditITDialog dialog = new EditITDialog();
            Bundle args = new Bundle();

            args.putInt(Utils.POSITION,position);
            args.putInt("id",cardsList.get(position).getId());
            args.putString("name",cardsList.get(position).getName());
            args.putInt("prepare",cardsList.get(position).getPrepare());
            args.putInt("work",cardsList.get(position).getWork());
            args.putInt("rest",cardsList.get(position).getRest());
            args.putInt("exercises",cardsList.get(position).getExercises());
            args.putInt("sets",cardsList.get(position).getSets());
            args.putInt("setsRest",cardsList.get(position).getSetsRest());
            args.putInt("cooling",cardsList.get(position).getCooling());

            dialog.setArguments(args);
            dialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"edit IT card");
        }

        public void openPreviewDialog(int position)
        {
            PreviewIntervalTimerDialog dialog = new PreviewIntervalTimerDialog();
            Bundle args = new Bundle();

            IntervalTimerCard card = cardsList.get(position);
            int[] array = {card.getPrepare(), card.getWork(), card.getRest(), card.getExercises(), card.getSets(), card.getSetsRest(), card.getCooling()};
            args.putIntArray("IT_values", array);
            dialog.setArguments(args);
            dialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"preview IT card");
        }

    }




}
