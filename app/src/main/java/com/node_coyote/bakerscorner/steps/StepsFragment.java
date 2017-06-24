package com.node_coyote.bakerscorner.steps;

import android.support.v4.app.LoaderManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.node_coyote.bakerscorner.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    SimpleExoPlayerView mExoPlayer;
    StepsCursorAdapter mAdapter;

    public static final int STEPS_LOADER = 3;

    String[] STEPS_PROJECTION = {
            StepContract.StepEntry._ID,
            StepContract.StepEntry.COLUMN_STEP_ID,
            StepContract.StepEntry.COLUMN_SHORT_DESCRIPTION,
            StepContract.StepEntry.COLUMN_DESCRIPTION,
            StepContract.StepEntry.COLUMN_VIDEO_URL,
            StepContract.StepEntry.COLUMN_THUMBNAIL_URL
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StepsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StepsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StepsFragment newInstance(String param1, String param2) {
        StepsFragment fragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View stepsContainer = inflater.inflate(R.layout.fragment_steps, container, false);
        mExoPlayer = (SimpleExoPlayerView) container.findViewById(R.id.recipe_exo_player);
        mAdapter = new StepsCursorAdapter(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        RecyclerView stepRecycler = (RecyclerView) stepsContainer.findViewById(R.id.steps_recycler_view);
        stepRecycler.setAdapter(mAdapter);
        stepRecycler.setLayoutManager(manager);


        getLoaderManager().initLoader(STEPS_LOADER, null, this);

        // Inflate the layout for this fragment
        // TODO initialize player here
        return stepsContainer;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {

        switch (loaderId) {
            case STEPS_LOADER:
                return new android.support.v4.content.CursorLoader(
                        getContext(),
                        StepContract.StepEntry.CONTENT_URI,
                        STEPS_PROJECTION,
                        null,
                        null,
                        null
                );
            default:
                throw new RuntimeException("Loader not implemented" + loaderId);
        }
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        mAdapter.swapStepCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mAdapter.swapStepCursor(null);
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
