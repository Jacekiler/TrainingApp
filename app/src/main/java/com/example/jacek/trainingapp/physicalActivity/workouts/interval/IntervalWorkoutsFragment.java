package com.example.jacek.trainingapp.physicalActivity.workouts.interval;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.physicalActivity.PhysicalActivityData;
import com.example.jacek.trainingapp.physicalActivity.workouts.AddWorkoutDialog;


public class IntervalWorkoutsFragment extends Fragment
{
    private RecyclerView recyclerView;
    private Button add;
    private PhysicalActivityData physicalActivityData;
    private IntervalWorkoutsAdapter adapter;

    public IntervalWorkoutsFragment()
    {}



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_interval_workouts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);

        physicalActivityData = new PhysicalActivityData(getContext());
        physicalActivityData.open();

        setAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openAddIntervalWorkoutDialog();
            }
        });

    }

    public void addWorkout(IntervalWorkout intervalWorkout)
    {
        adapter.add(intervalWorkout);
    }

    public void deleteWorkout(int position)
    {
        adapter.delete(position);
    }

    public void openAddIntervalWorkoutDialog()
    {
        AddWorkoutDialog addWorkoutDialog = new AddWorkoutDialog();
        addWorkoutDialog.show(getFragmentManager(),"add interval workout dialog");
    }

    public void initialize(View view)
    {
        recyclerView = view.findViewById(R.id.intervalWorkoutsRecyclerView);
        add = view.findViewById(R.id.addIntervalWorkout);
    }




    public void setAdapter()
    {
        if(getArguments().getString("type").equals(Utils.ACTIVITY_TYPE_NORMAL))
        {
            adapter = new IntervalWorkoutsAdapter(getContext(), physicalActivityData, getArguments().getString("type"));
        }
        else
        {
            adapter = new IntervalWorkoutsAdapter(getContext(), physicalActivityData, getArguments());
        }
        recyclerView.setAdapter(adapter);
    }

    public void updateAdapter()
    {
        adapter.update();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setAdapter();
//        updateAdapter();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        physicalActivityData.close();
    }

}
