package com.node_coyote.bakerscorner;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        RecipeAdapter mRecipeAdapter = new RecipeAdapter();
        RecyclerView recipeRecycler = (RecyclerView) findViewById(R.id.recipe_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recipeRecycler.setLayoutManager(linearLayoutManager);
        recipeRecycler.setAdapter(mRecipeAdapter);

        // Create recyclerView onClick ingredients and steps
        // Call background thread.
    }
}
