package com.node_coyote.bakerscorner.recipes;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.node_coyote.bakerscorner.R;
import com.node_coyote.bakerscorner.ingredients.IngredientContract.IngredientEntry;
import com.node_coyote.bakerscorner.ingredients.IngredientsFragment;
import com.node_coyote.bakerscorner.recipes.RecipeContract.RecipeEntry;
import com.node_coyote.bakerscorner.steps.StepContract.StepEntry;
import com.node_coyote.bakerscorner.steps.StepsFragment;

/**
 * Created by node_coyote on 6/20/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private ContentValues[] mRecipeData;
    private Cursor mCursor;
    private static final String RECIPE_ID_KEY = "RECIPE ID";
    private static final String ROW_ID_KEY = "ROW_ID";
    private static RecipeOnClickHandler mRecipeClickHandler = null;


    RecipeAdapter(RecipeOnClickHandler  handler){
        mRecipeClickHandler = handler;
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeAdapterViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int columnRecipeNameIndex = mCursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_NAME);
        int columnServingsIndex = mCursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_SERVINGS);

        String recipeName = mCursor.getString(columnRecipeNameIndex);
        int servingsValue = mCursor.getInt(columnServingsIndex);
        // TODO clean this up
//        String servings =  getString(R.string.recipe_servings_text) + " " + String.valueOf(servingsValue);
        String servings =  " " + String.valueOf(servingsValue);

        holder.mRecipeNameView.setText(recipeName);
        holder.mServingsView.setText(servings);
    }

    @Override
    public int getItemCount() {
        // Check for empty data to prevent crash
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    void swapCursor(Cursor freshCursor) {
        mCursor = freshCursor;
        notifyDataSetChanged();
    }

    void setRecipeData(ContentValues[] recipeData) {
        mRecipeData = recipeData;
        notifyDataSetChanged();
    }

    interface RecipeOnClickHandler {
        void onClick(Uri recipeUri);
    }

    class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        final TextView mRecipeNameView;
        final TextView mServingsView;
        final Button mIngredientsButton;

        RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            mRecipeNameView = (TextView) itemView.findViewById(R.id.recipe_title_view);
            mServingsView = (TextView) itemView.findViewById(R.id.servings_view);
            // TODO work with onClick events more gracefully.

            // TODO change button to textView. Entire view will be a button.
            mIngredientsButton = (Button) itemView.findViewById(R.id.ingredients_button);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            Bundle bundle = new Bundle();

            int rowIdColumnIndex = mCursor.getColumnIndex(IngredientEntry._ID);
            long rowId = mCursor.getLong(rowIdColumnIndex);

            Uri uri = ContentUris.withAppendedId(IngredientEntry.CONTENT_URI, rowId);
            mRecipeClickHandler.onClick(uri);
            Log.v("SENDING ROW ID", String.valueOf(rowId));

            mCursor.moveToPosition(adapterPosition);
            int recipeId = mCursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_ID);

            // TODO recipeId unnecessary? Just use rowId?
            Log.v("SENDING RECIPE ID", String.valueOf(recipeId));

            // Send over our recipeId so our Ingredients and Steps fragments
            // can know which parts of the database to query, load cursor, and populate views.
            bundle.putInt(RECIPE_ID_KEY, recipeId);
            bundle.putLong(ROW_ID_KEY, rowId);

            // Go to our ViewPager with 2 fragments; Ingredients and Steps
            Intent intent = new Intent( v.getContext(), RecipeDetailActivity.class);
            intent.putExtras(bundle);
            v.getContext().startActivity(intent);
        }
    }
}
