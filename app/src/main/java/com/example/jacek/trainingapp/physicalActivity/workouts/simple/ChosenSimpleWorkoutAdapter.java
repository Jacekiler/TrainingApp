package com.example.jacek.trainingapp.physicalActivity.workouts.simple;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.interfaces.itemTouchHelper.ItemTouchHelperInterface;
import com.example.jacek.trainingapp.physicalActivity.PhysicalActivityData;

import java.util.ArrayList;
import java.util.Collections;

public class ChosenSimpleWorkoutAdapter extends RecyclerView.Adapter<ChosenSimpleWorkoutAdapter.ChosenSimpleWorkoutViewHolder>
    implements ItemTouchHelperInterface
{
    private Context context;
    private ArrayList<SimpleWorkoutExercise> exercises;
    private PhysicalActivityData physicalActivityData;
    private ArrayList<Integer> ids;
    private String workoutName;
    private boolean repsChanged = false;

    public ChosenSimpleWorkoutAdapter(Context context, PhysicalActivityData physicalActivityData, String workoutName)
    {
        this.context = context;
        this.physicalActivityData = physicalActivityData;
        this.workoutName = workoutName;
        exercises = physicalActivityData.getAllSimpleWorkoutExercises(workoutName);

        ids = new ArrayList<>();
        for(SimpleWorkoutExercise e:exercises)
        {
            ids.add(Integer.valueOf(e.getId()));
        }

    }

    @NonNull
    @Override
    public ChosenSimpleWorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_simple_exercise, parent, false);
        return new ChosenSimpleWorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChosenSimpleWorkoutViewHolder holder, int position)
    {
        holder.name.setText(exercises.get(position).getExercise().getName());
        holder.reps.setText(String.valueOf(exercises.get(position).getReps()));
    }

    @Override
    public int getItemCount()
    {
        return exercises.size();
    }

    public void update()
    {
        exercises = physicalActivityData.getAllSimpleWorkoutExercises(workoutName);
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition)
    {
//        if(fromPosition < toPosition)
//        {
//            for(int i=fromPosition; i<toPosition; i++)
//            {
//                Collections.swap(exercises,i,i+1);
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
        updatedMovedItems();
        notifyItemMoved(fromPosition, toPosition);
    }

    public void updatedMovedItems()
    {
        for(int i=0; i<getItemCount(); i++)
        {
            physicalActivityData.editSimpleWorkoutExercise(ids.get(i),exercises.get(i));
        }
    }

    @Override
    public void onItemDisMiss(int position)
    {
        physicalActivityData.deleteExerciseFromSimpleWorkout(exercises.get(position).getId());
        exercises.remove(position);
        notifyItemRemoved(position);
    }


    public void delete(int position)
    {
        physicalActivityData.deleteExerciseFromSimpleWorkout(exercises.get(position).getId());
        exercises.remove(position);
        notifyItemRemoved(position);

    }

    public void editSimpleWorkoutExercises(int id, int reps)
    {
        physicalActivityData.editSimpleWorkoutExerciseReps(id, reps);
    }

    public String getWorkoutAsTraining()
    {
        String training="Zestaw ćwiczeń\n";
        StringBuilder sb = new StringBuilder(training);

        for(SimpleWorkoutExercise e:exercises)
        {
            if (e.getReps() == 0)
            {
                sb.append(e.getExercise().getName() + "\n");
            }
            else
            {
                sb.append(e.getExercise().getName() + " (" + e.getReps() + ")\n");
            }
        }

        return sb.toString();
    }

    public class ChosenSimpleWorkoutViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name;
        private EditText reps;

        public ChosenSimpleWorkoutViewHolder(final View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.simpleExerciseTextViewRow);
            reps = itemView.findViewById(R.id.simpleRepsEditTextRow);

//            itemView.setOnLongClickListener(new View.OnLongClickListener()
//            {
//                @Override
//                public boolean onLongClick(View v)
//                {
//                    Utils.showPopupDelete(context,v,getAdapterPosition());
//                    return false;
//                }
//            });

            reps.setOnFocusChangeListener(new View.OnFocusChangeListener()
            {
                @Override
                public void onFocusChange(View v, boolean hasFocus)
                {
                    if(!hasFocus)
                    {
                        if(TextUtils.isEmpty(((EditText)v).getText()))
                        {
                            ((EditText) v).setText("0");
                        }

                        if(Integer.parseInt(reps.getText().toString()) != exercises.get(getAdapterPosition()).getReps())
                        {
                            exercises.get(getAdapterPosition()).setReps(Integer.parseInt(reps.getText().toString()));
                            notifyItemChanged(getAdapterPosition());
                            editSimpleWorkoutExercises(exercises.get(getAdapterPosition()).getId(), exercises.get(getAdapterPosition()).getReps());
                        }

                        Utils.hideKeyboard(context, v);
                    }
                }
            });

        }

    }
}