package com.node_coyote.bakerscorner;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.node_coyote.bakerscorner.ingredients.IngredientContract;

import static com.node_coyote.bakerscorner.ingredients.IngredientContract.BASE_CONTENT_URI;
import static com.node_coyote.bakerscorner.ingredients.IngredientContract.PATH_INGREDIENT;

/**
 * Created by node_coyote on 7/21/17.
 */

public class IngredientWidgetService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENTS = "com.node_coyote.bakerscorner.action.update_ingredients";
    public static final String ACTION_UPDATE_INGREDIENTS_WIDGET = "com.node_coyote.bakerscorner.action.update_ingredients_widgets";

    String[] INGREDIENT_PROJECTION = {
            IngredientContract.IngredientEntry._ID,
            IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ID,
            IngredientContract.IngredientEntry.COLUMN_QUANTITY,
            IngredientContract.IngredientEntry.COLUMN_MEASURE,
            IngredientContract.IngredientEntry.COLUMN_INGREDIENT

    };

    /**
     * Creates an IntentService.  Invoked by subclass's constructor.
     */
    public IngredientWidgetService() {
        super("IngredientWidgetService");
    }

    public static void startActionUpdateIngredients(Context context) {
        Intent intent = new Intent(context, IngredientWidgetService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        context.startService(intent);
    }

    public static void startActionUpdateIngredientWidgets(Context context) {
        Log.v("startingHandl", "HANDLIN");
        Intent intent = new Intent(context, IngredientWidgetService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS_WIDGET);
        Log.v("ActionUpdate", ACTION_UPDATE_INGREDIENTS_WIDGET);
        context.startService(intent);
    }

    private void handleActionUpdateIngredients() {
        Uri INGREDIENTS_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT).build();
        getContentResolver().query(
                INGREDIENTS_URI,
                INGREDIENT_PROJECTION,
                null,
                null,
                null);

    }

    private void handleActionUpdateIngredientWidgets() {
        Log.v("handleAction", "updatin");

        Uri INGREDIENTS_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT).build();
        Cursor cursor =  getContentResolver().query(
                INGREDIENTS_URI,
                INGREDIENT_PROJECTION,
                null,
                null,
                null);

        String ingredient = "Doh";
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_INGREDIENT);
            ingredient = cursor.getString(columnIndex);
            Log.v("WIDGET PROVDR", ingredient);

            cursor.close();

        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidgetProvider.class));
        IngredientWidgetProvider.updateIngredientWidgets(this, appWidgetManager, ingredient, appWidgetIds);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.v("handling", intent.getAction());
        handleActionUpdateIngredientWidgets();

//        if (intent != null) {
//            final String action = intent.getAction();
//
//            if (ACTION_UPDATE_INGREDIENTS.equals(action)) {
//                handleActionUpdateIngredients();
//            } else if (ACTION_UPDATE_INGREDIENTS_WIDGET.equals(action)) {
//                handleActionUpdateIngredientWidgets();
//            }
//        }
    }
}
