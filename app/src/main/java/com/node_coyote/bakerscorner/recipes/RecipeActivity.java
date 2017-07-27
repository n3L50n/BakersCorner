package com.node_coyote.bakerscorner.recipes;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.node_coyote.bakerscorner.widget.IngredientWidgetService;
import com.node_coyote.bakerscorner.R;
import com.node_coyote.bakerscorner.recipes.RecipeContract.RecipeEntry;
import com.node_coyote.bakerscorner.utility.JSONUtility;
import com.node_coyote.bakerscorner.utility.NetworkUtility;
import com.node_coyote.bakerscorner.widget.CurrentRecipeContract;
import com.node_coyote.bakerscorner.widget.CurrentRecipeDatabaseHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>,
        RecipeCursorAdapter.RecipeOnClickHandler{

    @BindView(R.id.recipe_recycler_view) RecyclerView recipeRecycler;
    @BindView(R.id.empty_recipe_view) ImageButton emptyRecipe;
    @BindView(R.id.empty_app_name_view) TextView emptyAppName;
    @BindView(R.id.no_connection_view) TextView noConnection;

    public static final String LOG_TAG = RecipeActivity.class.getSimpleName();

    private RecipeCursorAdapter mRecipeCursorAdapter;
    private ContentValues[] mRecipeData;
    private static final int RECIPE_LOADER = 5;

    private static final String[] RECIPE_PROJECTION = {
            RecipeEntry._ID,
            RecipeEntry.COLUMN_RECIPE_ID,
            RecipeEntry.COLUMN_RECIPE_NAME,
            RecipeEntry.COLUMN_RECIPE_SERVINGS,
            RecipeEntry.COLUMN_RECIPE_IMAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRecipeCursorAdapter = new RecipeCursorAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recipeRecycler.setLayoutManager(linearLayoutManager);
        recipeRecycler.setAdapter(mRecipeCursorAdapter);
        recipeRecycler.setHasFixedSize(true);
        emptyRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchRecipeData();
            }
        });

        if (checkForDatabase()){
            fetchRecipeData();
        } else {
            getLoaderManager().restartLoader(RECIPE_LOADER, null, this);
        }
    }

    /**
     * This method helps us check if our database is empty.
     * @return True if the database is empty and false if it exists.
     */
    boolean checkForDatabase(){
        boolean empty = true;
        RecipeDatabaseHelper helper = new RecipeDatabaseHelper(RecipeActivity.this);
        SQLiteDatabase database = helper.getReadableDatabase();
        String check = "SELECT COUNT(*) FROM recipes";
        Cursor cursor = database.rawQuery(check, null);
        if (cursor != null && cursor.moveToFirst()) {
            empty = (cursor.getInt (0) == 0);
            cursor.close();
        }

        return empty;
    }

    /**
     * Helper method to bundle up view toggling.
     * This one dismisses the items that show when there is no internet and replaces them with our recipes.
     */
    private void showRecipes(){
        emptyRecipe.setVisibility(View.INVISIBLE);
        emptyAppName.setVisibility(View.INVISIBLE);
        noConnection.setVisibility(View.INVISIBLE);
        recipeRecycler.setVisibility(View.VISIBLE);

    }

    /**
     * Helper method to bundle up view toggling.
     * This one informs our users that there is no internet and hides the empty recipes.
     */
    private void showEmptyView(){
        emptyRecipe.setVisibility(View.VISIBLE);
        emptyAppName.setVisibility(View.VISIBLE);
        noConnection.setVisibility(View.VISIBLE);
        recipeRecycler.setVisibility(View.GONE);
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
            showRecipes();
        } else {
            showEmptyView();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch (loaderId) {
            case RECIPE_LOADER:
                return new CursorLoader(
                        this,
                        RecipeEntry.CONTENT_URI,
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
        mRecipeCursorAdapter.swapCursor(data);
        if (data.getCount() != 0) showRecipes();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecipeCursorAdapter.swapCursor(null);
    }

    @Override
    public void onClick(Uri recipeUri) {

        String og = recipeUri.toString();
        String ng = og.substring(55);
        Integer lg = Integer.parseInt(ng);
        Intent intent = new Intent(this, RecipeDetailActivityOld.class);
        intent.setData(recipeUri);
        startActivity(intent);

        ContentValues values = new ContentValues();
        values.put(CurrentRecipeContract.CurrentRecipeEntry.COLUMN_CURRENT_RECIPE_ID, lg);
        Uri uri = ContentUris.withAppendedId(CurrentRecipeContract.CurrentRecipeEntry.CONTENT_URI, 1);
        if (checkCurrent()) {
            getContentResolver().insert(uri, values);
        } else {
            getContentResolver().update(uri, values, null, null);
        }

        getContentResolver().notifyChange(CurrentRecipeContract.CurrentRecipeEntry.CONTENT_URI, null);
    }

    boolean checkCurrent() {
        boolean empty = true;
        CurrentRecipeDatabaseHelper helper = new CurrentRecipeDatabaseHelper(RecipeActivity.this);
        SQLiteDatabase database = helper.getReadableDatabase();
        String check = "SELECT COUNT(*) FROM current";
        Cursor cursor = database.rawQuery(check, null);
        if (cursor != null && cursor.moveToFirst()) {
            empty = (cursor.getInt (0) == 0);
            cursor.close();
        }
        return empty;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IngredientWidgetService.startActionUpdateIngredientWidgets(getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        IngredientWidgetService.startActionUpdateIngredientWidgets(getApplicationContext());
    }

    @Override
    protected void onStop() {
        super.onStop();
        IngredientWidgetService.startActionUpdateIngredientWidgets(getApplicationContext());
    }

    public class FetchRecipeData extends AsyncTask<String, Void, ContentValues[]> {

        @Override
        protected ContentValues[] doInBackground(String... params) {

            try {
                String recipeJSONResponse = NetworkUtility.getHttpResponse(BakingUrl.getRecipeUrl());
                mRecipeData = JSONUtility.getRecipeStringsFromJSON(RecipeActivity.this, recipeJSONResponse);
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
            if (mRecipeData != null) {
                mRecipeCursorAdapter.setRecipeData(mRecipeData);
            }
        }
    }
}
