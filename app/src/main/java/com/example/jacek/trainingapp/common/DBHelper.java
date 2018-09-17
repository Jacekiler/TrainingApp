package com.example.jacek.trainingapp.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jacek.trainingapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jacek on 18.03.2018.
 */

public class DBHelper extends SQLiteOpenHelper
{
    private Context context;
    private static final String DB_NAME = "TrainingApp.db";
    private static final int DB_VERSION = 50;
//    private static DBHelper instance;

    private static final String PRODUCT_CATEGORIES_TABLE = "Products_categories";
    private static final String PRODUCT_CATEGORIES_NAME_COLUMN = "category_name";
    private static final String CREATE_TABLE_PRODUCT_CATEGORIES =
            "CREATE TABLE " + PRODUCT_CATEGORIES_TABLE +
                    " (" +
                    PRODUCT_CATEGORIES_NAME_COLUMN + " TEXT PRIMARY KEY" +
                    ")";

    private static final String PRODUCT_TABLE = "Products";
    private static final String PRODUCT_NAME_COLUMN = "product_name";
    private static final String PRODUCT_CALORIES_COLUMN = "calories";
    private static final String PRODUCT_CATEGORY_COLUMN = "category_name";
    private static final String CREATE_TABLE_PRODUCTS =
            "CREATE TABLE " + PRODUCT_TABLE +
                    " (" +
                    PRODUCT_NAME_COLUMN + " TEXT PRIMARY KEY, " +
                    PRODUCT_CALORIES_COLUMN + " INTEGER NOT NULL, " +
                    PRODUCT_CATEGORY_COLUMN + " TEXT NOT NULL REFERENCES " + PRODUCT_CATEGORIES_TABLE +
                    ")";


    private static final String INTERVAL_TIMERS_TABLE = "Interval_timers";
    private static final String INTERVAL_TIMER_ID_COLUMN = "id";
    private static final String INTERVAL_TIMER_NAME_COLUMN = "timer_name";
    private static final String INTERVAL_TIMER_PREPARE_COLUMN = "prepare";
    private static final String INTERVAL_TIMER_WORK_COLUMN = "work";
    private static final String INTERVAL_TIMER_REST_COLUMN = "rest";
    private static final String INTERVAL_TIMER_EXERCISES_COLUMN = "exercises";
    private static final String INTERVAL_TIMER_SETS_COLUMN = "sets";
    private static final String INTERVAL_TIMER_SETS_REST_COLUMN = "sets_rest";
    private static final String INTERVAL_TIMER_COOLING_COLUMN = "cooling";
    private static final String CREATE_TABLE_INTERVAL_TIMERS =
            "CREATE TABLE " + INTERVAL_TIMERS_TABLE +
                    " (" +
                    INTERVAL_TIMER_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    INTERVAL_TIMER_NAME_COLUMN + " TEXT, " +
                    INTERVAL_TIMER_PREPARE_COLUMN + " INTEGER NOT NULL, " +
                    INTERVAL_TIMER_WORK_COLUMN + " INTEGER NOT NULL, " +
                    INTERVAL_TIMER_REST_COLUMN + " INTEGER NOT NULL, " +
                    INTERVAL_TIMER_EXERCISES_COLUMN + " INTEGER NOT NULL, " +
                    INTERVAL_TIMER_SETS_COLUMN + " INTEGER NOT NULL, " +
                    INTERVAL_TIMER_SETS_REST_COLUMN + " INTEGER NOT NULL, " +
                    INTERVAL_TIMER_COOLING_COLUMN + " INTEGER NOT NULL" +
                    ")";


    private static final String EXERCISES_CATEGORIES_TABLE = "Exercises_categories";
    private static final String EXERCISES_CATEGORIES_NAME_COLUMN = "category_name"; // ręce nogi abs plecy inne
    private static final String CREATE_TABLE_EXERCISES_CATEGORIES =
            "CREATE TABLE " + EXERCISES_CATEGORIES_TABLE +
                    " (" +
                    EXERCISES_CATEGORIES_NAME_COLUMN + " TEXT PRIMARY KEY" +
                    ")";


