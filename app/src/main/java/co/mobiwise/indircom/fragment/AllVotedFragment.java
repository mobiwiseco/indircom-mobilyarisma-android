package co.mobiwise.indircom.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.mobiwise.indircom.R;

/**
 * Created by mac on 14/03/15.
 */
public class AllVotedFragment extends Fragment {
    public AllVotedFragment() {
    }

    /**
     * Static factory method that returns the
     * new fragment to the client.
     */
    public static AllVotedFragment newInstance() {
        AllVotedFragment allVotedFragment = new AllVotedFragment();
        return allVotedFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_voted, container, false);
        return rootView;
    }
}
