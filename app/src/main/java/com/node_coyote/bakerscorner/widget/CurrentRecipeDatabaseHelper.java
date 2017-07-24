package com.node_coyote.bakerscorner.widget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by node_coyote on 7/24/17.
 */

public class CurrentRecipeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "current";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_CURRENT_RECIPE_TABLE =
            " CREATE TABLE " + DATABASE_NAME + " ("
            + CurrentRecipeContract.CurrentRecipeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CurrentRecipeContract.CurrentRecipeEntry.COLUMN_CURRENT_RECIPE_ID + " INTEGER NOT NULL DEFAULT 1);";

    public CurrentRecipeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CURRENT_RECIPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
