package com.node_coyote.bakerscorner.ingredients;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.node_coyote.bakerscorner.R;
import com.node_coyote.bakerscorner.ingredients.IngredientContract.IngredientEntry;
import com.node_coyote.bakerscorner.recipes.RecipeContract.RecipeEntry;

/**
 * Created by node_coyote on 6/23/17.
 */

class IngredientCursorAdapter extends RecyclerView.Adapter<IngredientCursorAdapter.IngredientAdapterViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    IngredientCursorAdapter(Context context) {
        mContext = context;
    }

    @Override
    public IngredientAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View ingredientView = LayoutInflater.from(mContext).inflate(R.layout.ingredient_list_item, parent, false);

        return new IngredientAdapterViewHolder(ingredientView);
    }

    @Override
    public void onBindViewHolder(IngredientAdapterViewHolder holder, int position) {

                mCursor.moveToPosition(position);

                int ingredientColumnIndex = mCursor.getColumnIndex(IngredientEntry.COLUMN_INGREDIENT);
                String ingredient = mCursor.getString(ingredientColumnIndex);

                int quantityColumnIndex = mCursor.getColumnIndex(IngredientEntry.COLUMN_QUANTITY);
                int quantity = mCursor.getInt(quantityColumnIndex);

                int measureColumnIndex = mCursor.getColumnIndex(IngredientEntry.COLUMN_MEASURE);
                String measure = mCursor.getString(measureColumnIndex);
                Log.v("Binding", ingredient);

                // TODO Butterknife
                holder.mIngredientNameView.setText(ingredient);
                holder.mQuantityAmountView.setText(String.valueOf(quantity));
                holder.mMeasureAmountView.setText(measure);

    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    void swapIngredientCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    class IngredientAdapterViewHolder extends RecyclerView.ViewHolder {

        final TextView mQuantityAmountView;
        final TextView mMeasureAmountView;
        final TextView mIngredientNameView;

        IngredientAdapterViewHolder(View itemView) {
            super(itemView);
            mQuantityAmountView = (TextView) itemView.findViewById(R.id.quantity_text_view);
            mMeasureAmountView = (TextView) itemView.findViewById(R.id.measure_text_view);
            mIngredientNameView = (TextView) itemView.findViewById(R.id.ingredient_text_view);
        }
    }
}
