package com.node_coyote.bakerscorner.ingredients;

import android.support.v4.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.node_coyote.bakerscorner.R;
import com.node_coyote.bakerscorner.recipes.RecipeDetailActivity;
import com.node_coyote.bakerscorner.steps.StepContract;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link IngredientsFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link IngredientsFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class IngredientsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int INGREDIENT_LOADER = 9;
    public static final int STEPS_LOADER = 3;

    String[] INGREDIENT_PROJECTION = {
            IngredientContract.IngredientEntry._ID,
            IngredientContract.IngredientEntry.COLUMN_INGREDIENT_ID,
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

    IngredientAdapter mIngredientAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public IngredientsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IngredientsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IngredientsFragment newInstance(String param1, String param2) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View ingredientsView = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ListView ingredientsList = (ListView) ingredientsView.findViewById(R.id.ingredients_list_view);
        mIngredientAdapter = new IngredientAdapter(container.getContext());
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

    }
}
