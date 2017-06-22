package com.node_coyote.bakerscorner.utility;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.node_coyote.bakerscorner.recipeData.BakeContract.BakeEntry;
import com.node_coyote.bakerscorner.ingredientData.IngredientContract.IngredientEntry;
import com.node_coyote.bakerscorner.stepData.StepContract.StepEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by node_coyote on 6/6/17.
 */

public final class BakeJSONUtility {


    // A tag for log messages.
    public static final String LOG_TAG = BakeJSONUtility.class.getSimpleName();

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

                int ingredientItemCounter = 0;
                int ingredientId = recipeId + ingredientItemCounter;
                ContentValues ingredientValues = new ContentValues();
                ContentValues[] ingredientsValuesArray = new ContentValues[recipeIngredients.length()];

                for (int j = 0; j < recipeIngredients.length(); j++){
                    
                    JSONObject ingredients = recipeIngredients.getJSONObject(i);

                    int quantity = ingredients.getInt(QUANTITY);
                    String measure = ingredients.getString(MEASURE);
                    String ingredient = ingredients.getString(INGREDIENT);

                    ingredientItemCounter++;
                    ingredientValues.put(IngredientEntry.COLUMN_INGREDIENT_ID, ingredientId);
                    ingredientValues.put(IngredientEntry.COLUMN_QUANTITY, quantity);  
                    ingredientValues.put(IngredientEntry.COLUMN_MEASURE, measure);
                    ingredientValues.put(IngredientEntry.COLUMN_INGREDIENT, ingredient);

                    ingredientsValuesArray[j] = ingredientValues;
                }

                context.getContentResolver().bulkInsert(IngredientEntry.CONTENT_URI, ingredientsValuesArray);

                JSONArray recipeSteps = recipe.getJSONArray(STEPS);

                int stepsItemCounter = 0;
                int stepsRecipeId = recipeId + stepsItemCounter;

                ContentValues stepValues = new ContentValues();
                ContentValues[] stepValuesArray = new ContentValues[recipeSteps.length()];

                for (int k = 0; k < recipeSteps.length(); k++ ) {

                    JSONObject steps = recipeSteps.getJSONObject(k);

                    int stepId = steps.getInt(STEP_ID);
                    String shortDescription = steps.getString(SHORT_DESCRIPTION);
                    String description = steps.getString(DESCRIPTION);
                    String videoURL = steps.getString(VIDEO_URL);
                    String thumbnailURL = steps.getString(THUMBNAIL_URL);

                    stepValues.put(StepEntry.COLUMN_STEP_ID, stepId);
                    stepValues.put(StepEntry.COLUMN_SHORT_DESCRIPTION, shortDescription);
                    stepValues.put(StepEntry.COLUMN_DESCRIPTION, description);
                    stepValues.put(StepEntry.COLUMN_VIDEO_URL, videoURL); 
                    stepValues.put(StepEntry.COLUMN_THUMBNAIL_URL, thumbnailURL);

                    stepValuesArray[k] = stepValues;

                }

                context.getContentResolver().bulkInsert(StepEntry.CONTENT_URI, stepValuesArray);

                int recipeServings = recipe.getInt(SERVINGS);
                String recipeImage = recipe.getString(IMAGE);

                ContentValues values = new ContentValues();
                values.put(BakeEntry.COLUMN_RECIPE_ID, recipeId);
                values.put(BakeEntry.COLUMN_RECIPE_NAME, recipeName);
                values.put(BakeEntry.COLUMN_RECIPE_INGREDIENTS_ID, ingredientId);
                values.put(BakeEntry.COLUMN_RECIPE_STEPS_ID, stepsRecipeId);
                values.put(BakeEntry.COLUMN_RECIPE_SERVINGS, recipeServings);
                values.put(BakeEntry.COLUMN_RECIPE_IMAGE, recipeImage);

                parsedRecipeValues[i] = values;
            }

        } catch (JSONException e){
            e.printStackTrace();
        }
        Log.v(LOG_TAG, parsedRecipeValues[3].toString());
        context.getContentResolver().bulkInsert(BakeEntry.CONTENT_URI, parsedRecipeValues);
        return parsedRecipeValues;
    }
}
