package com.node_coyote.bakerscorner.ingredients;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.node_coyote.bakerscorner.ingredients.IngredientContract.IngredientEntry;

/**
 * Created by node_coyote on 6/6/17.
 */

public class IngredientDatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "ingredients";

    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_INGREDIENTS_TABLE =
            " CREATE TABLE " + DATABASE_NAME + " ("
            + IngredientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + IngredientEntry.COLUMN_INGREDIENT + " TEXT, "
            + IngredientEntry.COLUMN_MEASURE + " TEXT, "
            + IngredientEntry.COLUMN_QUANTITY + " REAL) ";
            //+ IngredientEntry.COLUMN_INGREDIENT_ID + " INTEGER);";

    public IngredientDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_INGREDIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
