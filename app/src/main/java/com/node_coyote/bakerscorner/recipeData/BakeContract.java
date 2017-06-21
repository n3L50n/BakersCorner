package com.node_coyote.bakerscorner.recipeData;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by node_coyote on 6/6/17.
 */

public class BakeContract {
    public BakeContract(){}
    public static final String CONTENT_AUTHORITY = "com.node_coyote.bakerscorner.recipeData";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_RECIPE = "recipes";

    public static final class BakeEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_RECIPE)
                .build();

        /**
         *
         */
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECIPE;

        /**
         *
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECIPE;

        /**
         * The name of the database for recipes.
         */
        public static final String TABLE_NAME = "recipes";

        /**
         * Type: INTEGER
         * Unique identifier for a recipe(row) in the database
         */
        public static final String _ID = BaseColumns._ID;

        /**
         * Type: INTEGER
         */
        public static final String COLUMN_RECIPE_ID = "id";

        /**
         * Type: TEXT
         *
         */
        public static final String COLUMN_RECIPE_NAME = "name";

        /**
         * Type: INTEGER
         */
        public static final String COLUMN_RECIPE_INGREDIENTS_ID = "ingredients_id";

        /**
         * Type: INTEGER
         */
        public static final String COLUMN_RECIPE_STEPS_ID = "steps_recipe_id";

        /**
         * Type: INTEGER
         */
        public static final String COLUMN_RECIPE_SERVINGS = "servings";

        /**
         * Type: TEXT
         */
        public static final String COLUMN_RECIPE_IMAGE = "image";
    }
}
