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

import com.node_coyote.bakerscorner.R;

import com.node_coyote.bakerscorner.steps.StepContract.StepEntry;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    StepsCursorAdapter mAdapter;

    public static final int STEPS_LOADER = 3;
    private static final String ROW_ID_KEY = "ROW_ID";
    private static final String RECIPE_NAME_KEY = "RECIPE_NAME";
    int STEP_ID;

    String[] STEPS_PROJECTION = {
            StepEntry._ID,
            StepEntry.COLUMN_STEP_ID,
            StepEntry.COLUMN_SHORT_DESCRIPTION,
            StepEntry.COLUMN_DESCRIPTION,
            StepEntry.COLUMN_VIDEO_URL,
            StepEntry.COLUMN_THUMBNAIL_URL,
            StepEntry.COLUMN_STEP_RECIPE_ID
    };

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
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getArguments().getInt(StepEntry.COLUMN_STEP_RECIPE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View stepsContainer = inflater.inflate(R.layout.content_recipe_detail, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            long rowId = bundle.getLong(ROW_ID_KEY);
            String recipeName = bundle.getString(RECIPE_NAME_KEY);
            getActivity().setTitle(recipeName);
            STEP_ID = (int) (long) rowId;
        }

        mAdapter = new StepsCursorAdapter(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        RecyclerView stepRecycler = (RecyclerView) stepsContainer.findViewById(R.id.steps_detail_recycler_view);
        stepRecycler.setAdapter(mAdapter);
        stepRecycler.setLayoutManager(manager);
        getLoaderManager().initLoader(STEPS_LOADER, null, this);

        return stepsContainer;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {

        switch (loaderId) {
            case STEPS_LOADER:
                String selection = "step_recipe_id = " + STEP_ID;
                return new android.support.v4.content.CursorLoader(
                        getContext(),
                        StepEntry.CONTENT_URI,
                        STEPS_PROJECTION,
                        selection,
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