    private static final String EXERCISES_TABLE = "Exercises";
    private static final String EXERCISE_NAME_COLUMN = "exercise_name";
    private static final String EXERCISE_CATEGORY_COLUMN = "category_name"; // ręce nogi abs plecy inne
    private static final String EXERCISE_DESCRIPTION_COLUMN = "description";
    private static final String CREATE_TABLE_EXERCISES =
            "CREATE TABLE " + EXERCISES_TABLE +
                    " (" +
                    EXERCISE_NAME_COLUMN + " TEXT PRIMARY KEY, " +
                    EXERCISE_CATEGORY_COLUMN + " TEXT NOT NULL REFERENCES " + EXERCISES_CATEGORIES_TABLE + ", " +
                    EXERCISE_DESCRIPTION_COLUMN + " TEXT" +
                    ")";


    private static final String INTERVAL_WORKOUTS_TABLE = "Interval_workouts";
    private static final String INTERVAL_WORKOUTS_NAME_COLUMN = "workout_name";
    private static final String CREATE_TABLE_INTERVAL_WORKOUTS =
            "CREATE TABLE " + INTERVAL_WORKOUTS_TABLE +
                    " (" +
                    INTERVAL_WORKOUTS_NAME_COLUMN + " TEXT PRIMARY KEY" +
                    ")";

    private static final String SIMPLE_WORKOUTS_TABLE = "Simple_workouts";
    private static final String SIMPLE_WORKOUTS_NAME_COLUMN = "workout_name";
    private static final String CREATE_TABLE_SIMPLE_WORKOUTS =
            "CREATE TABLE " + SIMPLE_WORKOUTS_TABLE +
                    " (" +
                    SIMPLE_WORKOUTS_NAME_COLUMN + " TEXT PRIMARY KEY" +
                    ")";


    private static final String INTERVAL_WORKOUTS_EXERCISES_TABLE = "Interval_workouts_exercises";
    private static final String INTERVAL_WORKOUTS_EXERCISES_ID_COLUMN = "id";
    private static final String INTERVAL_WORKOUTS_EXERCISES_NAME_COLUMN = "workout_name";
    private static final String INTERVAL_WORKOUTS_EXERCISES_EXERCISE_COLUMN = "exercise_name";
    private static final String CREATE_TABLE_INTERVAL_WORKOUTS_EXERCISES =
            "CREATE TABLE " + INTERVAL_WORKOUTS_EXERCISES_TABLE +
                    " (" +
                    INTERVAL_WORKOUTS_EXERCISES_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    INTERVAL_WORKOUTS_EXERCISES_NAME_COLUMN + " TEXT NOT NULL REFERENCES " + INTERVAL_WORKOUTS_TABLE + " ON DELETE CASCADE ON UPDATE CASCADE, " +
                    INTERVAL_WORKOUTS_EXERCISES_EXERCISE_COLUMN + " TEXT NOT NULL REFERENCES " + EXERCISES_TABLE + " ON DELETE CASCADE ON UPDATE CASCADE" +
                    ")";


