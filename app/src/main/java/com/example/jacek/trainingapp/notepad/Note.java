package com.example.jacek.trainingapp.notepad;

import android.support.annotation.NonNull;

public class Note implements Comparable<Note>
{
    private String date;
    private String time;
    private String description;

    public Note(String date, String time, String note)
    {
        this.date = date;
        this.time = time;
        this.description = note;
    }

    public String getDescription()
    {
        return description;
    }

    public String getDate()
    {
        return date;
    }

    public String getTime()
    {
        return time;
    }

    public String getDatetime()
    {
        return date+" "+time;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Note))
        {
            return false;
        }
        Note note = (Note)obj;
        return date.equals(note.getDate()) && time.equals(note.getTime());
    }

    @Override
    public int compareTo(@NonNull Note o)
    {
        if(date.compareTo(o.getDate()) == 0)
        {
            return -1 * time.compareTo(o.getTime());
        }
        else
        {
            return -1 * date.compareTo(o.getDate());
        }


    }
}
