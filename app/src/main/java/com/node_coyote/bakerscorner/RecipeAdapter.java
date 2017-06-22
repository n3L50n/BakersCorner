package com.node_coyote.bakerscorner;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.node_coyote.bakerscorner.recipeData.BakeContract.BakeEntry;
import com.node_coyote.bakerscorner.viewFragments.IngredientsFragment;

/**
 * Created by node_coyote on 6/20/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private final Context mContext;
    private ContentValues[] mRecipeData;
    private Cursor mCursor;

    RecipeAdapter(Context context){
        mContext = context;
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.recipe_item, parent, false);
        return new RecipeAdapterViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int columnRecipeNameIndex = mCursor.getColumnIndex(BakeEntry.COLUMN_RECIPE_NAME);
        int columnServingsIndex = mCursor.getColumnIndex(BakeEntry.COLUMN_RECIPE_SERVINGS);
        String recipeName = mCursor.getString(columnRecipeNameIndex);
        int servingsValue = mCursor.getInt(columnServingsIndex);
        String servings = mContext.getString(R.string.recipe_servings_text) + " " + String.valueOf(servingsValue);
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

    class RecipeAdapterViewHolder extends RecyclerView.ViewHolder{

        final TextView mRecipeNameView;
        final TextView mServingsView;
        final Button mIngredientsButton;

        RecipeAdapterViewHolder(View itemView) {
            super(itemView);
            mRecipeNameView = (TextView) itemView.findViewById(R.id.recipe_title_view);
            mServingsView = (TextView) itemView.findViewById(R.id.servings_view);
            // TODO work with onClick events more gracefully.
            mIngredientsButton = (Button) itemView.findViewById(R.id.ingredients_button);

            mIngredientsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( v.getContext(), RecipeDetailActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
