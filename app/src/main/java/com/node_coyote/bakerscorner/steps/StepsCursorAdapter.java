package com.node_coyote.bakerscorner.steps;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.node_coyote.bakerscorner.R;
import com.node_coyote.bakerscorner.steps.StepContract.StepEntry;

/**
 * Created by node_coyote on 6/23/17.
 */

public class StepsCursorAdapter extends RecyclerView.Adapter<StepsCursorAdapter.StepsCursorViewHolder> {

    Cursor mCursor;
    Context mContext;

    StepsCursorAdapter(Context context) {
        mContext = context;
    }

    @Override
    public StepsCursorAdapter.StepsCursorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View stepsHolder = LayoutInflater.from(mContext).inflate(R.layout.step_item, parent, false);
        return new StepsCursorViewHolder(stepsHolder);
    }

    @Override
    public void onBindViewHolder(StepsCursorAdapter.StepsCursorViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        int descriptionColumnIndex = mCursor.getColumnIndex(StepEntry.COLUMN_DESCRIPTION);
        String description = mCursor.getString(descriptionColumnIndex);

        holder.mDescriptionView.setText(description);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    void swapStepCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    class StepsCursorViewHolder extends RecyclerView.ViewHolder {

        final TextView mDescriptionView;

        StepsCursorViewHolder(View itemView) {
            super(itemView);
            mDescriptionView = (TextView) itemView.findViewById(R.id.step_description);
        }
    }
}
