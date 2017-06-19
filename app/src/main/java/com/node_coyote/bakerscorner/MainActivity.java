package com.node_coyote.bakerscorner;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.node_coyote.bakerscorner.viewFragments.RecipePagerAdapter;

public class MainActivity extends AppCompatActivity {

    /**
     * Theme ideas.
     * Loading Indicator: Spoon in a bowl
     * Red and White checker theme.
     * Yellow lined paper. (Stains, used)
     * Paperclip fragments of a recipe to stay in the view until unpinned.
     *     longClick to remove.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO Butterknife
        ViewPager recipePager = (ViewPager) findViewById(R.id.recipe_view_pager);
        RecipePagerAdapter recipeAdapter = new RecipePagerAdapter(this, getSupportFragmentManager());
        recipePager.setAdapter(recipeAdapter);

        // Create recyclerView onClick ingredients and steps
        // Call background thread.
    }
}
