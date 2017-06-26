package com.node_coyote.bakerscorner.recipes;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.node_coyote.bakerscorner.ingredients.IngredientContract;
import com.node_coyote.bakerscorner.ingredients.IngredientsFragment;
import com.node_coyote.bakerscorner.steps.StepsFragment;

/**
 * Created by node_coyote on 6/22/17.
 */

public class RecipeDetailFragmentPager extends FragmentPagerAdapter {

    Context mContext;
    Cursor mCursor;
    private static final String RECIPE_ID_KEY = "RECIPE ID";

    RecipeDetailFragmentPager(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IngredientsFragment();
            case 1:
                return new StepsFragment();
            default:
                return new IngredientsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
