package com.node_coyote.bakerscorner.recipes;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;


import com.node_coyote.bakerscorner.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailActivityOld extends AppCompatActivity {

    @BindView(R.id.recipe_detail_view_pager) ViewPager recipePager;
    @BindView(R.id.sliding_tabs) TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail_old);

        ButterKnife.bind(this);
        RecipeDetailFragmentPager adapter = new RecipeDetailFragmentPager(this, getSupportFragmentManager());
        recipePager.setAdapter(adapter);
        tabLayout.setupWithViewPager(recipePager);
    }
}