    // zrobić żeby dodawało nulle zamist 0
    // w przypadku zmian - skontrolować rozdział z baz w pracy
    private static final String SIMPLE_WORKOUTS_EXERCISES_TABLE = "Simple_workouts_exercises";
    private static final String SIMPLE_WORKOUTS_EXERCISES_ID_COLUMN = "id";
    private static final String SIMPLE_WORKOUTS_EXERCISES_NAME_COLUMN = "workout_name";
    private static final String SIMPLE_WORKOUTS_EXERCISES_EXERCISE_COLUMN = "exercise_name";
    private static final String SIMPLE_WORKOUTS_EXERCISES_REPS_COLUMN = "reps";
    private static final String CREATE_TABLE_SIMPLE_WORKOUTS_EXERCISES =
            "CREATE TABLE " + SIMPLE_WORKOUTS_EXERCISES_TABLE +
                    " (" +
                    SIMPLE_WORKOUTS_EXERCISES_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SIMPLE_WORKOUTS_EXERCISES_NAME_COLUMN + " TEXT NOT NULL REFERENCES " + SIMPLE_WORKOUTS_TABLE + " ON DELETE CASCADE ON UPDATE CASCADE, " +
                    SIMPLE_WORKOUTS_EXERCISES_EXERCISE_COLUMN + " TEXT NOT NULL REFERENCES " + EXERCISES_TABLE + " ON DELETE CASCADE ON UPDATE CASCADE, " +
                    SIMPLE_WORKOUTS_EXERCISES_REPS_COLUMN + " INTEGER" +
                    ")";


    private static final String DATES_TABLE = "Dates";
    private static final String DATES_DATE = "date";
    private static final String CREATE_TABLE_DATES =
            "CREATE TABLE " + DATES_TABLE +
                    "(" +
                    DATES_DATE + " TEXT PRIMARY KEY" +
                    ")";


    private static final String NOTES_TABLE = "Notes";
    private static final String NOTE_DATE_COLUMN = "date";
    private static final String NOTE_TIME_COLUMN = "time";
    private static final String NOTE_DESCRIPTION_COLUMN = "description";
    private static final String CREATE_TABLE_NOTES =
            "CREATE TABLE " + NOTES_TABLE +
                    " (" +
                    NOTE_DATE_COLUMN + " TEXT REFERENCES " + DATES_TABLE + ", " +
                    NOTE_TIME_COLUMN + " TEXT, " +
                    NOTE_DESCRIPTION_COLUMN + " TEXT NOT NULL, " +
                    "PRIMARY KEY(" + NOTE_DATE_COLUMN + "," + NOTE_TIME_COLUMN + ")" +
                    ")";





    // zmienić żeby dodawało nulle zamiast "" i 0;
    //    // w przypadku zmian - skontrolować rozdział z baz w pracy
    private static final String DAILY_CALORIES_DETAILS_TABLE = "Daily_calories_details";
    private static final String DAILY_CALORIES_DETAILS_ID_COLUMN = "id";
    private static final String DAILY_CALORIES_DETAILS_DATE_COLUMN = "date";
    private static final String DAILY_CALORIES_DETAILS_NAME_COLUMN = "product_name";
    private static final String DAILY_CALORIES_DETAILS_AMOUNT_COLUMN = "amount";
    private static final String DAILY_CALORIES_DETAILS_CALORIES_COLUMN = "calories";
    private static final String CREATE_TABLE_DAILY_CALORIES_DETAILS =
            "CREATE TABLE " + DAILY_CALORIES_DETAILS_TABLE +
                    " (" +
                    DAILY_CALORIES_DETAILS_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DAILY_CALORIES_DETAILS_DATE_COLUMN + " TEXT NOT NULL REFERENCES " + DATES_TABLE + " ON DELETE CASCADE, " +
                    DAILY_CALORIES_DETAILS_NAME_COLUMN + " TEXT, " +
                    DAILY_CALORIES_DETAILS_AMOUNT_COLUMN + " INTEGER, " +
                    DAILY_CALORIES_DETAILS_CALORIES_COLUMN + " INTEGER NOT NULL" +
                    ")";



