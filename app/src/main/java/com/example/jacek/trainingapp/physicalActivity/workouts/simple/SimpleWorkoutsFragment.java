package com.example.jacek.trainingapp.physicalActivity.workouts.simple;

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
import com.example.jacek.trainingapp.physicalActivity.PhysicalActivityData;
import com.example.jacek.trainingapp.physicalActivity.workouts.AddWorkoutDialog;


public class SimpleWorkoutsFragment extends Fragment
{
    private RecyclerView recyclerView;
    private Button add;
    private PhysicalActivityData physicalActivityData;
    private SimpleWorkoutsAdapter adapter;

    public SimpleWorkoutsFragment()
    {}


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_simple_workouts, container, false);
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
                openAddSimpleWorkoutDialog();
            }
        });

    }

    public void openAddSimpleWorkoutDialog()
    {
        AddWorkoutDialog addSimpleWorkoutDialog = new AddWorkoutDialog();
        addSimpleWorkoutDialog.show(getFragmentManager(), "add simple workout dialog");
    }

    public void addWorkout(SimpleWorkout simpleWorkout)
    {
        adapter.add(simpleWorkout);
    }

    public void deleteWorkout(int position)
    {
        adapter.delete(position);
    }

    public void initialize(View view)
    {
        recyclerView = view.findViewById(R.id.simpleWorkoutsRecyclerView);
        add = view.findViewById(R.id.addSimpleWorkout);
    }

    public void setAdapter()
    {
        if(getArguments().getString("type").equals("normal"))
        {
            adapter = new SimpleWorkoutsAdapter(getContext(), physicalActivityData, getArguments().getString("type"));
        }
        else
        {
            adapter = new SimpleWorkoutsAdapter(getContext(), physicalActivityData, getArguments());
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
