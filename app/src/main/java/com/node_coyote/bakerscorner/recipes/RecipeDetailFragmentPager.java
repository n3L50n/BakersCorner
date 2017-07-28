package com.node_coyote.bakerscorner.recipes;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.node_coyote.bakerscorner.R;
import com.node_coyote.bakerscorner.ingredients.IngredientsFragment;
import com.node_coyote.bakerscorner.steps.StepsListFragment;

/**
 * Created by node_coyote on 6/22/17.
 */

public class RecipeDetailFragmentPager extends FragmentPagerAdapter {

    private Context mContext;

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
                return new StepsListFragment();
            default:
                return new IngredientsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.ingredients_title);
            case 1:
                return mContext.getString(R.string.steps_title);
        }
        return null;
    }
}
