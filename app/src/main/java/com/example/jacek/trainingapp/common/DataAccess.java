package com.example.jacek.trainingapp.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public abstract class DataAccess
{
    protected SQLiteDatabase db;
    private DBHelper dbhelper;

    public DataAccess(Context context)
    {
        dbhelper = new DBHelper(context);
    }

    public void open()
    {
        db = dbhelper.getWritableDatabase();
        db.setForeignKeyConstraintsEnabled(true);

    }

    public void close()
    {
        if (dbhelper != null)
        {
            dbhelper.close();
        }
    }

    public boolean hasDate(String date)
    {
        Cursor cursor = null;
        boolean found = false;
        try
        {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.getDatesTable() +
                    " WHERE " + DBHelper.getDatesDate() + " ='" + date + "'", null);

            if (cursor.getCount() > 0)
            {
                found = true;
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

        return found;
    }

    public void addDate(String date)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getDatesDate(), date);
        db.insert(DBHelper.getDatesTable(), null, values);
    }

    protected boolean isDateUsed(String date)
    {
        Cursor cursor = null;
        boolean found = false;
        try
        {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.getDailyCaloriesDetailsTable() + " WHERE " +
                    DBHelper.getDailyCaloriesDetailsDateColumn() + " ='" + date + "'", null);

            if (cursor.getCount() > 0)
            {
                found = true;
            }
            else
            {
                cursor = db.rawQuery("SELECT * FROM " + DBHelper.getTimetableTrainingsTable() + " WHERE " +
                        DBHelper.getTimetableTrainingsDateColumn() + " ='" + date + "'", null);
                if (cursor.getCount() > 0)
                {
                    found = true;
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

        return found;
    }

    public void deleteDate(String date)
    {
        db.delete(DBHelper.getDatesTable(),DBHelper.getDatesDate() + " ='" + date + "'", null);
    }

}
