package com.example.jacek.trainingapp.physicalActivity.workouts.simple;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.physicalActivity.PhysicalActivityData;
import com.example.jacek.trainingapp.physicalActivity.workouts.WorkoutsActivity;

import java.util.ArrayList;

public class SimpleWorkoutsAdapter extends RecyclerView.Adapter<SimpleWorkoutsAdapter.SimpleWorkoutViewHolder>
{
    private Context context;
    private ArrayList<SimpleWorkout> simpleWorkouts;
    private PhysicalActivityData physicalActivityData;
    private String type;
    private Bundle exerciseBundle;

    public SimpleWorkoutsAdapter(Context context, PhysicalActivityData physicalActivityData, String type)
//    public SimpleWorkoutsAdapter(Context context, PhysicalActivityData physicalActivityData)
    {
        this.context = context;
        this.physicalActivityData = physicalActivityData;
        simpleWorkouts = physicalActivityData.getAllSimpleWorkouts();
        this.type = type;
    }

    public SimpleWorkoutsAdapter(Context context, PhysicalActivityData physicalActivityData, Bundle args)
//    public SimpleWorkoutsAdapter(Context context, PhysicalActivityData physicalActivityData)
    {
        this.context = context;
        this.physicalActivityData = physicalActivityData;
        simpleWorkouts = physicalActivityData.getAllSimpleWorkouts();
        type = args.getString("type");
        exerciseBundle = args.getBundle("exerciseBundle");
    }

    @NonNull
    @Override
    public SimpleWorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_workout, parent, false);
        return new SimpleWorkoutsAdapter.SimpleWorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleWorkoutViewHolder holder, int position)
    {
        holder.vhSimpleWorkout = simpleWorkouts.get(position);
        holder.name.setText(simpleWorkouts.get(position).getName());
        for(SimpleWorkoutExercise e:holder.vhSimpleWorkout.getExercises())
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View addView = inflater.inflate(R.layout.row_workout_exercise, null);
            TextView txtValue = addView.findViewById(R.id.intervalExerciseTextViewRow);

            txtValue.setText(e.getExercise().getName());
            holder.simpleExercisesContainer.addView(addView);
        }
    }

    @Override
    public int getItemCount()
    {
        return simpleWorkouts.size();
    }

    public void update()
    {
        simpleWorkouts = physicalActivityData.getAllSimpleWorkouts();
        notifyDataSetChanged();
    }

    public void add(SimpleWorkout simpleWorkout)
    {
        try
        {
            physicalActivityData.addSimpleWorkout(simpleWorkout);
            simpleWorkouts.add(simpleWorkout);
            notifyItemInserted(getItemCount()-1);
        }
        catch (SQLiteConstraintException e)
        {
            Toast.makeText(context,"Taki workout już istnieje",Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(int position)
    {
        physicalActivityData.deleteSimpleWorkout(simpleWorkouts.get(position).getName());
        simpleWorkouts.remove(position);
        notifyItemRemoved(position);
    }


    public class SimpleWorkoutViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout simpleExercisesContainer;
        private SimpleWorkout vhSimpleWorkout;
        private TextView name;

        public SimpleWorkoutViewHolder(View itemView)
        {
            super(itemView);
            simpleExercisesContainer = itemView.findViewById(R.id.exercisesContainer);
            name = itemView.findViewById(R.id.nameWorkoutRow);

            if(type.equals("normal"))
            {
                simpleExercisesContainer.setOnClickListener(enterWorkout);
                itemView.setOnClickListener(enterWorkout);

                simpleExercisesContainer.setOnLongClickListener(showPopup);
                itemView.setOnLongClickListener(showPopup);
            }
            else
            {
                simpleExercisesContainer.setOnClickListener(addingExercise);
                itemView.setOnClickListener(addingExercise);
            }
        }



        View.OnClickListener enterWorkout = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,ChosenSimpleWorkoutActivity.class);
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
                WorkoutsActivity.openAddExerciseToWorkoutDialog(context, simpleWorkouts.get(getAdapterPosition()).getName(), exerciseBundle);

            }
        };


    }
}
