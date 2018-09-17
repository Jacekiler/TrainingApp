package com.example.jacek.trainingapp.notepad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jacek.trainingapp.common.Utils;
import com.example.jacek.trainingapp.interfaces.NoteDetailInterfaces;
import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.BasicNoNavViewActivity;
import com.example.jacek.trainingapp.physicalActivity.PhysicalActivityData;

import java.util.Date;

import static com.example.jacek.trainingapp.common.Utils.ACTIVITY_TYPE_EDIT;
import static com.example.jacek.trainingapp.common.Utils.shortFormatDate;
import static com.example.jacek.trainingapp.common.Utils.timeFormat;

public class NoteDetailsActivity extends BasicNoNavViewActivity implements NoteDetailInterfaces
{
    private TextView dateTV;
    private TextView timeTV;
    private EditText description;
    private Button save;
    private Button addToTimetable;
    private NotesData notesData;
    private String date;
    //
    private String time;
    private String currentDescription;
    private boolean stateChanged = false;

    private NotepadAdapter adapter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        initialize();

        setToolbar();

        notesData = new NotesData(this);
        notesData.open();

        adapter = new NotepadAdapter(this, notesData);

        if(getIntent().getExtras().getString("type").equals(ACTIVITY_TYPE_EDIT))
        {
            date = getIntent().getExtras().getString("date");
            time = getIntent().getExtras().getString("time");
            currentDescription = adapter.get(date, time).getDescription();
        }
        else
        {
            Date currentDate = new Date();
            date = shortFormatDate.format(currentDate);
            time = timeFormat.format(currentDate);
            currentDescription="";
        }

        dateTV.setText(date);
        timeTV.setText(time);
        description.setText(currentDescription);

        description.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                if(!s.toString().equals(currentDescription))
                {
                    stateChanged = true;
                    save.setVisibility(View.VISIBLE);
                    save.setEnabled(true);

                    addToTimetable.setVisibility(View.INVISIBLE);
                    addToTimetable.setEnabled(false);
                }
                else
                {
                    stateChanged = false;
                    save.setVisibility(View.INVISIBLE);
                    save.setEnabled(false);

                    addToTimetable.setVisibility(View.VISIBLE);
                    addToTimetable.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               save();
            }
        });

        addToTimetable.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utils.openAddToTimetable(NoteDetailsActivity.this);
            }
        });


    }


    public void save()
    {
        if(TextUtils.isEmpty(description.getText()))
        {
            if(colorChanged)
            {
                setResult(RESULT_OK);
                colorChanged = false;
            }
            finish();
            adapter.delete(getIntent().getExtras().getInt(Utils.POSITION));
        }
        else
        {
            Utils.hideKeyboard(this,description);

            Date currentDate = new Date();
            String newDate = shortFormatDate.format(currentDate);
            String newTime = timeFormat.format(currentDate);
            dateTV.setText(newDate);
            timeTV.setText(newTime);
            currentDescription = description.getText().toString();
            Note newNote = new Note(newDate, newTime, description.getText().toString());

            if(getIntent().getExtras().getString("type").equals(ACTIVITY_TYPE_EDIT))
            {
                adapter.edit(getIntent().getExtras().getInt(Utils.POSITION), newNote);
            }
            else
            {
                adapter.add(newNote);
            }
            stateChanged = false;
            save.setVisibility(View.INVISIBLE);
            save.setEnabled(false);

            addToTimetable.setVisibility(View.VISIBLE);
            addToTimetable.setEnabled(true);
        }
    }


    public void openExitDialog()
    {
        ExitNoteDetailsDialog exitNoteDetailsDialog = new ExitNoteDetailsDialog();
        exitNoteDetailsDialog.show(getSupportFragmentManager(),"exit note details dialog");
    }

    @Override
    public void onBackPressed()
    {
        if(stateChanged)
        {
            openExitDialog();
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public void onSaveClick()
    {
        save();
        if(colorChanged)
        {
            setResult(RESULT_OK);
            colorChanged = false;
        }
        finish();
    }

    @Override
    public void onAddTrainingToTimetableClick(String date)
    {
        PhysicalActivityData physicalActivityData = new PhysicalActivityData(this);
        physicalActivityData.open();

        physicalActivityData.addTraining(date, description.getText().toString());

        physicalActivityData.close();
    }

    @Override
    public void onNotSaveClick()
    {
        if(colorChanged)
        {
            setResult(RESULT_OK);
            colorChanged = false;
        }
        finish();
    }

    public void initialize()
    {
        dateTV = findViewById(R.id.dateNoteDetails);
        timeTV = findViewById(R.id.timeNoteDetails);
        description = findViewById(R.id.descriptionNoteDetails);
        save = findViewById(R.id.saveNoteDetails);
        addToTimetable = findViewById(R.id.addNoteToTimetable);

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        notesData.close();
    }
}
