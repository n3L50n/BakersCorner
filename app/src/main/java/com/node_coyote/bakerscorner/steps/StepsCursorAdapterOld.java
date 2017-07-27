package com.node_coyote.bakerscorner.steps;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.node_coyote.bakerscorner.R;
import com.node_coyote.bakerscorner.steps.StepContract.StepEntry;
import com.squareup.picasso.Picasso;

/**
 * Created by node_coyote on 6/23/17.
 */

public class StepsCursorAdapterOld extends RecyclerView.Adapter<StepsCursorAdapterOld.StepsCursorViewHolder>  {

    Cursor mCursor;
    Context mContext;

    public StepsCursorAdapterOld(Context context) {
        mContext = context;
    }

    @Override
    public StepsCursorAdapterOld.StepsCursorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View stepsHolder = LayoutInflater.from(mContext).inflate(R.layout.step_item, parent, false);
        return new StepsCursorViewHolder(stepsHolder);
    }

    @Override
    public void onBindViewHolder(StepsCursorAdapterOld.StepsCursorViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        // TODO case(match){
        // TODO contains "Text" && "Url" image visible play, video visible,
        // TODO contains "text" !& "url, image invisible, play video invisible

        int descriptionColumnIndex = mCursor.getColumnIndex(StepEntry.COLUMN_DESCRIPTION);
        String description = mCursor.getString(descriptionColumnIndex);

        int thumbnailColumnIndex = mCursor.getColumnIndex(StepEntry.COLUMN_THUMBNAIL_URL);
        String thumbnail = mCursor.getString(thumbnailColumnIndex);

        int videoColumnIndex = mCursor.getColumnIndex(StepEntry.COLUMN_VIDEO_URL);
        String video = mCursor.getString(videoColumnIndex);

        if (!thumbnail.isEmpty() && thumbnail.length() != 0) {
            Picasso.with(holder.mThumbnailView.getContext()).load(thumbnail).into(holder.mThumbnailView);
            holder.mThumbnailView.setVisibility(View.VISIBLE);
        }

        holder.mDescriptionView.setText(description);

        if (!video.isEmpty() || video.length() != 0) {
            holder.initializePlayer(Uri.parse(video));
            holder.mExoPlayerView.setVisibility(View.VISIBLE);
        } else {
            holder.mExoPlayerView.setVisibility(View.GONE);
        }
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

    class StepsCursorViewHolder extends RecyclerView.ViewHolder implements ExoPlayer.EventListener {

        final TextView mDescriptionView;
        final ImageView mThumbnailView;
        final SimpleExoPlayerView mExoPlayerView;
        SimpleExoPlayer mExoPlayer;

        StepsCursorViewHolder(View itemView) {
            super(itemView);
            mExoPlayerView = (SimpleExoPlayerView) itemView.findViewById(R.id.recipe_exo_player);
            mThumbnailView = (ImageView) itemView.findViewById(R.id.step_thumbnail);
            mDescriptionView = (TextView) itemView.findViewById(R.id.step_description);
            mExoPlayer = null;
        }

        /**
         * Initialize ExoPlayer.
         * @param mediaUri The URI of the sample to play.
         */
        private void initializePlayer(Uri mediaUri) {
            if (mExoPlayer == null) {
                // Create an instance of the ExoPlayer.
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector, loadControl);
                Log.v("PLAYER", String.valueOf(mExoPlayerView));
                mExoPlayerView.setPlayer(mExoPlayer);

                // Set the ExoPlayer.EventListener to this activity.
                mExoPlayer.addListener(this);

                // Prepare the MediaSource.
                // TODO WTF
                String userAgent = Util.getUserAgent(mContext, "WTF");
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                        mContext, userAgent), new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(false);
            }
        }

        public void releasePlayer() {
            if (mExoPlayer != null) {
                mExoPlayer.release();
                mExoPlayer = null;
            }
        }

        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {

        }

        @Override
        public void onPositionDiscontinuity() {

        }
    }
}
