package com.node_coyote.bakerscorner.ingredients;

import android.support.v4.app.LoaderManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.node_coyote.bakerscorner.R;

public class IngredientsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int INGREDIENT_LOADER = 9;
    private static final String ROW_ID_KEY = "ROW_ID";
    int INGREDIENT_ID;


    String[] INGREDIENT_PROJECTION = {
            IngredientContract.IngredientEntry._ID,
            IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ID,
            IngredientContract.IngredientEntry.COLUMN_QUANTITY,
            IngredientContract.IngredientEntry.COLUMN_MEASURE,
            IngredientContract.IngredientEntry.COLUMN_INGREDIENT

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

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            long rowId = bundle.getLong(ROW_ID_KEY);
            INGREDIENT_ID = (int) (long) rowId;
        }

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
                String selection = "ingredients_id = " + INGREDIENT_ID;
                return new android.support.v4.content.CursorLoader(
                        getContext(),
                        IngredientContract.IngredientEntry.CONTENT_URI,
                        INGREDIENT_PROJECTION,
                        selection,
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
