package com.node_coyote.bakerscorner.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.node_coyote.bakerscorner.R;
import com.node_coyote.bakerscorner.recipes.RecipeActivity;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, String recipeName,
                                int appWidgetId) {

        Intent intent = new Intent(context, RecipeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Construct the RemoteViews object
        RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
        widget.setTextViewText(R.id.widget_recipe_name_text_view, recipeName);
        widget.setOnClickPendingIntent(R.id.ingredient_widget, pendingIntent);
        widget.setRemoteAdapter(R.id.widget_ingredients_list_view, new Intent(context, IngredientRemoteViewsService.class));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, widget);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        Log.v("UPDATING", "YUP");
        IngredientWidgetService.startActionUpdateIngredientWidgets(context);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredients_list_view);
    }

    public static void updateIngredientWidgets(Context context, AppWidgetManager appWidgetManager, String recipeName, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipeName, appWidgetId);
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

