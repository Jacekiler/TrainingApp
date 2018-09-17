package com.example.jacek.trainingapp.physicalActivity.exercises;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.physicalActivity.PhysicalActivityData;
import com.example.jacek.trainingapp.physicalActivity.workouts.WorkoutsActivity;

import java.util.ArrayList;
import java.util.Collections;

public class SpecifiedExercisesAdapter extends RecyclerView.Adapter<SpecifiedExercisesAdapter.MyHolder>
{
    private Context context;
    private ArrayList<Exercise> exercises;
    private PhysicalActivityData physicalActivityData;
    private String type;
    private String workoutName;


    public SpecifiedExercisesAdapter(Context context, PhysicalActivityData physicalActivityData, String category, String type, String workoutName)
    {
        this.context = context;
        this.physicalActivityData = physicalActivityData;
        exercises = physicalActivityData.getAllSpecifiedExercises(category);
        this.type = type;
        this.workoutName = workoutName;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_exercises, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position)
    {
        holder.exerciseName.setText(exercises.get(position).getName());
    }

    @Override
    public int getItemCount()
    {
        return exercises.size();
    }

    public Exercise getItemAt(int position)
    {
        return exercises.get(position);
    }

    public void add(Exercise exercise)
    {
        try
        {
            physicalActivityData.addExercise(exercise);
            exercises.add(exercise);
            Collections.sort(exercises);
            notifyDataSetChanged();
        }
        catch (SQLiteConstraintException e)
        {
            Toast.makeText(context,"Takie ćwiczenie już istnieje",Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(int position)
    {
        physicalActivityData.deleteExercise(exercises.get(position));
        exercises.remove(position);
        notifyItemRemoved(position);
    }

    public void edit(int position, Exercise exercise)
    {
        physicalActivityData.editExercise(exercises.get(position), exercise);
        exercises.set(position,exercise);
        Collections.sort(exercises);
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder
    {
        TextView exerciseName;

        public MyHolder(View itemView)
        {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseNameTextViewRow);

            if(type.equals("addingToIntervalWorkout"))
            {
                itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        openAddExerciseToWorkoutDialog();

                    }
                });
            }
            else if(type.equals("addingToSimpleWorkout"))
            {
                itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        openAddExerciseToWorkoutDialog();
                    }
                });
            }
            else
            {
                itemView.setOnLongClickListener(new View.OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(View v)
                    {
                        showPopup(v);
                        return false;
                    }
                });
            }
        }

        public void openAddExerciseToWorkoutDialog()
        {
            AddExerciseToWorkoutOnClickDialog addExerciseToWorkoutOnClickDialog = new AddExerciseToWorkoutOnClickDialog();
            Bundle args = new Bundle();
            args.putInt(Utils.POSITION,getAdapterPosition());
            args.putString("name",exerciseName.getText().toString());
            args.putString("type",type);
            addExerciseToWorkoutOnClickDialog.setArguments(args);
            addExerciseToWorkoutOnClickDialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"add exercise to workout dialog");
        }

        public void showPopup(View view)
        {
            PopupMenu popupMenu = new PopupMenu(context, view);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
            {
                @Override
                public boolean onMenuItemClick(MenuItem item)
                {
                    switch (item.getItemId())
                    {
                        case R.id.editExercise:
                        {
                            openEditExerciseDialog(getAdapterPosition());
                            return true;
                        }
                        case R.id.deleteExercise:
                        {
                            Utils.openDeleteConfirmDialog(context, getAdapterPosition());
                            return true;
                        }
                        case R.id.addToWorkout:
                        {
                            openAddToWorkoutDialog(getAdapterPosition());
                            return true;
                        }
                        default:
                        {
                            return false;
                        }
                    }
                }
            });

            popupMenu.inflate(R.menu.exercises_popup_menu);
            popupMenu.show();

        }

        public void openEditExerciseDialog(int position)
        {
            EditExerciseDialog editExerciseDialog = new EditExerciseDialog();
            Bundle args = new Bundle();
            args.putInt(Utils.POSITION,position);
            args.putString("name",exercises.get(position).getName());
            args.putString("category",exercises.get(position).getCategory());
            args.putString("description",exercises.get(position).getDescription());
            editExerciseDialog.setArguments(args);
            editExerciseDialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "edit exercise dialog");
        }

        public void openAddToWorkoutDialog(int position)
        {
            Intent intent = new Intent(context, WorkoutsActivity.class);
            intent.putExtra("type","addingExerciseToWorkout");
            Bundle args = new Bundle();
            args.putString("name", exercises.get(position).getName());
            args.putString("category", exercises.get(position).getCategory());
            args.putString("description", exercises.get(position).getDescription());
            intent.putExtra("exerciseBundle", args);
            context.startActivity(intent);
        }

    }
}
