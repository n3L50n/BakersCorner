package com.node_coyote.bakerscorner.steps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.node_coyote.bakerscorner.steps.StepContract.StepEntry;

/**
 * Created by node_coyote on 6/6/17.
 */

public class StepDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "steps";

    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_STEPS_TABLE =
            " CREATE TABLE " + DATABASE_NAME + " ("
            + StepEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StepEntry.COLUMN_STEP_ID + " INTEGER, "
            + StepEntry.COLUMN_SHORT_DESCRIPTION + " TEXT, "
            + StepEntry.COLUMN_DESCRIPTION + " TEXT, "
            + StepEntry.COLUMN_VIDEO_URL + " TEXT, "
            + StepEntry.COLUMN_THUMBNAIL_URL + " TEXT, "
            + StepEntry.COLUMN_STEP_RECIPE_ID + " INTEGER);";

    public StepDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STEPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
