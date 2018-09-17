package com.example.jacek.trainingapp.physicalActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;

import com.example.jacek.trainingapp.common.DBHelper;
import com.example.jacek.trainingapp.common.DataAccess;
import com.example.jacek.trainingapp.physicalActivity.exercises.Exercise;
import com.example.jacek.trainingapp.physicalActivity.timetable.Training;
import com.example.jacek.trainingapp.physicalActivity.workouts.interval.IntervalWorkout;
import com.example.jacek.trainingapp.physicalActivity.workouts.interval.IntervalWorkoutExercise;
import com.example.jacek.trainingapp.physicalActivity.workouts.simple.SimpleWorkout;
import com.example.jacek.trainingapp.physicalActivity.workouts.simple.SimpleWorkoutExercise;

import java.util.ArrayList;

public class PhysicalActivityData extends DataAccess
{

    public PhysicalActivityData(Context context)
    {
        super(context);
    }


    public void addExercise(Exercise exercise) throws SQLiteConstraintException
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getExerciseNameColumn(), exercise.getName());
        values.put(DBHelper.getExerciseCategoryColumn(), exercise.getCategory());
        values.put(DBHelper.getExerciseDescriptionColumn(), exercise.getDescription());
        db.insertOrThrow(DBHelper.getExercisesTable(), null, values);
    }

    public void deleteExercise(Exercise exercise)
    {
        db.delete(DBHelper.getExercisesTable(), DBHelper.getExerciseNameColumn() + "='" + exercise.getName() + "'", null);
    }

    public void editExercise(Exercise currentExercise, Exercise updatedExercise)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getExerciseNameColumn(), updatedExercise.getName());
        values.put(DBHelper.getExerciseCategoryColumn(), updatedExercise.getCategory());
        values.put(DBHelper.getExerciseDescriptionColumn(), updatedExercise.getDescription());
        db.update(DBHelper.getExercisesTable(), values, DBHelper.getExerciseNameColumn() + " = '" + currentExercise.getName() + "'", null);
    }

    public void deleteExerciseFromIntervalWorkout(int id)
    {
        db.delete(DBHelper.getIntervalWorkoutsExercisesTable(), DBHelper.getIntervalWorkoutsExercisesIdColumn() +
                " = " + id, null);
    }

    public void deleteExerciseFromSimpleWorkout(int id)
    {
        db.delete(DBHelper.getSimpleWorkoutsExercisesTable(), DBHelper.getSimpleWorkoutsExercisesIdColumn() +
                " = " + id, null);
    }

    public ArrayList<Exercise> getAllSpecifiedExercises(String category)
    {
        ArrayList<Exercise> exercises = new ArrayList<>();
        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.getExercisesTable() + " WHERE " + DBHelper.getExerciseCategoryColumn() + " = '" + category + "' ORDER BY " + DBHelper.getExerciseNameColumn(), null);

            if (cursor.getCount() > 0)
            {
                while (cursor.moveToNext())
                {
                    Exercise exercise = new Exercise(cursor.getString(cursor.getColumnIndex(DBHelper.getExerciseNameColumn())), cursor.getString(cursor.getColumnIndex(DBHelper.getExerciseCategoryColumn())), cursor.getString(cursor.getColumnIndex(DBHelper.getExerciseDescriptionColumn())));
                    exercises.add(exercise);
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


        return exercises;
    }

    public ArrayList<IntervalWorkoutExercise> getAllIntervalWorkoutExercises(String workoutName)
    {
        ArrayList<IntervalWorkoutExercise> exercises = new ArrayList<>();
        Cursor cursor = null;
        try
        {
            String sql = "SELECT t1." + DBHelper.getExerciseNameColumn() + ", t1." + DBHelper.getExerciseCategoryColumn() +
                    ", t1." + DBHelper.getExerciseDescriptionColumn() + ", t2." + DBHelper.getIntervalWorkoutsExercisesIdColumn() + " " +
                    "FROM " + DBHelper.getExercisesTable() + " t1 " +
                    "JOIN " + DBHelper.getIntervalWorkoutsExercisesTable() + " t2 " +
                    "ON t1." + DBHelper.getExerciseNameColumn() + " = t2." + DBHelper.getIntervalWorkoutsExercisesExerciseColumn() + " " +
                    "WHERE t2." + DBHelper.getIntervalWorkoutsExercisesNameColumn() + " = '" + workoutName +"' " +
                    "ORDER BY t2."+ DBHelper.getIntervalWorkoutsExercisesIdColumn();
            cursor = db.rawQuery(sql, null);


            if (cursor.getCount() > 0)
            {
                while (cursor.moveToNext())
                {
                    Exercise exercise = new Exercise(cursor.getString(cursor.getColumnIndex(DBHelper.getExerciseNameColumn())), cursor.getString(cursor.getColumnIndex(DBHelper.getExerciseCategoryColumn())), cursor.getString(cursor.getColumnIndex(DBHelper.getExerciseDescriptionColumn())));
                    exercises.add(new IntervalWorkoutExercise(cursor.getInt(cursor.getColumnIndex(DBHelper.getIntervalWorkoutsExercisesIdColumn())), exercise));
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

        return exercises;
    }

    public ArrayList<SimpleWorkoutExercise> getAllSimpleWorkoutExercises(String workoutName)
    {
        ArrayList<SimpleWorkoutExercise> exercises = new ArrayList<>();
        Cursor cursor = null;
        try
        {
            String sql = "SELECT t1." + DBHelper.getExerciseNameColumn() + ", t1." + DBHelper.getExerciseCategoryColumn() +
                    ", t1." + DBHelper.getExerciseDescriptionColumn() + ", t2." + DBHelper.getSimpleWorkoutsExercisesIdColumn() +
                    ", t2." + DBHelper.getSimpleWorkoutsExercisesRepsColumn() + " " +
                    "FROM " + DBHelper.getExercisesTable() + " t1 " +
                    "JOIN " + DBHelper.getSimpleWorkoutsExercisesTable() + " t2 " +
                    "ON t1." + DBHelper.getExerciseNameColumn() + " = t2." + DBHelper.getSimpleWorkoutsExercisesExerciseColumn() + " " +
                    "WHERE t2." + DBHelper.getSimpleWorkoutsExercisesNameColumn() + " = '" + workoutName +"' " +
                    "ORDER BY t2." + DBHelper.getSimpleWorkoutsExercisesIdColumn();
            cursor = db.rawQuery(sql, null);


            if (cursor.getCount() > 0)
            {
                while (cursor.moveToNext())
                {
                    Exercise exercise = new Exercise(cursor.getString(cursor.getColumnIndex(DBHelper.getExerciseNameColumn())), cursor.getString(cursor.getColumnIndex(DBHelper.getExerciseCategoryColumn())), cursor.getString(cursor.getColumnIndex(DBHelper.getExerciseDescriptionColumn())));
                    SimpleWorkoutExercise simpleWorkoutExercise = new SimpleWorkoutExercise(cursor.getInt(cursor.getColumnIndex(DBHelper.getIntervalWorkoutsExercisesIdColumn())), exercise,cursor.getInt(cursor.getColumnIndex(DBHelper.getSimpleWorkoutsExercisesRepsColumn())));
                    exercises.add(simpleWorkoutExercise);
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


        return exercises;
    }

    public void editSimpleWorkoutExerciseReps(int id, int reps)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getSimpleWorkoutsExercisesRepsColumn(), reps);
        db.update(DBHelper.getSimpleWorkoutsExercisesTable(), values, DBHelper.getSimpleWorkoutsExercisesIdColumn() +
                " = " + id, null);
    }

    public void editSimpleWorkoutExercise(int id, SimpleWorkoutExercise exercise)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getSimpleWorkoutsExercisesExerciseColumn(), exercise.getExercise().getName());
        values.put(DBHelper.getSimpleWorkoutsExercisesRepsColumn(), exercise.getReps());
        db.update(DBHelper.getSimpleWorkoutsExercisesTable(), values, DBHelper.getSimpleWorkoutsExercisesIdColumn() +
                " = " + id, null);
    }

    public void editIntervalWorkoutExercise(int id, String exercise)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getIntervalWorkoutsExercisesExerciseColumn(), exercise);
        db.update(DBHelper.getIntervalWorkoutsExercisesTable(), values,DBHelper.getIntervalWorkoutsExercisesIdColumn() +
                " = " + id, null);
    }

    public void addIntervalWorkout(IntervalWorkout intervalWorkout) throws SQLiteConstraintException
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getIntervalWorkoutsNameColumn(), intervalWorkout.getName());
        db.insertOrThrow(DBHelper.getIntervalWorkoutsTable(), null, values);
    }

    public void addSimpleWorkout(SimpleWorkout simpleWorkout) throws SQLiteConstraintException
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getSimpleWorkoutsNameColumn(), simpleWorkout.getName());
        db.insertOrThrow(DBHelper.getSimpleWorkoutsTable(), null, values);
    }

    public void addExerciseToIntervalWorkout(String intervalWorkout, Exercise exercise)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getIntervalWorkoutsExercisesNameColumn(), intervalWorkout);
        values.put(DBHelper.getIntervalWorkoutsExercisesExerciseColumn(), exercise.getName());
        db.insert(DBHelper.getIntervalWorkoutsExercisesTable(), null, values);
    }

    public void addExerciseToSimpleWorkout(String simpleWorkout, Exercise exercise)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getSimpleWorkoutsExercisesNameColumn(), simpleWorkout);
        values.put(DBHelper.getSimpleWorkoutsExercisesExerciseColumn(), exercise.getName());
        db.insert(DBHelper.getSimpleWorkoutsExercisesTable(), null, values);
    }

    public void deleteIntervalWorkout(String intervalWorkout)
    {
        db.delete(DBHelper.getIntervalWorkoutsTable(), DBHelper.getIntervalWorkoutsNameColumn() + "='" + intervalWorkout + "'", null);
    }

    public void deleteSimpleWorkout(String simpleWorkout)
    {
        db.delete(DBHelper.getSimpleWorkoutsTable(), DBHelper.getSimpleWorkoutsNameColumn() + "='" + simpleWorkout + "'", null);
    }


    public ArrayList<IntervalWorkout> getAllIntervalWorkouts()
    {
        ArrayList<IntervalWorkout> intervalWorkouts = new ArrayList<>();
        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.getIntervalWorkoutsTable(), null);

            if (cursor.getCount() > 0)
            {
                while (cursor.moveToNext())
                {
                    String name = cursor.getString(cursor.getColumnIndex(DBHelper.getIntervalWorkoutsNameColumn()));
                    IntervalWorkout intervalWorkout = new IntervalWorkout(name, getAllIntervalWorkoutExercises(name));
                    intervalWorkouts.add(intervalWorkout);
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


        return intervalWorkouts;
    }

    public ArrayList<SimpleWorkout> getAllSimpleWorkouts()
    {
        ArrayList<SimpleWorkout> simpleWorkouts = new ArrayList<>();
        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery("SELECT * FROM " + DBHelper.getSimpleWorkoutsTable(), null);

            if (cursor.getCount() > 0)
            {
                while (cursor.moveToNext())
                {
                    String name = cursor.getString(cursor.getColumnIndex(DBHelper.getSimpleWorkoutsNameColumn()));
                    SimpleWorkout simpleWorkout = new SimpleWorkout(name, getAllSimpleWorkoutExercises(name));
                    simpleWorkouts.add(simpleWorkout);
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


        return simpleWorkouts;
    }


    public ArrayList<Training> getAllTrainings(String date)
    {
        ArrayList<Training> trainings = new ArrayList<>();
        Cursor cursor = null;
        try
        {
            cursor = db.rawQuery("SELECT " + DBHelper.getTimetableTrainingsIdColumn() + ", " + DBHelper.getTimetableTrainingsDescriptionColumn() + " FROM "
                    + DBHelper.getTimetableTrainingsTable() + " WHERE " + DBHelper.getTimetableTrainingsDateColumn()
                    + " ='" + date + "' ORDER BY " + DBHelper.getTimetableTrainingsIdColumn(), null);

            if (cursor.getCount() > 0)
            {
                while (cursor.moveToNext())
                {
                    Training training = new Training(cursor.getInt(cursor.getColumnIndex(DBHelper.getTimetableTrainingsIdColumn())),cursor.getString(cursor.getColumnIndex(DBHelper.getTimetableTrainingsDescriptionColumn())));
                    trainings.add(training);
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


        return trainings;
    }

    public void addTraining(String date, String description)
    {
        ContentValues values = new ContentValues();

        if(!hasDate(date))
        {
            ContentValues dateValue = new ContentValues();
            dateValue.put(DBHelper.getDatesDate(), date);
            db.insert(DBHelper.getDatesTable(), null, dateValue);
        }

        values.put(DBHelper.getTimetableTrainingsDateColumn(), date);
        values.put(DBHelper.getTimetableTrainingsDescriptionColumn(), description);
        db.insertOrThrow(DBHelper.getTimetableTrainingsTable(), null, values);

    }

    public void deleteTraining(String date, int id)
    {
        db.delete(DBHelper.getTimetableTrainingsTable(), DBHelper.getTimetableTrainingsIdColumn() + " ='" + id + "'", null);
        if(!isDateUsed(date))
        {
            deleteDate(date);
        }
    }

    public void editTraining(Training training)
    {
        ContentValues values = new ContentValues();
        values.put(DBHelper.getTimetableTrainingsDescriptionColumn(), training.getDescription());
        db.update(DBHelper.getTimetableTrainingsTable(), values, DBHelper.getTimetableTrainingsIdColumn() + " = '" + training.getId() + "'", null);

    }


}
