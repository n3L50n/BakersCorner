package com.node_coyote.bakerscorner.steps;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by node_coyote on 6/6/17.
 */

public class StepContract {

    public StepContract(){}

    public static final String CONTENT_AUTHORITY = "com.node_coyote.bakerscorner.stepData";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_STEPS = "steps"; 

    public static final class StepEntry implements BaseColumns {
        
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(PATH_STEPS)
            .build(); 

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STEPS;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STEPS;

        public static final String TABLE_NAME = "steps"; 

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_STEP_ID = "id";

        public static final String COLUMN_SHORT_DESCRIPTION = "shortDescription"; 

        public static final String COLUMN_DESCRIPTION = "description"; 

        public static final String COLUMN_VIDEO_URL = "videoURL";

        public static final String COLUMN_THUMBNAIL_URL = "thumbnailURL"; 
    }
}
