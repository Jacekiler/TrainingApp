package com.example.jacek.trainingapp.physicalActivity.timetable;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.physicalActivity.PhysicalActivityData;

import java.util.ArrayList;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.TrainingViewHolder>
{
    private Context context;
    private ArrayList<Training> trainings;
    private PhysicalActivityData physicalActivityData;
    private String date;

    public TimetableAdapter(Context context, PhysicalActivityData physicalActivityData, String date)
    {
        this.context = context;
        this.physicalActivityData = physicalActivityData;
        trainings = physicalActivityData.getAllTrainings(date);
        this.date = date;
    }

    @NonNull
    @Override
    public TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_training, parent, false);
        return new TrainingViewHolder(view);
    }

    @Override
    public int getItemCount()
    {
        return trainings.size();
    }

    @Override
    public void onBindViewHolder(@NonNull TrainingViewHolder holder, int position)
    {
        holder.nr.setText(String.valueOf(position+1)+".");
        holder.description.setText(trainings.get(position).getDescription());
    }

    public void add(String date, String description)
    {
        physicalActivityData.addTraining(date, description);
        update(date);
    }

    public void update(String date)
    {
        trainings = physicalActivityData.getAllTrainings(date);
        notifyDataSetChanged();
    }

    public void edit(int position, String description)
    {
        trainings.set(position, new Training(trainings.get(position).getId(),description));
        physicalActivityData.editTraining(trainings.get(position));
        notifyItemChanged(position);

//        notesData.edit(notes.get(position), updated);
//        notes.set(position, updated);
//        Collections.sort(notes);
//        notifyDataSetChanged();
    }

    // DONE
    public void delete(int position)
    {
//        physicalActivityData.deleteTraining(trainings.get(position).getId());

        physicalActivityData.deleteTraining(date, trainings.get(position).getId());
        trainings.remove(position);
        notifyItemRemoved(position);
    }



    public class TrainingViewHolder extends RecyclerView.ViewHolder
    {
        private TextView nr;
        private TextView description;

        public TrainingViewHolder(View itemView)
        {
            super(itemView);
            nr = itemView.findViewById(R.id.nrRowTraining);
            description = itemView.findViewById(R.id.descriptionRowTraining);

            itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    Utils.showPopupDelete(context, v, getAdapterPosition());
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    openTrainingViewDialog();
                }
            });
        }

        public void openTrainingViewDialog()
        {
            TrainingViewDialog trainingViewDialog = new TrainingViewDialog();
            Bundle args = new Bundle();
            args.putInt(Utils.POSITION, getAdapterPosition());
            args.putString("description", description.getText().toString());
            trainingViewDialog.setArguments(args);
            trainingViewDialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"training view dialog");
        }



    }
}
