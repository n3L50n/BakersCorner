package com.node_coyote.bakerscorner.ingredients;

/**
 * Created by node_coyote on 6/22/17.
 */

public class Ingredients {
    String mIngredient;
    String mQuantity;
    String mMeasure;

    Ingredients(String ingredient, String quantity, String measure) {
        mIngredient = ingredient;
        mQuantity = quantity;
        mMeasure = measure;
    }

    String getIngredient(){
        return mIngredient;
    }

    String getQuantity(){
        return mQuantity;
    }

    String getMeasure(){
        return mMeasure;
    }
}
