package com.node_coyote.bakerscorner.widget;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by node_coyote on 7/24/17.
 */

public class CurrentRecipeContract {
    public CurrentRecipeContract(){}
    public static final String CONTENT_AUTHORITY = "com.node_coyote.bakerscorner.widget";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CURRENT = "current";
    public static final class CurrentRecipeEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_CURRENT)
                .build();
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CURRENT;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CURRENT;

        /**
         * The name of the database for current widget recipe tracking.
         */
        public static final String TABLE_NAME = "current";

        /**
         * Type: INTEGER
         * Unique identifier for the recipe to provide to the widget in the database
         */
        public static final String _ID = BaseColumns._ID;

        /**
         * Type: INTEGER
         */
        public static final String COLUMN_CURRENT_RECIPE_ID = "current_id";

    }
}