    private static final String TIMETABLE_TRAININGS_TABLE = "Timetable_trainings";
    private static final String TIMETABLE_TRAININGS_ID_COLUMN = "id";
    private static final String TIMETABLE_TRAININGS_DATE_COLUMN = "date";
    private static final String TIMETABLE_TRAININGS_DESCRIPTION_COLUMN = "description";
    private static final String CREATE_TABLE_TIMETABLE_TRAININGS =
            "CREATE TABLE " + TIMETABLE_TRAININGS_TABLE +
                    " (" +
                    TIMETABLE_TRAININGS_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TIMETABLE_TRAININGS_DATE_COLUMN + " TEXT NOT NULL REFERENCES " + DATES_TABLE +", " +
                    TIMETABLE_TRAININGS_DESCRIPTION_COLUMN + " TEXT NOT NULL" +
                    ")";





//    public static DBHelper getInstance(Context context)
//    {
//        if(instance == null)
//        {
//            instance = new DBHelper(context);
//        }
//        return instance;
//    }
//
//    private DBHelper(Context context)
//    {
//        super(context,DB_NAME,null,DB_VERSION);
//    }

    DBHelper(Context context)
    {
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
    }

    public static String getProductCategoriesTable()
    {
        return PRODUCT_CATEGORIES_TABLE;
    }

    public static String getProductCategoriesNameColumn()
    {
        return PRODUCT_CATEGORIES_NAME_COLUMN;
    }

    public static String getProductTable()
    {
        return PRODUCT_TABLE;
    }

    public static String getProductNameColumn()
    {
        return PRODUCT_NAME_COLUMN;
    }

    public static String getProductCaloriesColumn()
    {
        return PRODUCT_CALORIES_COLUMN;
    }

    public static String getProductCategoryColumn()
    {
        return PRODUCT_CATEGORY_COLUMN;
    }

    public static String getIntervalTimersTable()
    {
        return INTERVAL_TIMERS_TABLE;
    }

    public static String getIntervalTimerIdColumn()
    {
        return INTERVAL_TIMER_ID_COLUMN;
    }

    public static String getIntervalTimerNameColumn()
    {
        return INTERVAL_TIMER_NAME_COLUMN;
    }

    public static String getIntervalTimerPrepareColumn()
    {
        return INTERVAL_TIMER_PREPARE_COLUMN;
    }

    public static String getIntervalTimerWorkColumn()
    {
        return INTERVAL_TIMER_WORK_COLUMN;
    }

    public static String getIntervalTimerRestColumn()
    {
        return INTERVAL_TIMER_REST_COLUMN;
    }

    public static String getIntervalTimerExercisesColumn()
    {
        return INTERVAL_TIMER_EXERCISES_COLUMN;
    }

    public static String getIntervalTimerSetsColumn()
    {
        return INTERVAL_TIMER_SETS_COLUMN;
    }

    public static String getIntervalTimerSetsRestColumn()
    {
        return INTERVAL_TIMER_SETS_REST_COLUMN;
    }

    public static String getIntervalTimerCoolingColumn()
    {
        return INTERVAL_TIMER_COOLING_COLUMN;
    }

    public static String getExercisesCategoriesTable()
    {
        return EXERCISES_CATEGORIES_TABLE;
    }

    public static String getExercisesCategoriesNameColumn()
    {
        return EXERCISES_CATEGORIES_NAME_COLUMN;
    }

    public static String getExercisesTable()
    {
        return EXERCISES_TABLE;
    }

    public static String getExerciseNameColumn()
    {
        return EXERCISE_NAME_COLUMN;
    }

    public static String getExerciseCategoryColumn()
    {
        return EXERCISE_CATEGORY_COLUMN;
    }

    public static String getExerciseDescriptionColumn()
    {
        return EXERCISE_DESCRIPTION_COLUMN;
    }

    public static String getNotesTable()
    {
        return NOTES_TABLE;
    }

    public static String getNoteDescriptionColumn()
    {
        return NOTE_DESCRIPTION_COLUMN;
    }

    public static String getNoteDateColumn()
    {
        return NOTE_DATE_COLUMN;
    }

    public static String getNoteTimeColumn()
    {
        return NOTE_TIME_COLUMN;
    }

    public static String getDailyCaloriesDetailsTable()
    {
        return DAILY_CALORIES_DETAILS_TABLE;
    }

