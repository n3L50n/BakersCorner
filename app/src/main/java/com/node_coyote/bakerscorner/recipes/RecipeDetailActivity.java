package com.node_coyote.bakerscorner.recipes;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.node_coyote.bakerscorner.R;
import com.node_coyote.bakerscorner.ingredients.IngredientContract.IngredientEntry;
import com.node_coyote.bakerscorner.steps.StepContract.StepEntry;

public class RecipeDetailActivity extends AppCompatActivity
        //implements LoaderManager.LoaderCallbacks
{
//
//    public static final int INGREDIENT_LOADER = 9;
//    public static final int STEPS_LOADER = 3;
//
//    String[] INGREDIENT_PROJECTION = {
//            IngredientEntry._ID,
//            IngredientEntry.COLUMN_INGREDIENT_ID,
//            IngredientEntry.COLUMN_QUANTITY,
//            IngredientEntry.COLUMN_MEASURE,
//            IngredientEntry.COLUMN_INGREDIENT
//
//    };
//
//    String[] STEPS_PROJECTION = {
//            StepEntry._ID,
//            StepEntry.COLUMN_STEP_ID,
//            StepEntry.COLUMN_SHORT_DESCRIPTION,
//            StepEntry.COLUMN_DESCRIPTION,
//            StepEntry.COLUMN_VIDEO_URL,
//            StepEntry.COLUMN_THUMBNAIL_URL
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // TODO Butterknife
        ViewPager recipePager = (ViewPager) findViewById(R.id.recipe_detail_view_pager);
        RecipeDetailFragmentPager adapter = new RecipeDetailFragmentPager(this, getSupportFragmentManager());
        recipePager.setAdapter(adapter);
    }

//    @Override
//    public Loader onCreateLoader(int loaderId, Bundle args) {
//        switch (loaderId) {
//            case INGREDIENT_LOADER:
//                return new CursorLoader(
//                        this,
//                        IngredientEntry.CONTENT_URI,
//                        INGREDIENT_PROJECTION,
//                        null,
//                        null,
//                        null
//                );
//            case STEPS_LOADER:
//                return new CursorLoader(
//                        this,
//                        StepEntry.CONTENT_URI,
//                        STEPS_PROJECTION,
//                        null,
//                        null,
//                        null
//                );
//            default:
//                throw new RuntimeException("Loader not implemented" + loaderId);
//        }
//    }
//
//    @Override
//    public void onLoadFinished(Loader loader, Object data) {
//
//
//    }
//
//    @Override
//    public void onLoaderReset(Loader loader) {
//
//    }
}
