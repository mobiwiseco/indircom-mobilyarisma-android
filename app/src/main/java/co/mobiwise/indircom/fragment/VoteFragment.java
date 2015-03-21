package co.mobiwise.indircom.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.api.Api;
import co.mobiwise.indircom.listener.VoteControllerListener;
import co.mobiwise.indircom.model.App;

/**
 * Created by mac on 13/03/15.
 */
public class VoteFragment extends Fragment implements VoteControllerListener{

    private static String TAG = "VoteFragment";

    private Button button;

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
        View rootView = inflater.inflate(R.layout.fragment_vote, container, false);
        button = (Button) rootView.findViewById(R.id.button_temporary);

        //Register this fragment to listen VoteController api methods.
        Api.getInstance(getActivity().getApplicationContext()).registerVoteControllerListener(VoteFragment.this);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = UserManager.getInstance(getActivity().getApplicationContext()).getUser();
                Api.getInstance(getActivity().getApplicationContext()).getApps(user.getToken(),user.getAuth_id());
            }
        });*/
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Unregister vote controller
        Api.getInstance(getActivity().getApplicationContext()).unregisterVoteControllerListener();
    }
}
