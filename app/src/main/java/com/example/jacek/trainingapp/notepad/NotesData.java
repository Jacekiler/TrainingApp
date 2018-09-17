package com.example.jacek.trainingapp.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.jacek.trainingapp.common.DBHelper;
import com.example.jacek.trainingapp.common.DataAccess;

import java.util.ArrayList;

public class NotesData extends DataAccess
{

    public NotesData(Context context)
    {
        super(context);
    }

    public void add(Note note)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getNoteDateColumn(), note.getDate());
        values.put(DBHelper.getNoteTimeColumn(), note.getTime());
        values.put(DBHelper.getNoteDescriptionColumn(), note.getDescription());
        db.insert(DBHelper.getNotesTable(), null, values);
    }

    public void delete(Note note)
    {
        db.delete(DBHelper.getNotesTable(), DBHelper.getNoteDateColumn() + "='" + note.getDate() + "' AND " +
                DBHelper.getNoteTimeColumn() + " ='" + note.getTime() + "'", null);
    }

    public void edit(Note currentNote, Note updatedNote)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getNoteDateColumn(), updatedNote.getDate());
        values.put(DBHelper.getNoteTimeColumn(), updatedNote.getTime());
        values.put(DBHelper.getNoteDescriptionColumn(), updatedNote.getDescription());
        db.update(DBHelper.getNotesTable(), values, DBHelper.getNoteDateColumn() + " = '" + currentNote.getDate() + "' AND " +
                DBHelper.getNoteTimeColumn() + " ='" + currentNote.getTime() + "'", null);
    }

    public void edit(String date, Note updatedNote)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getNoteDateColumn(), updatedNote.getDate());
        values.put(DBHelper.getNoteDescriptionColumn(), updatedNote.getDescription());
        db.update(DBHelper.getNotesTable(), values, DBHelper.getNoteDateColumn() + " = '" + date + "'", null);
    }

    public Note get(String date, String time)
    {
        Note note = null;
        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.getNotesTable() + " WHERE " + DBHelper.getNoteDateColumn() + " ='" + date + "' AND " +
                    DBHelper.getNoteTimeColumn() + " ='" + time + "'"                    , null);

            if (cursor.getCount() > 0)
            {
                cursor.moveToFirst();
                note = new Note(cursor.getString(cursor.getColumnIndex(DBHelper.getNoteDateColumn())), cursor.getString(cursor.getColumnIndex(DBHelper.getNoteTimeColumn())),
                        cursor.getString(cursor.getColumnIndex(DBHelper.getNoteDescriptionColumn())));
            }

        }
        catch (Exception e)
        {

        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
        return note;
    }

    public ArrayList<Note> getAllNotes()
    {
        ArrayList<Note> notes = new ArrayList<>();
        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.getNotesTable() + " ORDER BY " + DBHelper.getNoteDateColumn() + " DESC, " + DBHelper.getNoteTimeColumn() + " DESC", null);

            if (cursor.getCount() > 0)
            {
                while (cursor.moveToNext())
                {
                    Note note = new Note(cursor.getString(cursor.getColumnIndex(DBHelper.getNoteDateColumn())), cursor.getString(cursor.getColumnIndex(DBHelper.getNoteTimeColumn())),
                            cursor.getString(cursor.getColumnIndex(DBHelper.getNoteDescriptionColumn())));
                    notes.add(note);
                }
            }

        } catch (Exception e)
        {

        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }


        return notes;
    }


}
