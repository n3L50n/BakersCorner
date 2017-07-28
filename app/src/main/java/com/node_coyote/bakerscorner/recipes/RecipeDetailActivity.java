package com.node_coyote.bakerscorner.recipes;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.node_coyote.bakerscorner.R;
import com.node_coyote.bakerscorner.ingredients.IngredientsFragment;
import com.node_coyote.bakerscorner.steps.StepsListFragment;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Ingredients");
        if (Build.VERSION.SDK_INT >= 21) {
            toolbar.setTranslationZ(2.00f);
            toolbar.setElevation(2.00f);
        }

        View mUpButton = findViewById(R.id.action_up);
        mUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSupportNavigateUp();
            }
        });

        //TODO on back and on up button pressed. must reload cursor data into recipeActivity

        StepsListFragment stepsListFragment = new StepsListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.steps_fragment, stepsListFragment)
                .commit();

        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        fragmentManager.beginTransaction()
                .add(R.id.ingredients_container, ingredientsFragment)
                .commit();
    }

}
