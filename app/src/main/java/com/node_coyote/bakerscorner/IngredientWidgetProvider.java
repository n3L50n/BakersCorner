package com.node_coyote.bakerscorner;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.node_coyote.bakerscorner.recipes.RecipeActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, String ingredient,
                                int appWidgetId) {

        Intent intent = new Intent(context, RecipeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
        views.setTextViewText(R.id.widget_recipe_name_text_view, ingredient);
        Log.v("WIDGET PROVDR", ingredient);
        views.setOnClickPendingIntent(R.id.ingredient_widget, pendingIntent);
        // Instruct the widget manager to update the widget

        Intent updateIntent = new Intent(context, IngredientWidgetService.class);
        updateIntent.setAction(IngredientWidgetService.ACTION_UPDATE_INGREDIENTS_WIDGET);
        PendingIntent ingredientPendingIntent = PendingIntent.getService(context, 0, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.ingredient_widget, ingredientPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.v("UPDATING", "YUP");
        IngredientWidgetService.startActionUpdateIngredientWidgets(context);
    }

    public static void updateIngredientWidgets(Context context, AppWidgetManager appWidgetManager, String ingredient, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, ingredient, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

