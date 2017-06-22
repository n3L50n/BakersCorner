package com.node_coyote.bakerscorner.recipes;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by node_coyote on 6/6/17.
 */

public final class BakingUrl {
    // The url holding Miriam's recipe data.
    private static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static URL getRecipeUrl() {
        URL url = null;
        try {
            url = new URL(RECIPE_URL);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

}
