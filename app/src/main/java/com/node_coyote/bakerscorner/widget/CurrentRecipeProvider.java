package com.node_coyote.bakerscorner.widget;

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

/**
 * Created by node_coyote on 7/24/17.
 */

public class CurrentRecipeProvider extends ContentProvider {

    private static final int CURRENT = 39;
    private static final int CURRENT_ID = 27;
    private static final UriMatcher sMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sMatcher.addURI(CurrentRecipeContract.CONTENT_AUTHORITY, CurrentRecipeContract.PATH_CURRENT, CURRENT);
        sMatcher.addURI(CurrentRecipeContract.CONTENT_AUTHORITY, CurrentRecipeContract.PATH_CURRENT + "/#", CURRENT_ID);
    }

    private CurrentRecipeDatabaseHelper mHelper;

    @Override
    public boolean onCreate() {
        mHelper = new CurrentRecipeDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mHelper.getReadableDatabase();
        Cursor cursor;
        int match = sMatcher.match(uri);
        switch (match) {
            case CURRENT:
                cursor = database.query(CurrentRecipeContract.CurrentRecipeEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CURRENT_ID:
                selection = CurrentRecipeContract.CurrentRecipeEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(CurrentRecipeContract.CurrentRecipeEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown uri" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sMatcher.match(uri);
        switch (match){
            case CURRENT:
                return CurrentRecipeContract.CurrentRecipeEntry.CONTENT_LIST_TYPE;
            case CURRENT_ID:
                return CurrentRecipeContract.CurrentRecipeEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown Uri" + uri + "with match" + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // Get a writable database to fill with ingredient data.
        final SQLiteDatabase database = mHelper.getWritableDatabase();

        switch (sMatcher.match(uri)) {
            case CURRENT:
                // bulkInsert
            case CURRENT_ID:

                // insert new ingredients with given values
                long id = database.insert(CurrentRecipeContract.CurrentRecipeEntry.TABLE_NAME, null, values);

                // Insertion fails if id is -1. Log it with error and return null
                if (id == -1) {
                    Log.v("Failed", "Failed to insert row for " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

//    @Override
//    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
//
//        // Get a writable database to fill with ingredient data goodies.
//        final SQLiteDatabase database = mHelper.getWritableDatabase();
//
//        switch (sMatcher.match(uri)) {
//            case CURRENT:
//                database.beginTransaction();
//                int rowsInserted = 0;
//                try {
//                    for (ContentValues value : values) {
//                        long _id = database.insert(CurrentRecipeContract.CurrentRecipeEntry.TABLE_NAME, null, value);
//                        Log.v("Failed", String.valueOf(_id));
//                        if (_id != -1) {
//                            rowsInserted++;
//                        }
//                    }
//                    database.setTransactionSuccessful();
//                } finally {
//                    database.endTransaction();
//                }
//
//                if (rowsInserted > 0) {
//                    getContext().getContentResolver().notifyChange(uri, null);
//                }
//
//                return rowsInserted;
//
//            default:
//                return super.bulkInsert(uri, values);
//        }
//    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mHelper.getWritableDatabase();
        int updatedRows = database.update(CurrentRecipeContract.CurrentRecipeEntry.TABLE_NAME, values, selection, selectionArgs);
        if (updatedRows != 0 ){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updatedRows;
    }
}
