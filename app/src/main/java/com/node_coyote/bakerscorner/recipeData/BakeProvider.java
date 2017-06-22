package com.node_coyote.bakerscorner.recipeData;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.node_coyote.bakerscorner.recipeData.BakeContract.BakeEntry;

/**
 * Created by node_coyote on 6/6/17.
 */

public class BakeProvider extends ContentProvider {

    // A tag for log messages.
    public static final String LOG_TAG = BakeContract.BakeEntry.class.getSimpleName();

    // uri watcher code for the content uri for the recipe database.
    private static final int RECIPE = 42;

    // uri watcher code for the content uri for a single recipe in the recipes database.
    private static final int RECIPE_ID = 9;

    // UriMatcher object to match a content uri to a code.
    private static final UriMatcher sMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // The items to match with the uri matcher. Is it a recipe, or all recipes?
    static {
        sMatcher.addURI(BakeContract.CONTENT_AUTHORITY, BakeContract.PATH_RECIPE, RECIPE);
        sMatcher.addURI(BakeContract.CONTENT_AUTHORITY, BakeContract.PATH_RECIPE + "/#", RECIPE_ID);
    }

    private BakeDatabaseHelper mHelper;

    @Override
    public boolean onCreate() {
        mHelper = new BakeDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // We need a readable database to look at.
        SQLiteDatabase database = mHelper.getReadableDatabase();

        // We'll pack a cursor with schools for the roster.
        Cursor cursor;

        // Match uri to code.
        int match = sMatcher.match(uri);
        switch (match) {
            case RECIPE:
                // look at the whole roster of schools.
                cursor = database.query(BakeEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case RECIPE_ID:
                // query a row by id.
                // Add an additional parameter for an individual school in the database.
                selection = BakeEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(BakeEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown uri " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sMatcher.match(uri);
        switch (match) {
            case RECIPE:
                return BakeEntry.CONTENT_LIST_TYPE;
            case RECIPE_ID:
                return BakeEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown uri" + uri + "with match" + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // Get a writable database to fill with ingredient data.
        final SQLiteDatabase database = mHelper.getWritableDatabase();

        switch (sMatcher.match(uri)) {
            case RECIPE:
                // bulkInsert
            case RECIPE_ID:

                // insert new ingredients with given values
                long id = database.insert(BakeEntry.TABLE_NAME, null, values);

                // Insertion fails if id is -1. Log it with error and return null
                if (id == -1) {
                    Log.v(LOG_TAG, "Failed to insert row for " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        // Get a writable database to fill with ingredient data goodies.
        final SQLiteDatabase database = mHelper.getWritableDatabase();

        switch (sMatcher.match(uri)) {
            case RECIPE:
                database.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = database.insert(BakeEntry.TABLE_NAME, null, value);
                        Log.v(LOG_TAG, String.valueOf(_id));
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    database.setTransactionSuccessful();
                } finally {
                    database.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
