package com.node_coyote.bakerscorner;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.node_coyote.bakerscorner.recipeData.BakeContract.BakeEntry;
import com.node_coyote.bakerscorner.recipeData.BakingUrl;
import com.node_coyote.bakerscorner.recipeData.RecipeDatabaseHelper;
import com.node_coyote.bakerscorner.utility.JSONUtility;
import com.node_coyote.bakerscorner.utility.NetworkUtility;
import com.node_coyote.bakerscorner.viewFragments.IngredientsFragment;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * Theme ideas.
     * Loading Indicator: Spoon in a bowl
     * Red and White checker theme.
     * Yellow lined paper. (Stains, used)
     * Paperclip fragments of a recipe to stay in the view until unpinned.
     *     longClick to remove.
     */

    private RecipeAdapter mRecipeAdapter;
    private ContentValues[] mRecipeData;
    private static final int RECIPE_LOADER = 5;
    private static final String[] RECIPE_PROJECTION = {
            BakeEntry._ID,
            BakeEntry.COLUMN_RECIPE_ID,
            BakeEntry.COLUMN_RECIPE_NAME,
            BakeEntry.COLUMN_RECIPE_INGREDIENTS_ID,
            BakeEntry.COLUMN_RECIPE_STEPS_ID,
            BakeEntry.COLUMN_RECIPE_SERVINGS,
            BakeEntry.COLUMN_RECIPE_IMAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO Butterknife
        mRecipeAdapter = new RecipeAdapter(this);
        RecyclerView recipeRecycler = (RecyclerView) findViewById(R.id.recipe_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recipeRecycler.setLayoutManager(linearLayoutManager);
        recipeRecycler.setAdapter(mRecipeAdapter);
        recipeRecycler.setHasFixedSize(true);

        if (checkforDatabase()){
            fetchRecipeData();
        } else {
            getLoaderManager().restartLoader(RECIPE_LOADER, null, this);
        }

        // Create recyclerView onClick ingredients and steps
        // Call background thread.
    }

    /**
     * This method helps us check if our database is empty.
     * @return True if the database is empty and false if it exists.
     */
    boolean checkforDatabase(){
        boolean empty = true;
        RecipeDatabaseHelper helper = new RecipeDatabaseHelper(MainActivity.this);
        SQLiteDatabase database = helper.getReadableDatabase();
        String check = "SELECT COUNT(*) FROM recipes";
        Cursor cursor = database.rawQuery(check, null);
        if (cursor != null && cursor.moveToFirst()) {
            empty = (cursor.getInt (0) == 0);
        }
        cursor.close();
        return empty;
    }

    /**
     * This method runs our network operations on a background thread.
     * It also helps us decide what should be shown in the view depending on internet connection.
     */
    void fetchRecipeData(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new FetchRecipeData().execute();
            getLoaderManager().initLoader(RECIPE_LOADER, null, this);
            //mEmpty.setText(R.string.nothing);
            //showSchoolRoster();

        } else {
//            showLoadingIndicator();
//            mEmpty.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch (loaderId) {
            case RECIPE_LOADER:
                return new CursorLoader(
                        this,
                        BakeEntry.CONTENT_URI,
                        RECIPE_PROJECTION,
                        null,
                        null,
                        null
                );
            default:
                throw new RuntimeException("Loader not implemented" + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mRecipeAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecipeAdapter.swapCursor(null);
    }

    public class FetchRecipeData extends AsyncTask<String, Void, ContentValues[]> {

        @Override
        protected ContentValues[] doInBackground(String... params) {

            try {
                String recipeJSONResponse = NetworkUtility.getHttpResponse(BakingUrl.getRecipeUrl());
                mRecipeData = JSONUtility.getRecipeStringsFromJSON(MainActivity.this, recipeJSONResponse);
                Log.v(LOG_TAG, mRecipeData[2].toString());
                return mRecipeData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ContentValues[] recipeData) {
            mRecipeData = recipeData;
            Log.v(LOG_TAG, mRecipeData[2].toString());

            if (mRecipeData != null) {
                mRecipeAdapter.setRecipeData(mRecipeData);
            }
        }
    }
}
