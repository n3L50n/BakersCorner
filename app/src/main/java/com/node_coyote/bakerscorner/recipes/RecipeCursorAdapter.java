package com.node_coyote.bakerscorner.recipes;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.node_coyote.bakerscorner.R;
import com.node_coyote.bakerscorner.recipes.RecipeContract.RecipeEntry;

/**
 * Created by node_coyote on 6/20/17.
 */

public class RecipeCursorAdapter extends RecyclerView.Adapter<RecipeCursorAdapter.RecipeAdapterViewHolder> {

    private ContentValues[] mRecipeData;
    private Cursor mCursor;
    private static final String ROW_ID_KEY = "ROW_ID";
    private static final String RECIPE_NAME_KEY = "RECIPE_NAME";
    private static RecipeOnClickHandler mRecipeClickHandler = null;


    RecipeCursorAdapter(RecipeOnClickHandler  handler){
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

        RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            mRecipeNameView = (TextView) itemView.findViewById(R.id.recipe_title_view);
            mServingsView = (TextView) itemView.findViewById(R.id.servings_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View recipeView) {

            int adapterPosition = getAdapterPosition();
            Bundle bundle = new Bundle();
            mCursor.moveToPosition(adapterPosition);

            int rowIdColumnIndex = mCursor.getColumnIndex(RecipeEntry._ID);
            long rowId = mCursor.getLong(rowIdColumnIndex);

            int recipeNameColumnIndex = mCursor.getColumnIndex(RecipeEntry.COLUMN_RECIPE_NAME);
            String recipeName = mCursor.getString(recipeNameColumnIndex);

            if (recipeName != null) {
                bundle.putString(RECIPE_NAME_KEY , recipeName);
            }

            Uri uri = ContentUris.withAppendedId(RecipeEntry.CONTENT_URI, rowId);
            mRecipeClickHandler.onClick(uri);
            Log.v("SENDING ROW ID", String.valueOf(rowId));

            // Send over our recipeId so our Ingredients and Steps fragments
            // can know which parts of the database to query, load cursor, and populate views.
            bundle.putLong(ROW_ID_KEY, rowId);

            // Go to our ViewPager with 2 fragments; Ingredients and Steps
            Intent intent = new Intent( recipeView.getContext(), RecipeDetailActivity.class);
            intent.putExtras(bundle);
            recipeView.getContext().startActivity(intent);
        }
    }
}
