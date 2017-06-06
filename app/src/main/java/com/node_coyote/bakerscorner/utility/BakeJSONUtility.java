package com.node_coyote.bakerscorner.utility;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by node_coyote on 6/6/17.
 */

public final class BakeJSONUtility {

    public static ContentValues[] getRecipeStringsFromJSON(Context context, String recipeJSONString) throws JSONException {

        final String RECIPE_ID = "id";
        final String NAME = "name";

        // An array of JSON Objects pertaining to ingredients.
        final String INGREDIENTS = "ingredients";

        // The elements within each JSON object within the ingredients array.
        final String QUANTITY = "quantity";
        final String MEASURE = "measure";
        final String INGREDIENT = "ingredient";

        // An array of JSON objects pertaining to steps.
        final String STEPS = "steps";

        // The elements within each JSON object within the ingredients array.
        final String STEP_ID = "id";
        final String SHORT_DESCRIPTION = "shortDescription";
        final String DESCRIPTION = "description";
        final String VIDEO_URL = "videoURL";
        final String THUMBNAIL_URL = "thumbnailURL";

        final String SERVINGS = "servings";
        final String IMAGE = "image";

        JSONArray root = new JSONArray(recipeJSONString);
        ContentValues[] parsedRecipeValues = new ContentValues[root.length()];

        try {
            for (int i = 0; i < root.length(); i++){
                JSONObject recipe = root.getJSONObject(i);

                int recipeId = recipe.getInt(RECIPE_ID);
                String recipeName = recipe.getString(NAME);
                JSONArray recipeIngredients = recipe.getJSONArray(INGREDIENTS);

                for (int j = 0; j < recipeIngredients.length(); j++){
                    JSONObject steps = recipeIngredients.getJSONObject(i);
                    int quantity = steps.getInt(QUANTITY);
                    String measure = steps.getString(MEASURE);
                    String ingredient = steps.getString(INGREDIENT);
                }

                JSONArray recipeSteps = recipe.getJSONArray(STEPS);
                // TODO for xxxxx

                int recipeServings = recipe.getInt(SERVINGS);
                String recipeImage = recipe.getString(IMAGE);

            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return parsedRecipeValues;
    }
}
