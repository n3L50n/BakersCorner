package com.node_coyote.bakerscorner.recipeData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.node_coyote.bakerscorner.recipeData.BakeContract.BakeEntry;

/**
 * Created by node_coyote on 6/6/17.
 */

public class BakeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "recipes";

    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_RECIPE_TABLE =
            " CREATE TABLE " + DATABASE_NAME + " ("
            + BakeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + BakeEntry.COLUMN_RECIPE_ID + " INTEGER, "
            + BakeEntry.COLUMN_RECIPE_NAME + " TEXT, "
            + BakeEntry.COLUMN_RECIPE_INGREDIENTS_ID + " INTEGER, "
            + BakeEntry.COLUMN_RECIPE_STEPS_ID + " INTEGER, "
            + BakeEntry.COLUMN_RECIPE_SERVINGS + " INTEGER, "
            + BakeEntry.COLUMN_RECIPE_IMAGE + " TEXT);";

    public BakeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RECIPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
