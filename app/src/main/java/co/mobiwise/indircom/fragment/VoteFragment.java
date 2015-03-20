package co.mobiwise.indircom.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.listener.VoteControllerListener;
import co.mobiwise.indircom.model.App;

/**
 * Created by mac on 13/03/15.
 */
public class VoteFragment extends Fragment implements VoteControllerListener{

    public VoteFragment() {
    }

    /**
     * Static factory method that returns the
     * new fragment to the client.
     */
    public static VoteFragment newInstance() {
        VoteFragment mainFragment = new VoteFragment();
        return mainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_voting, container, false);
        return rootView;
    }

    @Override
    public void onVoteCompleted() {

    }

    @Override
    public void onErrorOccured(String error_code) {

    }

    @Override
    public void onVoteStartSending() {

    }

    @Override
    public void onAppsStartFetching() {

    }

    @Override
    public void onAppsFetchCompleted(ArrayList<App> apps) {

    }
}
