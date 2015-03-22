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
public class VoteFragment extends Fragment implements VoteControllerListener {

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
        /**
         * Get All unvoted apps on clicked.
         */
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = UserManager.getInstance(getActivity().getApplicationContext()).getUser();
                Api.getInstance(getActivity().getApplicationContext()).getApps(user.getToken(), user.getAuth_id());
            }
        });*/

        /**
         * Vote one app on click.
         */
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = UserManager.getInstance(getActivity().getApplicationContext()).getUser();
                Api.getInstance(getActivity().getApplicationContext()).voteApp(user.getAuth_id(),1,1);
            }
        });*/
    }

    /**
     * Notified when app vote completely sent.
     */
    @Override
    public void onVoteCompleted(int app_id) {
        //TODO update list adapter by removing app by given app_id.
    }

    /**
     * Notified when error occured while sending vote
     * @param error_code
     */
    @Override
    public void onErrorOccured(String error_code) {
        //TODO
    }

    /**
     * Notified when vote start sending.
     */
    @Override
    public void onVoteStartSending() {
        //TODO
    }

    /**
     * Notified when unvoted apps start fetching
     */
    @Override
    public void onAppsStartFetching() {
        //TODO
    }

    /**
     * Notified when all unvoted app list fetched.
     * @param apps
     */
    @Override
    public void onAppsFetchCompleted(ArrayList<App> apps) {
        //TODO notify list adapter with list.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Unregister vote controller
        Api.getInstance(getActivity().getApplicationContext()).unregisterVoteControllerListener();
    }
}
