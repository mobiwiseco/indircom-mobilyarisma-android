package co.mobiwise.indircom.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.mobiwise.indircom.R;

/**
 * Created by mac on 13/03/15.
 */
public class VotingFragment extends Fragment {

    public VotingFragment() {
    }

    /**
     * Static factory method that returns the
     * new fragment to the client.
     */
    public static VotingFragment newInstance() {
        VotingFragment mainFragment = new VotingFragment();
        return mainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_voting, container, false);
        return rootView;
    }
}
