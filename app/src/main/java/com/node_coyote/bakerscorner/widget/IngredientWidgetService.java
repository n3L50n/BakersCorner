package com.node_coyote.bakerscorner.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.node_coyote.bakerscorner.R;
import com.node_coyote.bakerscorner.ingredients.IngredientContract.IngredientEntry;
import com.node_coyote.bakerscorner.recipes.RecipeContract;

import static com.node_coyote.bakerscorner.ingredients.IngredientContract.BASE_CONTENT_URI;
import static com.node_coyote.bakerscorner.ingredients.IngredientContract.PATH_INGREDIENT;
import static com.node_coyote.bakerscorner.recipes.RecipeContract.PATH_RECIPE;
import static com.node_coyote.bakerscorner.widget.CurrentRecipeContract.PATH_CURRENT;
/**
 * Created by node_coyote on 7/21/17.
 */

public class IngredientWidgetService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENTS_WIDGET = "com.node_coyote.bakerscorner.action.update_ingredients_widgets";
    private static final int CURRENT_ID_COLUMN = 0;

    /**
     * Creates an IntentService.  Invoked by subclass's constructor.
     */
    public IngredientWidgetService() {
        super("IngredientWidgetService");
    }

    public static void startActionUpdateIngredientWidgets(Context context) {
        Intent intent = new Intent(context, IngredientWidgetService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS_WIDGET);
        context.startService(intent);
    }

    private void handleActionUpdateIngredientWidgets() {

        Uri CURRENT_URI = com.node_coyote.bakerscorner.widget.CurrentRecipeContract.BASE_CONTENT_URI.buildUpon().appendPath(PATH_CURRENT).build();

        String[] currentProjection = {
                CurrentRecipeContract.CurrentRecipeEntry.COLUMN_CURRENT_RECIPE_ID
        };

        Cursor currentRecipeCursor = getContentResolver().query(
                CURRENT_URI,
                currentProjection,
                null,
                null,
                null
        );


        int currentId;
        if (currentRecipeCursor != null && currentRecipeCursor.getCount() > 0) {
            currentRecipeCursor.moveToFirst();
            currentId = currentRecipeCursor.getInt(CURRENT_ID_COLUMN);
            currentRecipeCursor.close();
        } else {
            return;
        }

        Uri RECIPE_NAME_URI = com.node_coyote.bakerscorner.recipes.RecipeContract.BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build();
        String[] recipeNameProjection = {RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME};
        Cursor recipeCursor =  getContentResolver().query(
                RECIPE_NAME_URI,
                recipeNameProjection,
                null,
                null,
                null);

        String recipeName;
        if (recipeCursor != null && recipeCursor.getCount() > 0) {
            recipeCursor.moveToPosition(currentId - 1);
            int columnIndex = recipeCursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME);
            recipeName = recipeCursor.getString(columnIndex);
            recipeCursor.close();
        } else {
            return;
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientWidgetProvider.class));
        IngredientWidgetProvider.updateIngredientWidgets(this, appWidgetManager, recipeName, appWidgetIds);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredients_list_view);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {
            final String action = intent.getAction();

            if (ACTION_UPDATE_INGREDIENTS_WIDGET.equals(action)) {
                handleActionUpdateIngredientWidgets();
            }
        }
    }
}
