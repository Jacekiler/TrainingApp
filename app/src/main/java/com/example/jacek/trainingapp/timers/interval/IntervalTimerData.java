package com.example.jacek.trainingapp.timers.interval;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.jacek.trainingapp.common.DBHelper;
import com.example.jacek.trainingapp.common.DataAccess;

import java.util.ArrayList;

/**
 * Created by Jacek on 14.04.2018.
 */

public class IntervalTimerData extends DataAccess
{

    public IntervalTimerData(Context context)
    {
        super(context);
    }

    public boolean add(IntervalTimerCard card)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getIntervalTimerNameColumn(), card.getName());
        values.put(DBHelper.getIntervalTimerPrepareColumn(), card.getPrepare());
        values.put(DBHelper.getIntervalTimerWorkColumn(), card.getWork());
        values.put(DBHelper.getIntervalTimerRestColumn(), card.getRest());
        values.put(DBHelper.getIntervalTimerExercisesColumn(), card.getExercises());
        values.put(DBHelper.getIntervalTimerSetsColumn(), card.getSets());
        values.put(DBHelper.getIntervalTimerSetsRestColumn(), card.getSetsRest());
        values.put(DBHelper.getIntervalTimerCoolingColumn(), card.getCooling());
        return db.insert(DBHelper.getIntervalTimersTable(), null, values) != -1;
    }

    public void delete(IntervalTimerCard card)
    {
        db.delete(DBHelper.getIntervalTimersTable(), DBHelper.getIntervalTimerIdColumn() + "=" + card.getId(), null);
    }

    public void edit(IntervalTimerCard currentCard, IntervalTimerCard updatedCard)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getIntervalTimerNameColumn(), updatedCard.getName());
        values.put(DBHelper.getIntervalTimerPrepareColumn(), updatedCard.getPrepare());
        values.put(DBHelper.getIntervalTimerWorkColumn(), updatedCard.getWork());
        values.put(DBHelper.getIntervalTimerRestColumn(), updatedCard.getRest());
        values.put(DBHelper.getIntervalTimerExercisesColumn(), updatedCard.getExercises());
        values.put(DBHelper.getIntervalTimerSetsColumn(), updatedCard.getSets());
        values.put(DBHelper.getIntervalTimerSetsRestColumn(), updatedCard.getSetsRest());
        values.put(DBHelper.getIntervalTimerCoolingColumn(), updatedCard.getCooling());
        db.update(DBHelper.getIntervalTimersTable(), values, DBHelper.getIntervalTimerIdColumn() + " = " + currentCard.getId(), null);
    }

    public ArrayList<IntervalTimerCard> getAll()
    {
        ArrayList<IntervalTimerCard> cards = new ArrayList<>();
        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.getIntervalTimersTable(), null);

            if (cursor.getCount() > 0)
            {
                while (cursor.moveToNext())
                {
                    IntervalTimerCard card = new IntervalTimerCard(cursor.getInt(cursor.getColumnIndex(DBHelper.getIntervalTimerIdColumn())),
                            cursor.getString(cursor.getColumnIndex(DBHelper.getIntervalTimerNameColumn())),
                            cursor.getInt(cursor.getColumnIndex(DBHelper.getIntervalTimerPrepareColumn())),
                            cursor.getInt(cursor.getColumnIndex(DBHelper.getIntervalTimerWorkColumn())),
                            cursor.getInt(cursor.getColumnIndex(DBHelper.getIntervalTimerRestColumn())),
                            cursor.getInt(cursor.getColumnIndex(DBHelper.getIntervalTimerExercisesColumn())),
                            cursor.getInt(cursor.getColumnIndex(DBHelper.getIntervalTimerSetsColumn())),
                            cursor.getInt(cursor.getColumnIndex(DBHelper.getIntervalTimerSetsRestColumn())),
                            cursor.getInt(cursor.getColumnIndex(DBHelper.getIntervalTimerCoolingColumn())));
                    cards.add(card);
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


        return cards;
    }
}
