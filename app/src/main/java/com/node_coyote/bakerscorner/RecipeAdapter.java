package com.node_coyote.bakerscorner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by node_coyote on 6/20/17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int gridItemId = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;

        View view = inflater.inflate(gridItemId, parent, attachToParentImmediately);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RecipeAdapterViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(, "Arrgghh", Toast.LENGTH_SHORT);
        }
    }
}
