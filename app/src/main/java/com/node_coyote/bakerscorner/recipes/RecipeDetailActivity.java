package com.node_coyote.bakerscorner.recipes;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.node_coyote.bakerscorner.R;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // TODO Butterknife
        ViewPager recipePager = (ViewPager) findViewById(R.id.recipe_detail_view_pager);
        RecipeDetailFragmentPager adapter = new RecipeDetailFragmentPager(this, getSupportFragmentManager());
        recipePager.setAdapter(adapter);
    }
}
