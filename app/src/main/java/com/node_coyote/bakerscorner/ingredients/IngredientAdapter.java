package com.node_coyote.bakerscorner.ingredients;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.node_coyote.bakerscorner.R;

/**
 * Created by node_coyote on 6/8/17.
 */

public class IngredientAdapter extends ArrayAdapter<String> {

    public IngredientAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View ingredientView, @NonNull ViewGroup parent) {

        if (ingredientView != null) {
            ingredientView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_ingredients, parent, false);
        }

        // TODO Butterknife
        TextView quantity = (TextView) ingredientView.findViewById(R.id.quantity_text_view);
        quantity.setText("42");

        TextView measure = (TextView) ingredientView.findViewById(R.id.measure_text_view);
        measure.setText("CUP");

        TextView ingredient = (TextView) ingredientView.findViewById(R.id.ingredient_text_view);
        ingredient.setText("unsalted butter");

        return ingredientView;
    }
}