    public static String getDailyCaloriesDetailsIdColumn()
    {
        return DAILY_CALORIES_DETAILS_ID_COLUMN;
    }

    public static String getDailyCaloriesDetailsDateColumn()
    {
        return DAILY_CALORIES_DETAILS_DATE_COLUMN;
    }

    public static String getDailyCaloriesDetailsNameColumn()
    {
        return DAILY_CALORIES_DETAILS_NAME_COLUMN;
    }

    public static String getDailyCaloriesDetailsAmountColumn()
    {
        return DAILY_CALORIES_DETAILS_AMOUNT_COLUMN;
    }

    public static String getDailyCaloriesDetailsCaloriesColumn()
    {
        return DAILY_CALORIES_DETAILS_CALORIES_COLUMN;
    }

    public static String getIntervalWorkoutsTable()
    {
        return INTERVAL_WORKOUTS_TABLE;
    }

    public static String getIntervalWorkoutsNameColumn()
    {
        return INTERVAL_WORKOUTS_NAME_COLUMN;
    }

    public static String getSimpleWorkoutsTable()
    {
        return SIMPLE_WORKOUTS_TABLE;
    }

    public static String getSimpleWorkoutsNameColumn()
    {
        return SIMPLE_WORKOUTS_NAME_COLUMN;
    }

    public static String getIntervalWorkoutsExercisesTable()
    {
        return INTERVAL_WORKOUTS_EXERCISES_TABLE;
    }

    public static String getIntervalWorkoutsExercisesIdColumn()
    {
        return INTERVAL_WORKOUTS_EXERCISES_ID_COLUMN;
    }

    public static String getIntervalWorkoutsExercisesNameColumn()
    {
        return INTERVAL_WORKOUTS_EXERCISES_NAME_COLUMN;
    }

    public static String getIntervalWorkoutsExercisesExerciseColumn()
    {
        return INTERVAL_WORKOUTS_EXERCISES_EXERCISE_COLUMN;
    }

    public static String getSimpleWorkoutsExercisesTable()
    {
        return SIMPLE_WORKOUTS_EXERCISES_TABLE;
    }

    public static String getSimpleWorkoutsExercisesIdColumn()
    {
        return SIMPLE_WORKOUTS_EXERCISES_ID_COLUMN;
    }

    public static String getSimpleWorkoutsExercisesNameColumn()
    {
        return SIMPLE_WORKOUTS_EXERCISES_NAME_COLUMN;
    }

    public static String getSimpleWorkoutsExercisesExerciseColumn()
    {
        return SIMPLE_WORKOUTS_EXERCISES_EXERCISE_COLUMN;
    }

    public static String getSimpleWorkoutsExercisesRepsColumn()
    {
        return SIMPLE_WORKOUTS_EXERCISES_REPS_COLUMN;
    }

    public static String getTimetableTrainingsTable()
    {
        return TIMETABLE_TRAININGS_TABLE;
    }

    public static String getTimetableTrainingsIdColumn()
    {
        return TIMETABLE_TRAININGS_ID_COLUMN;
    }

    public static String getTimetableTrainingsDateColumn()
    {
        return TIMETABLE_TRAININGS_DATE_COLUMN;
    }

    public static String getTimetableTrainingsDescriptionColumn()
    {
        return TIMETABLE_TRAININGS_DESCRIPTION_COLUMN;
    }

    public static String getDatesTable()
    {
        return DATES_TABLE;
    }

