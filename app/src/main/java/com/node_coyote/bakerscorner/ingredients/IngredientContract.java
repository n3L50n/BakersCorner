package com.node_coyote.bakerscorner.ingredients;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by node_coyote on 6/6/17.
 */

public class IngredientContract {

        public IngredientContract(){}
     
        public static final String CONTENT_AUTHORITY = "com.node_coyote.bakerscorner.ingredients";

        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        public static final String PATH_INGREDIENT = "ingredient";

        public static final class IngredientEntry implements BaseColumns {

            public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_INGREDIENT)
                    .build();

            /**
             *
             */
            public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT;

            /**
             *
             */
            public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT;

            /**
             * The name of the database for ingredients.
             */
            public static final String TABLE_NAME = "ingredients";

            /**
             * Type: INTEGER
             * Unique identifier for a set of ingredients (row) in the database
             */
            public static final String _ID = BaseColumns._ID;
        
            public static final String COLUMN_INGREDIENT_ID = "ingredients_id";

            public static final String COLUMN_QUANTITY = "quantity";

            public static final String COLUMN_MEASURE = "measure";

            public static final String COLUMN_INGREDIENT = "ingredient";
        }
}
