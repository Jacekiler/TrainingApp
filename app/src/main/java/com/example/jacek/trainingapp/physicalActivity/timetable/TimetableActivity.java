package com.example.jacek.trainingapp.physicalActivity.timetable;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jacek.trainingapp.interfaces.TimetableInterfaces;
import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicActivity;
import com.example.jacek.trainingapp.physicalActivity.PhysicalActivityData;

import java.text.ParseException;
import java.util.Date;

import static com.example.jacek.trainingapp.common.Utils.DAY_IN_MILLIS;
import static com.example.jacek.trainingapp.common.Utils.shortFormatDate;

public class TimetableActivity extends BasicActivity implements TimetableInterfaces
{
    private RecyclerView recycler;
    private Button previousDate;
    private Button nextDate;
    private Button add;
    private TextView date;
    PhysicalActivityData physicalActivityData;
    private TimetableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        setNavViewAndToolbar(R.id.timetable_layout);

        initializeUI();

        physicalActivityData = new PhysicalActivityData(this);
        physicalActivityData.open();
        adapter = new TimetableAdapter(this,physicalActivityData,date.getText().toString());
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        previousDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    date.setText(shortFormatDate.format(shortFormatDate.parse(date.getText().toString()).getTime() - DAY_IN_MILLIS));
                    adapter.update(date.getText().toString());
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        });

        nextDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    date.setText(shortFormatDate.format(shortFormatDate.parse(date.getText().toString()).getTime() + DAY_IN_MILLIS));
                    adapter.update(date.getText().toString());
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openAddTrainingDialog();
            }
        });

    }


    public void openAddTrainingDialog()
    {
        AddTrainingDialog addTrainingDialog = new AddTrainingDialog();

        addTrainingDialog.show(getSupportFragmentManager(),"add training dialog");
    }

    @Override
    public void onAddTrainingClick(String description)
    {
        adapter.add(date.getText().toString(), description);
    }

    @Override
    public void onDeleteClick(int position)
    {
        adapter.delete(position);
    }

    @Override
    public void onSaveTrainingClick(int position, String description)
    {
        adapter.edit(position, description);
    }

    @Override
    public void onEmptyTrainingSaveClick(int position)
    {
        adapter.delete(position);
    }

    @Override
    public void initializeUI()
    {
        recycler = findViewById(R.id.recyclerViewTimetable);
        previousDate = findViewById(R.id.previousDateTimetable);
        nextDate = findViewById(R.id.nextDateTimetable);
        add = findViewById(R.id.addButtonTimetable);
        date = findViewById(R.id.dateTimetable);
        date.setText(shortFormatDate.format(new Date()));
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        physicalActivityData.close();
    }
}