package com.node_coyote.bakerscorner.widget;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.node_coyote.bakerscorner.R;
import com.node_coyote.bakerscorner.ingredients.IngredientContract.IngredientEntry;

import static com.node_coyote.bakerscorner.ingredients.IngredientContract.BASE_CONTENT_URI;
import static com.node_coyote.bakerscorner.ingredients.IngredientContract.PATH_INGREDIENT;
import static com.node_coyote.bakerscorner.widget.CurrentRecipeContract.PATH_CURRENT;

/**
 * Created by node_coyote on 7/24/17.
 */

public class IngredientRemoteViewsService extends RemoteViewsService {

    int currentId;

    private String[] INGREDIENT_PROJECTION = {
            IngredientEntry._ID,
            IngredientEntry.COLUMN_INGREDIENT_ID,
            IngredientEntry.COLUMN_QUANTITY,
            IngredientEntry.COLUMN_MEASURE,
            IngredientEntry.COLUMN_INGREDIENT

    };

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new RemoteViewsFactory() {

            private Cursor ingredientsCursor = null;
            private int currentId = 1;

            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {

                if (ingredientsCursor != null) {
                    ingredientsCursor.close();
                }

                Uri CURRENT_URI = com.node_coyote.bakerscorner.widget.CurrentRecipeContract.BASE_CONTENT_URI.buildUpon().appendPath(PATH_CURRENT).build();

                String[] currentProjection = {CurrentRecipeContract.CurrentRecipeEntry.COLUMN_CURRENT_RECIPE_ID};
                Cursor currentRecipeCursor = getApplicationContext().getContentResolver().query(
                        CURRENT_URI,
                        currentProjection,
                        null,
                        null,
                        null
                );

                if (currentRecipeCursor != null && currentRecipeCursor.getCount() > 0) {
                    currentRecipeCursor.moveToFirst();
                    int columnIndex = currentRecipeCursor.getColumnIndex(CurrentRecipeContract.CurrentRecipeEntry.COLUMN_CURRENT_RECIPE_ID);
                    currentId = currentRecipeCursor.getInt(columnIndex);
                    currentRecipeCursor.close();
                }


                Uri ingredientsUri = BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT).build();
                String selection = " ingredients_id = " + currentId;
                ingredientsCursor = getContentResolver().query(
                        ingredientsUri,
                        INGREDIENT_PROJECTION,
                        selection,
                        null,
                        null
                );
            }

            @Override
            public void onDestroy() {
                if (ingredientsCursor != null) {
                    ingredientsCursor.close();
                    ingredientsCursor = null;
                }
            }

            @Override
            public int getCount() {
                return ingredientsCursor == null ? 0 : ingredientsCursor.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION
                        || ingredientsCursor == null
                        || !ingredientsCursor.moveToPosition(position)) {
                    return null;
                }

                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_ingredient_list_item);

                if (ingredientsCursor != null && ingredientsCursor.getCount() > 0) {
                    if (ingredientsCursor.moveToPosition(position)) {
                            int ingredientNameColumnIndex = ingredientsCursor.getColumnIndex(IngredientEntry.COLUMN_INGREDIENT);
                            int measureColumnIndex = ingredientsCursor.getColumnIndex(IngredientEntry.COLUMN_MEASURE);
                            int quantityColumnIndex = ingredientsCursor.getColumnIndex(IngredientEntry.COLUMN_QUANTITY);

                            String ingredient = ingredientsCursor.getString(ingredientNameColumnIndex);
                            String measure = ingredientsCursor.getString(measureColumnIndex);
                            int quantity = ingredientsCursor.getInt(quantityColumnIndex);

                            views.setTextViewText(R.id.widget_list_item_ingredient, ingredient);
                            views.setTextViewText(R.id.widget_list_item_measure, measure);
                            views.setTextViewText(R.id.widget_list_item_quantity, String.valueOf(quantity));
                    }
                }

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_ingredient_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }


            @Override
            public long getItemId(int position) {
                if (ingredientsCursor.moveToPosition(position)) {
                    int column = ingredientsCursor.getColumnIndex(IngredientEntry.COLUMN_INGREDIENT_ID);
                    return ingredientsCursor.getInt(column);
                }
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }


}
