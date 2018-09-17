package com.example.jacek.trainingapp.timers.simple;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.jacek.trainingapp.R;
import com.example.jacek.trainingapp.common.Utils;

import java.util.Locale;


public class SimpleTimerFragment extends Fragment
{
    private static final int HOUR_IN_MILLIS = 3600000;
    private static final int MINUTE_IN_MILLIS = 60000;
    private static final int SECOND_IN_MILLIS = 1000;
    private static final int HOURS = 100;
    private static final int MINUTES = 60;
    private static final int SECONDS = 60;
    private static final int ZERO = 0;

    private Button addHour;
    private Button subHour;
    private Button addMin;
    private Button subMin;
    private Button addSec;
    private Button subSec;
    private EditText hoursEditText;
    private EditText minutesEditText;
    private EditText secondsEditText;

    private Button start;
    private Button stop;
    private CountDownTimer countDownTimer;

    private long timeLeftInMilliseconds;

    private long startTime;
    private int milliseconds;
    private Handler handler;
    private Runnable updateTimeThread;
    private Handler increaseDecreaseHandler;
    private Runnable task;

    public SimpleTimerFragment()
    {}
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_simple_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);

        setOnLongClickAutoincreaseAutodecrease(addHour, subHour, hoursEditText, HOURS);
        setOnLongClickAutoincreaseAutodecrease(addMin, subMin, minutesEditText, MINUTES);
        setOnLongClickAutoincreaseAutodecrease(addSec, subSec, secondsEditText, SECONDS);

        setOnClickIncreaseDecrease(addHour, subHour, hoursEditText, HOURS);
        setOnClickIncreaseDecrease(addMin, subMin, minutesEditText, MINUTES);
        setOnClickIncreaseDecrease(addSec, subSec,secondsEditText, SECONDS);

        onEditTextsChanges();

        setTimer();

        start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startSimpleTimer();
            }
        });

        stop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                stopSimpleTimer();
            }
        });
    }

    public void setTimer()
    {
        milliseconds = SECOND_IN_MILLIS - 1;
        handler = new Handler();
        updateTimeThread = new Runnable()
        {
            boolean three, two, one, zero = false;
            int temp = 0;
            @Override
            public void run()
            {
                timeLeftInMilliseconds = startTime - SystemClock.uptimeMillis();

                int hours = (int)timeLeftInMilliseconds/HOUR_IN_MILLIS;
                int minutes = (int)timeLeftInMilliseconds%HOUR_IN_MILLIS/MINUTE_IN_MILLIS;
                int seconds = (int)timeLeftInMilliseconds%MINUTE_IN_MILLIS/SECOND_IN_MILLIS;

                hoursEditText.setText(String.format("%02d",hours));
                minutesEditText.setText(String.format("%02d",minutes));
                secondsEditText.setText(String.format(Locale.ENGLISH,"%02d",seconds));


                if(!three && timeLeftInMilliseconds < 4000)   // && timeLeftInMilliseconds > 3950)
                {
                    three = true;
                    playBeep(R.raw.beep1);
                }
                if(!two && timeLeftInMilliseconds < 3000)   // && timeLeftInMilliseconds > 2950)
                {
                    two = true;
                    playBeep(R.raw.beep1);
                }
                if(!one && timeLeftInMilliseconds < 2000)   // && timeLeftInMilliseconds > 1950)
                {
                    one = true;
                    playBeep(R.raw.beep1);
                }
                if(!zero && timeLeftInMilliseconds < 1000)   // && timeLeftInMilliseconds > 950)
                {
                    zero = true;
                    playBeep(R.raw.beep2);
                }

                if(timeLeftInMilliseconds<1000)
                {
                    three= false;
                    two = false;
                    one = false;
                    zero = false;
                    stopSimpleTimer();
                }
                else
                {
                    handler.postDelayed(this, 0);
                }
            }
        };
    }

    public void setOnClickIncreaseDecrease(Button plus, Button minus, EditText editText, int maxValue)
    {
        setOnClickIncrease(plus, editText, maxValue);
        setOnClickDecrease(minus, editText, maxValue);
    }

    public void setOnClickIncrease(Button plus, final EditText editText, final int maxValue)
    {
        plus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                increaseText(editText, maxValue);
                clearFocusOnEditTexts();
            }
        });
    }

    public void setOnClickDecrease(Button minus, final EditText editText, final int maxValue)
    {
        minus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                decreaseText(editText, maxValue);
                clearFocusOnEditTexts();
            }
        });
    }

    public void setOnLongClickAutoincreaseAutodecrease(Button plus, Button minus, EditText editText, int maxValue)
    {
        setOnLongClickIncrease(plus, editText, maxValue);
        setOnLongClickDecrease(minus, editText, maxValue);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setOnLongClickIncrease(Button plus, final EditText editText, final int maxValue)
    {
        plus.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                startIncreasing(editText, maxValue);
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

    public void startIncreasing(final EditText editText, final int maxValue)
    {
        task = new Runnable()
        {
            @Override
            public void run(
            )
            {
                increaseText(editText, maxValue);
                increaseDecreaseHandler.postDelayed(task, 100);
            }
        };
        task.run();
    }


    public void increaseText(EditText editText, int maxValue)
    {
        int a = Integer.parseInt(editText.getText().toString());
        a++;
        a %= maxValue;
        editText.setText(String.format("%02d",a));
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setOnLongClickDecrease(Button minus, final EditText editText, final int maxValue)
    {
        minus.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                startDecreasing(editText, maxValue);
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

    public void startDecreasing(final EditText editText, final int maxValue)
    {
        task = new Runnable()
        {
            @Override
            public void run()
            {
                decreaseText(editText, maxValue);
                increaseDecreaseHandler.postDelayed(task, 100);
            }
        };
        task.run();
    }

    public void decreaseText(EditText editText, int maxValue)
    {
        int editTextValue = Integer.parseInt(editText.getText().toString());
        if (editTextValue > ZERO)
        {
            editTextValue--;
            editText.setText(String.format("%02d", editTextValue));
        }
        else
        {
            editText.setText(String.format("%02d", maxValue - 1));
        }
    }

    public void stopIncreasingDecreasing()
    {
        increaseDecreaseHandler.removeCallbacks(task);
    }



    public void initialize(View view)
    {
        addHour =  view.findViewById(R.id.addHourSimpleTimer);
        subHour = view.findViewById(R.id.subHourSimpleTimer);
        addMin = view.findViewById(R.id.addMinuteSimpleTimer);
        subMin = view.findViewById(R.id.subMinuteSimpleTimer);
        addSec = view.findViewById(R.id.addSecondSimpleTimer);
        subSec = view.findViewById(R.id.subSecondSimpleTimer);
        hoursEditText =  view.findViewById(R.id.hoursSimpleTimer);
        minutesEditText = view.findViewById(R.id.minutesSimpleTimer);
        secondsEditText = view.findViewById(R.id.secondsSimpleTimer);

        start = view.findViewById(R.id.startSimpleTimer);
        stop = view.findViewById(R.id.stopSimpleTimer);


        increaseDecreaseHandler = new Handler();
    }

    public void playBeep(int resID)
    {
        MediaPlayer mp = MediaPlayer.create(getContext(),resID);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                mp.release();
            }
        });
        mp.setLooping(false);
        mp.start();
    }

    public void changeStartStopVisibilities()
    {
        if (start.getVisibility() == View.VISIBLE)
        {

            start.setVisibility(View.INVISIBLE);
            stop.setVisibility(View.VISIBLE);
        }
        else
        {
            stop.setVisibility(View.INVISIBLE);
            start.setVisibility(View.VISIBLE);
        }
    }

    public void startSimpleTimer()
    {
        startTime = SystemClock.uptimeMillis() + getHours()*HOUR_IN_MILLIS + getMinutes()*MINUTE_IN_MILLIS+getSeconds()*SECOND_IN_MILLIS + milliseconds; //+SECOND_IN_MILLIS-1;
        handler.postDelayed(updateTimeThread,0);
        changeStartStopVisibilities();
        disableButtons();

    }

    public void stopSimpleTimer()
    {
        milliseconds = (int)timeLeftInMilliseconds%SECOND_IN_MILLIS;
        handler.removeCallbacks(updateTimeThread);
        changeStartStopVisibilities();
        enableButtons();
    }

    public void clearFocusOnEditTexts()
    {
        if(hoursEditText.hasFocus())
        {
            hoursEditText.clearFocus();
        }
        else if(minutesEditText.hasFocus())
        {
            minutesEditText.clearFocus();
        }
        else if(secondsEditText.hasFocus())
        {
            secondsEditText.clearFocus();
        }
    }

    public void onEditTextsChanges()
    {
        View.OnFocusChangeListener lostFocus = new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if(!hasFocus)
                {
                    if (TextUtils.isEmpty(((EditText)v).getText()))
                    {
                        ((EditText)v).setText(String.format("%02d",0));
                    }
                    else if(((EditText)v).getText().length()==1)
                    {
                        ((EditText)v).setText(String.format("%02d",Integer.parseInt(((EditText)v).getText().toString())));
                    }
                    Utils.hideKeyboard(getContext(),v);
                }
            }
        };

        TextWatcher textChanged = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                disableStartIfZerosOrEmpty();
            }

            @Override
            public void afterTextChanged(Editable s)
            {}
        };

        hoursEditText.addTextChangedListener(textChanged);
        minutesEditText.addTextChangedListener(textChanged);
        secondsEditText.addTextChangedListener(textChanged);

        hoursEditText.setOnFocusChangeListener(lostFocus);
        minutesEditText.setOnFocusChangeListener(lostFocus);
        secondsEditText.setOnFocusChangeListener(lostFocus);
    }


    public void disableButtons()
    {
        addHour.setEnabled(false);
        subHour.setEnabled(false);
        addMin.setEnabled(false);
        subMin.setEnabled(false);
        addSec.setEnabled(false);
        subSec.setEnabled(false);
    }

    public void enableButtons()
    {
        addHour.setEnabled(true);
        subHour.setEnabled(true);
        addMin.setEnabled(true);
        subMin.setEnabled(true);
        addSec.setEnabled(true);
        subSec.setEnabled(true);
    }


    public int getHours()
    {
        return Integer.parseInt(hoursEditText.getText().toString());
    }

    public int getMinutes()
    {
        return Integer.parseInt(minutesEditText.getText().toString());
    }

    public int getSeconds()
    {
        return Integer.parseInt(secondsEditText.getText().toString());
    }


    public void disableStartIfZerosOrEmpty()
    {
        if(TextUtils.isEmpty(hoursEditText.getText()) || TextUtils.isEmpty(minutesEditText.getText()) || TextUtils.isEmpty(secondsEditText.getText()) ||
                (getHours() == 0 && getMinutes() == 0 && getSeconds() == 0))
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



}
