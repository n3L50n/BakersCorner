package com.node_coyote.bakerscorner.viewFragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by node_coyote on 6/7/17.
 */

public class RecipePagerAdapter extends FragmentPagerAdapter {

    Context mContext;

    public RecipePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RecipeFragment();
            case 1:
                return new RecipeFragment();
            default:
                return new RecipeFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
