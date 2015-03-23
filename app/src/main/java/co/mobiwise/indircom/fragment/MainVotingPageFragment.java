package co.mobiwise.indircom.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.adapter.ViewPagerAdapter;
import co.mobiwise.indircom.api.Api;
import co.mobiwise.indircom.controller.UserManager;
import co.mobiwise.indircom.listener.AppFetchControllerListener;
import co.mobiwise.indircom.listener.VotingActionFragmentCallback;
import co.mobiwise.indircom.model.App;
import co.mobiwise.indircom.model.User;

/**
 * Created by mac on 13/03/15.
 */
public class MainVotingPageFragment extends Fragment implements VotingActionFragmentCallback, AppFetchControllerListener{

    /**
     * To achieve mPager from MainActivity, define as static
     * The pager widget
     */
    private static ViewPager mPager;

    /**
     * provides the pages to the view pager widget.
     */
    private ViewPagerAdapter mPagerAdapter;

    /**
     * Tag to log.
     */
    private String TAG = "MainVotingPageFragment";

    public MainVotingPageFragment() {
    }

    /**
     * Static factory method that returns the
     * new fragment to the client.
     */
    public static MainVotingPageFragment newInstance() {
        MainVotingPageFragment mainFragment = new MainVotingPageFragment();
        return mainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_voting_page, container, false);


        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) rootView.findViewById(R.id.pager);

        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPagerAdapter = new ViewPagerAdapter(MainVotingPageFragment.this, getFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        User user = UserManager.getInstance(getActivity().getApplicationContext()).getUser();

        Api api = Api.getInstance(getActivity().getApplicationContext());
        api.registerAppsFetchListener(MainVotingPageFragment.this);
        api.getApps(user.getToken(), user.getAuth_id());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Api.getInstance(getActivity().getApplicationContext()).unregisterAppsFetchListener();
    }

    @Override
    public void setCurrentPage(int position) {

    }

    @Override
    public void onVotingAnimationEnd() {
        Log.v(TAG,"onVotingAnimationEnd");
        mPager.setCurrentItem(mPager.getCurrentItem()+1);
    }

    @Override
    public void onAppsStartFetching() {
        //TODO start showing dialog.
    }

    @Override
    public void onAppsFetchCompleted(ArrayList<App> apps) {
        Log.v(TAG,"onAppsFetchCompleted : Size : " + apps.size());
        mPagerAdapter.setAppList(apps);
        //TODO dismiss dialog.
    }
}
