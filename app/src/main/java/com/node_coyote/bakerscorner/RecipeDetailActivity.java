package com.node_coyote.bakerscorner;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.node_coyote.bakerscorner.viewFragments.IngredientsFragment;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        FragmentManager fragmentManager = getSupportFragmentManager();
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        fragmentManager.beginTransaction()
                .add(R.id.ingredients_container, ingredientsFragment)
                .commit();
    }
}
