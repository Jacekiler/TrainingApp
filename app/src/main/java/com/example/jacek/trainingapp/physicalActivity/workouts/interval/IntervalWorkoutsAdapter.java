package com.example.jacek.trainingapp.physicalActivity.workouts.interval;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.physicalActivity.PhysicalActivityData;
import com.example.jacek.trainingapp.physicalActivity.workouts.WorkoutsActivity;

import java.util.ArrayList;

public class IntervalWorkoutsAdapter extends RecyclerView.Adapter<IntervalWorkoutsAdapter.IntervalWorkoutViewHolder>
{
    private Context context;
    private ArrayList<IntervalWorkout> intervalWorkouts;
    private PhysicalActivityData physicalActivityData;
    private String type;
    private Bundle exerciseBundle;

    public IntervalWorkoutsAdapter(Context context, PhysicalActivityData physicalActivityData, String type)
//    public IntervalWorkoutsAdapter(Context context, PhysicalActivityData physicalActivityData)
    {
        this.context = context;
        this.physicalActivityData = physicalActivityData;
        intervalWorkouts = physicalActivityData.getAllIntervalWorkouts();
        this.type = type;
    }

    public IntervalWorkoutsAdapter(Context context, PhysicalActivityData physicalActivityData, Bundle args)
//    public IntervalWorkoutsAdapter(Context context, PhysicalActivityData physicalActivityData)
    {
        this.context = context;
        this.physicalActivityData = physicalActivityData;
        intervalWorkouts = physicalActivityData.getAllIntervalWorkouts();
        type = args.getString("type");
        exerciseBundle = args.getBundle("exerciseBundle");

    }

    @NonNull
    @Override
    public IntervalWorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_workout, parent, false);

        return new IntervalWorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IntervalWorkoutViewHolder holder, int position)
    {
        holder.VHIntervalWorkout = intervalWorkouts.get(position);
        holder.name.setText(intervalWorkouts.get(position).getName());
        for(IntervalWorkoutExercise e:holder.VHIntervalWorkout.getExercises())
        {
             LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             View addView = inflater.inflate(R.layout.row_workout_exercise,null);
             TextView txtValue = addView.findViewById(R.id.intervalExerciseTextViewRow);

            txtValue.setText(e.getExercise().getName());
            holder.exercisesContainer.addView(addView);
        }
    }

    @Override
    public int getItemCount()
    {
        return intervalWorkouts.size();
    }

    public void update()
    {
        intervalWorkouts = physicalActivityData.getAllIntervalWorkouts();
        notifyDataSetChanged();
    }

    public void add(IntervalWorkout intervalWorkout)
    {
        try
        {
            physicalActivityData.addIntervalWorkout(intervalWorkout);
            intervalWorkouts.add(intervalWorkout);
            notifyItemInserted(getItemCount()-1);
        }
        catch (SQLiteConstraintException e)
        {
            Toast.makeText(context,"Taki workout już istnieje",Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(int position)
    {
        physicalActivityData.deleteIntervalWorkout(intervalWorkouts.get(position).getName());
        intervalWorkouts.remove(position);
        notifyItemRemoved(position);
    }

    public class IntervalWorkoutViewHolder extends RecyclerView.ViewHolder
    {

        private LinearLayout exercisesContainer;
        private IntervalWorkout VHIntervalWorkout;
        private TextView name;


        Button b;

        public IntervalWorkoutViewHolder(View itemView)
        {
            super(itemView);

            exercisesContainer = itemView.findViewById(R.id.exercisesContainer);
            name = itemView.findViewById(R.id.nameWorkoutRow);

            if(type.equals("normal"))
            {
                exercisesContainer.setOnClickListener(enterWorkout);
                itemView.setOnClickListener(enterWorkout);

                exercisesContainer.setOnLongClickListener(showPopup);
                itemView.setOnLongClickListener(showPopup);
            }
            else
            {
                exercisesContainer.setOnClickListener(addingExercise);
                itemView.setOnClickListener(addingExercise);
            }


        }



        View.OnClickListener enterWorkout = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,ChosenIntervalWorkoutActivity.class);
                intent.putExtra(Utils.POSITION,getAdapterPosition());
                intent.putExtra("workoutName",name.getText().toString());
                ((AppCompatActivity)context).startActivityForResult(intent, 2);
            }
        };

        View.OnLongClickListener showPopup = new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                Utils.showPopupDelete(context,v,getAdapterPosition());
                return true;
            }
        };

        View.OnClickListener addingExercise = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                WorkoutsActivity.openAddExerciseToWorkoutDialog(context, intervalWorkouts.get(getAdapterPosition()).getName(), exerciseBundle);
            }
        };





    }
}