    public static String getDatesDate()
    {
        return DATES_DATE;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_PRODUCT_CATEGORIES);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_INTERVAL_TIMERS);
        db.execSQL(CREATE_TABLE_EXERCISES_CATEGORIES);
        db.execSQL(CREATE_TABLE_EXERCISES);
        db.execSQL(CREATE_TABLE_NOTES);
        db.execSQL(CREATE_TABLE_DAILY_CALORIES_DETAILS);
        db.execSQL(CREATE_TABLE_INTERVAL_WORKOUTS);
        db.execSQL(CREATE_TABLE_INTERVAL_WORKOUTS_EXERCISES);
        db.execSQL(CREATE_TABLE_SIMPLE_WORKOUTS);   // dodać przykład
        db.execSQL(CREATE_TABLE_SIMPLE_WORKOUTS_EXERCISES);     // dodać przykład
        db.execSQL(CREATE_TABLE_TIMETABLE_TRAININGS);   // dodać przykład
        db.execSQL(CREATE_TABLE_DATES);     // dodać przykład

        db.execSQL("INSERT INTO " + PRODUCT_CATEGORIES_TABLE + " VALUES('Wszystkie')");
        db.execSQL("INSERT INTO " + PRODUCT_CATEGORIES_TABLE + " VALUES('Mięso')");
        db.execSQL("INSERT INTO " + PRODUCT_CATEGORIES_TABLE + " VALUES('Nabiał')");
        db.execSQL("INSERT INTO " + PRODUCT_CATEGORIES_TABLE + " VALUES('Napoje')");
        db.execSQL("INSERT INTO " + PRODUCT_CATEGORIES_TABLE + " VALUES('Owoce')");
        db.execSQL("INSERT INTO " + PRODUCT_CATEGORIES_TABLE + " VALUES('Pieczywo')");
        db.execSQL("INSERT INTO " + PRODUCT_CATEGORIES_TABLE + " VALUES('Produkty zbożowe')");
        db.execSQL("INSERT INTO " + PRODUCT_CATEGORIES_TABLE + " VALUES('Słodycze, przekąski')");
        db.execSQL("INSERT INTO " + PRODUCT_CATEGORIES_TABLE + " VALUES('Warzywa')");
        db.execSQL("INSERT INTO " + PRODUCT_CATEGORIES_TABLE + " VALUES('Inne')");

        db.execSQL("INSERT INTO " + EXERCISES_CATEGORIES_TABLE + " VALUES('Ręce')");
        db.execSQL("INSERT INTO " + EXERCISES_CATEGORIES_TABLE + " VALUES('Nogi')");
        db.execSQL("INSERT INTO " + EXERCISES_CATEGORIES_TABLE + " VALUES('Abs')");
        db.execSQL("INSERT INTO " + EXERCISES_CATEGORIES_TABLE + " VALUES('Plecy')");
        db.execSQL("INSERT INTO " + EXERCISES_CATEGORIES_TABLE + " VALUES('Inne')");

        db.execSQL("INSERT INTO " + INTERVAL_TIMERS_TABLE + " ('timer_name','PREPARE','WORK','REST','EXERCISES','SETS','SETS_REST','COOLING')" +
                " VALUES('Przykładowy plan',5,15,10,6,3,0,30)");

        db.execSQL("INSERT INTO " + EXERCISES_TABLE + " VALUES('Pompki','Ręce','')");
        db.execSQL("INSERT INTO " + EXERCISES_TABLE + " VALUES('Przeskoki do wykroku','Nogi','')");
        db.execSQL("INSERT INTO " + EXERCISES_TABLE + " VALUES('Przysiad + wyskok','Nogi','')");
        db.execSQL("INSERT INTO " + EXERCISES_TABLE + " VALUES('Skip A','Nogi','')");
        db.execSQL("INSERT INTO " + EXERCISES_TABLE + " VALUES('Skip B','Nogi','')");
        db.execSQL("INSERT INTO " + EXERCISES_TABLE + " VALUES('Wykroki','Nogi','')");
        db.execSQL("INSERT INTO " + EXERCISES_TABLE + " VALUES('Rowerek','Abs','')");
        db.execSQL("INSERT INTO " + EXERCISES_TABLE + " VALUES('Russian twist','Abs','')");
        db.execSQL("INSERT INTO " + EXERCISES_TABLE + " VALUES('Plank','Abs','')");
        db.execSQL("INSERT INTO " + EXERCISES_TABLE + " VALUES('Burpees','Inne','')");

        db.execSQL("INSERT INTO " + INTERVAL_WORKOUTS_TABLE + " VALUES('Przykładowy zestaw')");

        db.execSQL("INSERT INTO " + INTERVAL_WORKOUTS_EXERCISES_TABLE + " ('workout_NAME','EXERCISE_name') VALUES('Przykładowy zestaw','Pompki')");
        db.execSQL("INSERT INTO " + INTERVAL_WORKOUTS_EXERCISES_TABLE + " ('workout_NAME','EXERCISE_name') VALUES('Przykładowy zestaw','Burpees')");
        db.execSQL("INSERT INTO " + INTERVAL_WORKOUTS_EXERCISES_TABLE + " ('workout_NAME','EXERCISE_name') VALUES('Przykładowy zestaw','Rowerek')");
        db.execSQL("INSERT INTO " + INTERVAL_WORKOUTS_EXERCISES_TABLE + " ('workout_NAME','EXERCISE_name') VALUES('Przykładowy zestaw','Wykroki')");
        db.execSQL("INSERT INTO " + INTERVAL_WORKOUTS_EXERCISES_TABLE + " ('workout_NAME','EXERCISE_name') VALUES('Przykładowy zestaw','Skip A')");

        db.execSQL("INSERT INTO " + SIMPLE_WORKOUTS_TABLE + " VALUES('Przykładowy zestaw')");

        db.execSQL("INSERT INTO " + SIMPLE_WORKOUTS_EXERCISES_TABLE + " ('workout_NAME','EXERCISE_name') VALUES('Przykładowy zestaw','Skip B')");
        db.execSQL("INSERT INTO " + SIMPLE_WORKOUTS_EXERCISES_TABLE + " ('workout_NAME','EXERCISE_name') VALUES('Przykładowy zestaw','Burpees')");
        db.execSQL("INSERT INTO " + SIMPLE_WORKOUTS_EXERCISES_TABLE + " ('workout_NAME','EXERCISE_name') VALUES('Przykładowy zestaw','Rowerek')");
        db.execSQL("INSERT INTO " + SIMPLE_WORKOUTS_EXERCISES_TABLE + " ('workout_NAME','EXERCISE_name') VALUES('Przykładowy zestaw','Pompki')");
        db.execSQL("INSERT INTO " + SIMPLE_WORKOUTS_EXERCISES_TABLE + " ('workout_NAME','EXERCISE_name') VALUES('Przykładowy zestaw','Skip A')");

        db.execSQL("INSERT INTO " + DATES_TABLE + " VALUES('2018-05-11')");

        db.execSQL("INSERT INTO " + NOTES_TABLE + " VALUES('2018-05-11','11:22:33','Przykładowa notatka nie służąca niczemu prócz demonstracji :)')");

        readProducts(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_CATEGORIES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INTERVAL_TIMERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISES_CATEGORIES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EXERCISES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DAILY_CALORIES_DETAILS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INTERVAL_WORKOUTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + INTERVAL_WORKOUTS_EXERCISES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SIMPLE_WORKOUTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SIMPLE_WORKOUTS_EXERCISES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TIMETABLE_TRAININGS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DATES_TABLE);
        onCreate(db);





    }

    public void readProducts(SQLiteDatabase db)
    {
        InputStream inputStream = context.getResources().openRawResource(R.raw.products);
        BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
        String eachline = null;
        try
        {
            eachline = bufferedReader.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        while (eachline != null) {
            // `the words in the file are separated by space`, so to get each words
            String[] words = eachline.split(",");
            db.execSQL("INSERT INTO " + PRODUCT_TABLE + " VALUES('" + words[0] +"'," + Integer.parseInt(words[1]) + ",'" + words[2] + "')");
            try
            {
                eachline = bufferedReader.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


}
