package com.node_coyote.bakerscorner.ingredients;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.node_coyote.bakerscorner.R;

/**
 * Created by node_coyote on 6/8/17.
 */

class IngredientAdapter extends ArrayAdapter<String> {

    Cursor cursor;

    IngredientAdapter(@NonNull Context context) {
        super(context, 0);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View ingredientView, @NonNull ViewGroup parent) {

        if (ingredientView != null) {
            ingredientView = LayoutInflater.from(getContext()).inflate(R.layout.ingredient_list_item, parent, false);
        }

        cursor.moveToPosition(position);

        int ingredientColumnIndex = cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_INGREDIENT);
        String ingredient = cursor.getString(ingredientColumnIndex);

        int quantityColumnIndex = cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_QUANTITY);
        String quantity = cursor.getString(quantityColumnIndex);

        int measureColumnIndex = cursor.getColumnIndex(IngredientContract.IngredientEntry.COLUMN_MEASURE);
        String measure = cursor.getString(measureColumnIndex);
        Log.v("STRANG", ingredient);

        cursor.close();

        // TODO Butterknife
        TextView quantityAmountView = (TextView) ingredientView.findViewById(R.id.quantity_text_view);
        TextView measureAmountView = (TextView) ingredientView.findViewById(R.id.measure_text_view);
        TextView ingredientNameView = (TextView) ingredientView.findViewById(R.id.ingredient_text_view);

        ingredientNameView.setText(ingredient);
        quantityAmountView.setText(quantity);
        measureAmountView.setText(measure);

        return ingredientView;
    }

    void swapIngredientCursor(Cursor newCursor) {
        cursor = newCursor;
    }
}
