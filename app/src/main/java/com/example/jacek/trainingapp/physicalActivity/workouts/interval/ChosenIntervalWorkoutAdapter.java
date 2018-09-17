package com.example.jacek.trainingapp.physicalActivity.workouts.interval;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.physicalActivity.PhysicalActivityData;
import com.example.jacek.trainingapp.interfaces.itemTouchHelper.ItemTouchHelperInterface;

import java.util.ArrayList;
import java.util.Collections;

public class ChosenIntervalWorkoutAdapter extends RecyclerView.Adapter<ChosenIntervalWorkoutAdapter.ChosenIntervalViewHolder>
    implements ItemTouchHelperInterface
{
    private Context context;
    private ArrayList<IntervalWorkoutExercise> exercises;
    private ArrayList<Integer> ids;
    private PhysicalActivityData physicalActivityData;
    private String workoutName;

    public ChosenIntervalWorkoutAdapter(Context context, PhysicalActivityData physicalActivityData, String workoutName)
    {
        this.context = context;
        this.physicalActivityData = physicalActivityData;
        this.workoutName = workoutName;
        exercises = physicalActivityData.getAllIntervalWorkoutExercises(workoutName);

        ids = new ArrayList<>();
        for(IntervalWorkoutExercise e:exercises)
        {
            ids.add(Integer.valueOf(e.getId()));
        }
    }

    @NonNull
    @Override
    public ChosenIntervalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_exercises, parent, false);
        return new ChosenIntervalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChosenIntervalViewHolder holder, int position)
    {
        holder.name.setText(exercises.get(position).getExercise().getName());
    }

    @Override
    public int getItemCount()
    {
        return exercises.size();
    }

    public void update()
    {
        exercises = physicalActivityData.getAllIntervalWorkoutExercises(workoutName);
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition)
    {
//        if(fromPosition < toPosition)
//        {
//            for(int i=fromPosition; i<toPosition; i++)
//            {
//                Collections.swap(exercises, i, i+1);
//            }
//        }
//        else
//        {
//            for(int i=fromPosition; i>toPosition; i--)
//            {
//                Collections.swap(exercises,i, i-1);
//            }
//        }

        Collections.swap(exercises,fromPosition,toPosition);

        notifyItemMoved(fromPosition, toPosition);
        updatedMovedItems();
    }

    public void updatedMovedItems()
    {
        for(int i=0; i<getItemCount(); i++)
        {
            physicalActivityData.editIntervalWorkoutExercise(ids.get(i),exercises.get(i).getExercise().getName());
        }
    }

    @Override
    public void onItemDisMiss(int position)
    {
        physicalActivityData.deleteExerciseFromIntervalWorkout(exercises.get(position).getId());
        exercises.remove(position);
        notifyItemRemoved(position);
    }


    public void delete(int position)
    {
        physicalActivityData.deleteExerciseFromIntervalWorkout(exercises.get(position).getId());
        exercises.remove(position);
        notifyItemRemoved(position);

    }

    public String[] getExercisesNames()
    {
        String[] names = new String[getItemCount()];
        for(int i=0; i<getItemCount(); i++)
        {
            names[i] = exercises.get(i).getExercise().getName();
        }

        return names;
    }

    public String getWorkoutAsTraining()
    {
        String training="Interwały:\n";
        StringBuilder sb = new StringBuilder(training);

        for(IntervalWorkoutExercise e:exercises)
        {
            sb.append(e.getExercise().getName() + "\n");
        }

        return sb.toString();
    }

    public class ChosenIntervalViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name;

        public ChosenIntervalViewHolder(final View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.exerciseNameTextViewRow);

//            itemView.setOnLongClickListener(new View.OnLongClickListener()
//            {
//                @Override
//                public boolean onLongClick(View v)
//                {
//                    Utilities.showPopupDelete(context,v,getAdapterPosition());
//                    return false;
//                }
//            });


        }




    }
}
