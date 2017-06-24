package com.node_coyote.bakerscorner.ingredients;

import android.support.v4.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.node_coyote.bakerscorner.R;
import com.node_coyote.bakerscorner.steps.StepContract;

public class IngredientsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int INGREDIENT_LOADER = 9;
    public static final int STEPS_LOADER = 3;

    String[] INGREDIENT_PROJECTION = {
            IngredientContract.IngredientEntry._ID,
            //IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ID,
            IngredientContract.IngredientEntry.COLUMN_QUANTITY,
            IngredientContract.IngredientEntry.COLUMN_MEASURE,
            IngredientContract.IngredientEntry.COLUMN_INGREDIENT

    };

    String[] STEPS_PROJECTION = {
            StepContract.StepEntry._ID,
            StepContract.StepEntry.COLUMN_STEP_ID,
            StepContract.StepEntry.COLUMN_SHORT_DESCRIPTION,
            StepContract.StepEntry.COLUMN_DESCRIPTION,
            StepContract.StepEntry.COLUMN_VIDEO_URL,
            StepContract.StepEntry.COLUMN_THUMBNAIL_URL
    };

    IngredientCursorAdapter mIngredientAdapter;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View ingredientsView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        RecyclerView ingredientsList = (RecyclerView) ingredientsView.findViewById(R.id.ingredients_recycler_view);
        mIngredientAdapter = new IngredientCursorAdapter(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        ingredientsList.setLayoutManager(manager);
        ingredientsList.setAdapter(mIngredientAdapter);
        getLoaderManager().initLoader(INGREDIENT_LOADER, null, this);
        return ingredientsView;
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch (loaderId) {
            case INGREDIENT_LOADER:
                return new android.support.v4.content.CursorLoader(
                        getContext(),
                        IngredientContract.IngredientEntry.CONTENT_URI,
                        INGREDIENT_PROJECTION,
                        null,
                        null,
                        null
                );
            case STEPS_LOADER:
                return new android.support.v4.content.CursorLoader(
                        getContext(),
                        StepContract.StepEntry.CONTENT_URI,
                        STEPS_PROJECTION,
                        null,
                        null,
                        null
                );
            default:
                throw new RuntimeException("Loader not implemented" + loaderId);
        }
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        mIngredientAdapter.swapIngredientCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mIngredientAdapter.swapIngredientCursor(null);
    }
}
