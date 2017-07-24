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
import com.node_coyote.bakerscorner.recipes.RecipeContract;
import com.node_coyote.bakerscorner.widget.CurrentRecipeContract;

import static com.node_coyote.bakerscorner.ingredients.IngredientContract.BASE_CONTENT_URI;
import static com.node_coyote.bakerscorner.ingredients.IngredientContract.PATH_INGREDIENT;
import static com.node_coyote.bakerscorner.recipes.RecipeContract.PATH_RECIPE;
import static com.node_coyote.bakerscorner.widget.CurrentRecipeContract.PATH_CURRENT;
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

        Uri CURRENT_URI = com.node_coyote.bakerscorner.widget.CurrentRecipeContract.BASE_CONTENT_URI.buildUpon().appendPath(PATH_CURRENT).build();

        String[] currentProjection = {CurrentRecipeContract.CurrentRecipeEntry.COLUMN_CURRENT_RECIPE_ID};
        Cursor ello = getContentResolver().query(
                CURRENT_URI,
                currentProjection,
                null,
                null,
                null
        );
        int currentId = 1;
        if (ello != null && ello.getCount() > 0) {
            ello.moveToFirst();
            int columnIndex = ello.getColumnIndex(CurrentRecipeContract.CurrentRecipeEntry.COLUMN_CURRENT_RECIPE_ID);
            currentId = ello.getInt(columnIndex);
            Log.v("ELLO", String.valueOf(currentId));
            ello.close();
        }

        Uri RECIPE_NAME_URI = com.node_coyote.bakerscorner.recipes.RecipeContract.BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build();
        String[] recipeNameProjection = {RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME};
        Cursor recipeCursor =  getContentResolver().query(
                RECIPE_NAME_URI,
                recipeNameProjection,
                null,
                null,
                null);

        String recipeName = "Recipe";
        if (recipeCursor != null && recipeCursor.getCount() > 0) {
            recipeCursor.moveToPosition(currentId - 1);
            int columnIndex = recipeCursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME);
            recipeName = recipeCursor.getString(columnIndex);
            Log.v("cursorWidgetUpdated", recipeName);

            recipeCursor.close();

        }

        Uri INGREDIENTS_URI = com.node_coyote.bakerscorner.ingredients.IngredientContract.BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT).build();
        Cursor ingredientCursor =  getContentResolver().query(
                INGREDIENTS_URI,
                INGREDIENT_PROJECTION,
                null,
                null,
                null);

        String ingredients = "Doh";

        if (ingredientCursor != null && ingredientCursor.getCount() > 0) {

            ingredientCursor.moveToPosition(currentId - 1);
            int ingredientNameColumnIndex = ingredientCursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_INGREDIENT);
            int measureColumnIndex = ingredientCursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_MEASURE);
            int quantityColumnIndex = ingredientCursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_QUANTITY);
            String ingredient = ingredientCursor.getString(ingredientNameColumnIndex);
            String measure = ingredientCursor.getString(measureColumnIndex);
            int quantity = ingredientCursor.getInt(quantityColumnIndex);
            ingredients = recipeName + "\n" + ingredient + " " + measure + " " + quantity + "\n";
            //TODO where position == ingredient id
            Log.v("cursorWidgetUpdated", ingredients);

            ingredientCursor.close();

        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidgetProvider.class));
        IngredientWidgetProvider.updateIngredientWidgets(this, appWidgetManager, ingredients, appWidgetIds);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_UPDATE_INGREDIENTS.equals(action)) {
                handleActionUpdateIngredients();
                Log.v("handling", action);

            } else if (ACTION_UPDATE_INGREDIENTS_WIDGET.equals(action)) {
                handleActionUpdateIngredientWidgets();
                Log.v("handling", action);

            }
        }
    }
}
