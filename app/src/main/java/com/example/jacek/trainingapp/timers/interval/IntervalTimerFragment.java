package com.example.jacek.trainingapp.timers.interval;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IntervalTimerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IntervalTimerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntervalTimerFragment extends Fragment
{
    private final String MIN_VALUE_TEXT = "To jest wartość minimalna";    // getResources().getString(R.string.min_value_message)  ??
    public static final int PREPARE_REST_SETSREST_COOLING_MIN_VALUE = 0;
    public static final int WORK_EXERCISES_SETS_MIN_VALUE = 1;

    private EditText prepare;
    private EditText work;
    private EditText rest;
    private EditText exercises;
    private EditText sets;
    private EditText setsRest;
    private EditText cooling;
    private FloatingActionButton prepareMinus;
    private FloatingActionButton preparePlus;
    private FloatingActionButton workMinus;
    private FloatingActionButton workPlus;
    private FloatingActionButton restMinus;
    private FloatingActionButton restPlus;
    private FloatingActionButton exercisesMinus;
    private FloatingActionButton exercisesPlus;
    private FloatingActionButton setsMinus;
    private FloatingActionButton setsPlus;
    private FloatingActionButton setsRestMinus;
    private FloatingActionButton setsRestPlus;
    private FloatingActionButton coolingMinus;
    private FloatingActionButton coolingPlus;
    private Button start;

    private LinearLayout root;

    private Handler handler;
    private Runnable task;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public IntervalTimerFragment()
    {}
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
       return inflater.inflate(R.layout.fragment_interval_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        setFieldsFromPreferences();
        onEditTextsChanges();


        setOnLongClickAutoincreaseAutodecrease(preparePlus, prepareMinus, prepare);
        setOnLongClickAutoincreaseAutodecrease(workPlus, workMinus, work);
        setOnLongClickAutoincreaseAutodecrease(restPlus, restMinus, rest);
        setOnLongClickAutoincreaseAutodecrease(exercisesPlus, exercisesMinus, exercises);
        setOnLongClickAutoincreaseAutodecrease(setsPlus, setsMinus, sets);
        setOnLongClickAutoincreaseAutodecrease(setsRestPlus, setsRestMinus, setsRest);
        setOnLongClickAutoincreaseAutodecrease(coolingPlus, coolingMinus, cooling);

        setOnClickIncreaseDecrease(preparePlus, prepareMinus, prepare);
        setOnClickIncreaseDecrease(workPlus, workMinus, work);
        setOnClickIncreaseDecrease(restPlus, restMinus, rest);
        setOnClickIncreaseDecrease(exercisesPlus, exercisesMinus, exercises);
        setOnClickIncreaseDecrease(setsPlus, setsMinus, sets);
        setOnClickIncreaseDecrease(setsRestPlus, setsRestMinus, setsRest);
        setOnClickIncreaseDecrease(coolingPlus, coolingMinus, cooling);





        start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(),IntervalTimerRunner.class);
                intent.putExtra("type",getArguments().getString("type"));
                if(getArguments().getString("type").equals("startingWorkout"))
                {
                    intent.putExtra("exercises", getArguments().getStringArray("exercises"));
                }
                startActivity(intent);
            }
        });
    }

    public void onEditTextsChanges()
    {
        View.OnFocusChangeListener lostFocus = new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                EditText e = (EditText)v;
                if(!hasFocus)
                {
                    if (TextUtils.isEmpty(e.getText()))
                    {
                        if(e.getId() == R.id.prepareEditText || e.getId() == R.id.restEditText || e.getId() == R.id.setsRestEditText ||
                                e.getId() == R.id.coolingEditText)
                        {
                            e.setText(String.valueOf(PREPARE_REST_SETSREST_COOLING_MIN_VALUE));
                        }
                        else
                        {
                            e.setText(String.valueOf(WORK_EXERCISES_SETS_MIN_VALUE));

                        }
                    }
                    Utils.hideKeyboard(getContext(),v);
                }
            }
        };

        TextWatcher textChangedMinZero = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                disableStartIfEmpty();
            }

            @Override
            public void afterTextChanged(Editable s)
            {}
        };

        TextWatcher textChangedMinOne = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                disableStartIfEmpty();
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(!TextUtils.isEmpty(s))
                {
                    if(Integer.parseInt(s.toString()) < WORK_EXERCISES_SETS_MIN_VALUE)
                    {
                        Toast.makeText(getContext(), "Wartość minimalna to "+WORK_EXERCISES_SETS_MIN_VALUE, Toast.LENGTH_SHORT).show();
                        s.clear();
                        s.append(String.valueOf(WORK_EXERCISES_SETS_MIN_VALUE));
                    }
                }
            }
        };


        prepare.addTextChangedListener(textChangedMinZero);
        work.addTextChangedListener(textChangedMinOne);
        rest.addTextChangedListener(textChangedMinZero);
        exercises.addTextChangedListener(textChangedMinOne);
        sets.addTextChangedListener(textChangedMinOne);
        setsRest.addTextChangedListener(textChangedMinZero);
        cooling.addTextChangedListener(textChangedMinZero);

        prepare.setOnFocusChangeListener(lostFocus);
        work.setOnFocusChangeListener(lostFocus);
        rest.setOnFocusChangeListener(lostFocus);
        exercises.setOnFocusChangeListener(lostFocus);
        sets.setOnFocusChangeListener(lostFocus);
        setsRest.setOnFocusChangeListener(lostFocus);
        cooling.setOnFocusChangeListener(lostFocus);
    }

    public void disableStartIfEmpty()
    {
        if(TextUtils.isEmpty(prepare.getText()) || TextUtils.isEmpty(work.getText()) || TextUtils.isEmpty(rest.getText()) ||
                TextUtils.isEmpty(exercises.getText()) || TextUtils.isEmpty(sets.getText()) || TextUtils.isEmpty(setsRest.getText()) ||
                TextUtils.isEmpty(cooling.getText()))
        {
            start.setEnabled(false);
        }
        else
        {
            if(!start.isEnabled())
            {
                start.setEnabled(true);
            }
        }
    }

    public void setOnClickIncreaseDecrease(FloatingActionButton plus, FloatingActionButton minus, final EditText editText)
    {
        setOnClickIncrease(plus, editText);
        setOnClickDecrease(minus, editText);
    }

    public void setOnClickIncrease(FloatingActionButton plus, final EditText editText)
    {
        plus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                increaseText(editText);
                clearFocusOnEditTexts();
            }
        });
    }

    public void setOnClickDecrease(FloatingActionButton minus, final EditText editText)
    {
        minus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                decreaseText(editText);
                clearFocusOnEditTexts();
            }
        });
    }

    public void setOnLongClickAutoincreaseAutodecrease(FloatingActionButton plus, FloatingActionButton minus, EditText editText)
    {
        setOnLongClickIncrease(plus, editText);
        setOnLongClickDecrease(minus, editText);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setOnLongClickIncrease(FloatingActionButton plus, final EditText editText)
    {
        plus.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                startIncreasing(editText);
                clearFocusOnEditTexts();
                return false;
            }
        });

        plus.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    stopIncreasingDecreasing();
                }
                return false;
            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    public void setOnLongClickDecrease(FloatingActionButton minus, final EditText editText)
    {
        minus.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                startDecreasing(editText);
                clearFocusOnEditTexts();
                return false;
            }
        });

        minus.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                stopIncreasingDecreasing();
                return false;
            }
        });
    }

    public void startIncreasing(final EditText editText)
    {
        task = new Runnable()
        {
            @Override
            public void run(
            )
            {
                increaseText(editText);
                handler.postDelayed(task, 100);
            }
        };
        task.run();
    }

    public void startDecreasing(final EditText editText)
    {
        task = new Runnable()
        {
            @Override
            public void run()
            {
                decreaseText(editText);
                handler.postDelayed(task, 100);
            }
        };
        task.run();
    }

    public void stopIncreasingDecreasing()
    {
        handler.removeCallbacks(task);
    }

    public void increaseText(EditText editText)
    {
        if (TextUtils.isEmpty(editText.getText()))
        {
            editText.setText(String.valueOf(getMinValue(editText)));
        }
        else
        {
            int a = Integer.parseInt(editText.getText().toString());
            a++;
            editText.setText(String.valueOf(a));
        }
    }

    public void decreaseText(EditText editText)
    {
        if(TextUtils.isEmpty(editText.getText()))
        {
            editText.setText(String.valueOf(getMinValue(editText)));
        }
        else
        {
            int editTextValue = Integer.parseInt(editText.getText().toString());
            if (editTextValue > getMinValue(editText))
            {
                editTextValue--;
                editText.setText(String.valueOf(editTextValue));
            }
            else
            {
                Toast.makeText(getContext(), MIN_VALUE_TEXT, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public int getMinValue(EditText editText)
    {
        if(editText.getId() == R.id.workEditText || editText.getId() == R.id.exercisesEditText || editText.getId() == R.id.setsEditText)
        {
            return WORK_EXERCISES_SETS_MIN_VALUE;
        }
        else
        {
            return PREPARE_REST_SETSREST_COOLING_MIN_VALUE;
        }
    }

    public void clearFocusOnEditTexts()
    {
        if(prepare.hasFocus() || work.hasFocus() || rest.hasFocus() || exercises.hasFocus() || sets.hasFocus() || setsRest.hasFocus()
                || cooling.hasFocus())
        {
            root.requestFocus();
        }
    }

    public void initialize(View view)
    {
        handler = new Handler();

        root = view.findViewById(R.id.intervalScrollViewLinearLayout);

        prepare = view.findViewById(R.id.prepareEditText);
        work = view.findViewById(R.id.workEditText);
        rest = view.findViewById(R.id.restEditText);
        exercises = view.findViewById(R.id.exercisesEditText);
        sets = view.findViewById(R.id.setsEditText);
        setsRest = view.findViewById(R.id.setsRestEditText);
        cooling = view.findViewById(R.id.coolingEditText);

        prepareMinus = view.findViewById(R.id.prepareMinusButton);
        preparePlus = view.findViewById(R.id.preparePlusButton);
        workMinus = view.findViewById(R.id.workMinusButton);
        workPlus = view.findViewById(R.id.workPlusButton);
        restMinus = view.findViewById(R.id.restMinusButton);
        restPlus = view.findViewById(R.id.restPlusButton);
        exercisesMinus = view.findViewById(R.id.exercisesMinusButton);
        exercisesPlus = view.findViewById(R.id.exercisesPlusButton);
        setsMinus = view.findViewById(R.id.setsMinusButton);
        setsPlus = view.findViewById(R.id.setsPlusButton);
        setsRestMinus = view.findViewById(R.id.setsRestMinusButton);
        setsRestPlus = view.findViewById(R.id.setsRestPlusButton);
        coolingMinus = view.findViewById(R.id.coolingMinusButton);
        coolingPlus = view.findViewById(R.id.coolingPlusButton);
        start = view.findViewById(R.id.startIntervalButtonIntervalTimer);

    }



    public void setFieldsFromPreferences()
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        prepare.setText(String.valueOf(sharedPreferences.getInt("prepare", sharedPreferences.getInt("defaultPrepare", Utils.DEFAULT_PREPARE_VALUE))));
        work.setText(String.valueOf(sharedPreferences.getInt("work", sharedPreferences.getInt("defaultWork", Utils.DEFAULT_WORK_VALUE))));
        rest.setText(String.valueOf(sharedPreferences.getInt("rest", sharedPreferences.getInt("defaultRest", Utils.DEFAULT_REST_VALUE))));
        sets.setText(String.valueOf(sharedPreferences.getInt("sets", sharedPreferences.getInt("defaultSets", Utils.DEFAULT_SETS_VALUE))));
        setsRest.setText(String.valueOf(sharedPreferences.getInt("setsRest", sharedPreferences.getInt("defaultSetsRest", Utils.DEFAULT_SETS_REST_VALUE))));
        cooling.setText(String.valueOf(sharedPreferences.getInt("cooling", sharedPreferences.getInt("defaultCooling", Utils.DEFAULT_COOLING_VALUE))));

        if(getArguments().getString("type").equals("normal"))
        {
            exercises.setText(String.valueOf(sharedPreferences.getInt("exercises", sharedPreferences.getInt("defaultExercises", Utils.DEFAULT_EXERCISES_VALUE))));
        }
        else
        {
            exercises.setText(String.valueOf(getArguments().getStringArray("exercises").length));
            exercises.setEnabled(false);
            exercisesPlus.setEnabled(false);
            exercisesMinus.setEnabled(false);
        }

    }

    public void restoreDefaults()
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        prepare.setText(String.valueOf(sharedPreferences.getInt("defaultPrepare", Utils.DEFAULT_PREPARE_VALUE)));
        work.setText(String.valueOf(sharedPreferences.getInt("defaultWork", Utils.DEFAULT_WORK_VALUE)));
        rest.setText(String.valueOf(sharedPreferences.getInt("defaultRest", Utils.DEFAULT_REST_VALUE)));

        sets.setText(String.valueOf(sharedPreferences.getInt("defaultSets", Utils.DEFAULT_SETS_VALUE)));
        setsRest.setText(String.valueOf(sharedPreferences.getInt("defaultSetsRest", Utils.DEFAULT_SETS_REST_VALUE)));
        cooling.setText(String.valueOf(sharedPreferences.getInt("defaultCooling", Utils.DEFAULT_COOLING_VALUE)));

        if(getArguments().getString("type").equals("normal"))
        {
            exercises.setText(String.valueOf(sharedPreferences.getInt("defaultExercises", Utils.DEFAULT_EXERCISES_VALUE)));
        }

    }


    public void openConfirmDialog()
    {
        AddIntervalTimerConfirmDialog confirmDialog = new AddIntervalTimerConfirmDialog();
        Bundle args = new Bundle();
        args.putInt("prepare", Integer.parseInt(prepare.getText().toString()));
        args.putInt("work", Integer.parseInt(work.getText().toString()));
        args.putInt("rest", Integer.parseInt(rest.getText().toString()));
        args.putInt("exercises", Integer.parseInt(exercises.getText().toString()));
        args.putInt("sets", Integer.parseInt(sets.getText().toString()));
        args.putInt("setsRest", Integer.parseInt(setsRest.getText().toString()));
        args.putInt("cooling", Integer.parseInt(cooling.getText().toString()));
        confirmDialog.setArguments(args);
        confirmDialog.show(getActivity().getSupportFragmentManager(),"interval confirm dialog");
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_interval_timer, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch(item.getItemId())
        {
            case R.id.setDefaultTimerValues:
            {
                clearFocusOnEditTexts();
                restoreDefaults();
                return true;
            }
            case R.id.addTimerToFavourites:
            {
                openConfirmDialog();
                return true;
            }
            case R.id.openFavouritesTimersList:
            {
                Intent list_intent = new Intent(getContext(), IntervalTimerFavouritesActivity.class);
                getActivity().startActivityForResult(list_intent, 2);
                return true;
            }
            case android.R.id.home:
            {
                getActivity().finish();
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void setValuesOnPauseIfChanged()
    {
        editor = sharedPreferences.edit();
        if(Integer.parseInt(prepare.getText().toString()) != sharedPreferences.getInt("prepare",sharedPreferences.getInt("defaultPrepare", Utils.DEFAULT_PREPARE_VALUE)))
        {
            editor.putInt("prepare",Integer.parseInt(prepare.getText().toString()));
        }

        if(Integer.parseInt(work.getText().toString()) != sharedPreferences.getInt("work",sharedPreferences.getInt("defaultWork", Utils.DEFAULT_WORK_VALUE)))
        {
            editor.putInt("work",Integer.parseInt(work.getText().toString()));
        }

        if(Integer.parseInt(rest.getText().toString()) != sharedPreferences.getInt("rest",sharedPreferences.getInt("defaultRest", Utils.DEFAULT_REST_VALUE)))
        {
            editor.putInt("rest",Integer.parseInt(rest.getText().toString()));
        }

        if(Integer.parseInt(exercises.getText().toString()) != sharedPreferences.getInt("exercises",sharedPreferences.getInt("defaultExercises", Utils.DEFAULT_EXERCISES_VALUE)))
        {
            editor.putInt("exercises",Integer.parseInt(exercises.getText().toString()));
        }

        if(Integer.parseInt(sets.getText().toString()) != sharedPreferences.getInt("sets",sharedPreferences.getInt("defaultSets", Utils.DEFAULT_SETS_VALUE)))
        {
            editor.putInt("sets",Integer.parseInt(sets.getText().toString()));
        }

        if(Integer.parseInt(setsRest.getText().toString()) != sharedPreferences.getInt("setsRest",sharedPreferences.getInt("defaultSetsRest", Utils.DEFAULT_SETS_REST_VALUE)))
        {
            editor.putInt("setsRest",Integer.parseInt(setsRest.getText().toString()));
        }

        if(Integer.parseInt(cooling.getText().toString()) != sharedPreferences.getInt("cooling",sharedPreferences.getInt("defaultCooling", Utils.DEFAULT_COOLING_VALUE)))
        {
            editor.putInt("cooling",Integer.parseInt(cooling.getText().toString()));
        }
        editor.apply();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setFieldsFromPreferences();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        setValuesOnPauseIfChanged();
    }



}
