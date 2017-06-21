package com.node_coyote.bakerscorner;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.node_coyote.bakerscorner.recipeData.BakingUrl;
import com.node_coyote.bakerscorner.utility.BakeJSONUtility;
import com.node_coyote.bakerscorner.utility.BakeNetworkUtility;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by node_coyote on 6/20/17.
 */

public class FetchRecipeData extends AsyncTask<String, Void, ContentValues[]> {

    private Context mContext;

    public FetchRecipeData(Context context) {
        this.mContext = context;
    }

    @Override
    protected ContentValues[] doInBackground(String... params) {

        try {
            String recipeJSONResponse = BakeNetworkUtility.getHttpResponse(BakingUrl.getRecipeUrl());
            try {
                Log.v("RECIPES", recipeJSONResponse);
                return BakeJSONUtility.getRecipeStringsFromJSON(mContext, recipeJSONResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return new ContentValues[0];
    }
}
