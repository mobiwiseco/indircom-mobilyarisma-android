package co.mobiwise.indircom.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

    private int lastPosition;

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

        /**
         * create pager adapter and tie to viewpager.
         */
        mPagerAdapter = new ViewPagerAdapter(MainVotingPageFragment.this, getFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        /**
         * Fetch non-voted apps by user.
         */
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
        /**
         * Hold current index to control head and tail of pager.
         */
        final int tempIndex = mPager.getCurrentItem();

        if(mPagerAdapter.getCount()>1){
            if(tempIndex == (mPagerAdapter.getCount()-1))
                mPager.setCurrentItem(tempIndex - 1);
            else
                mPager.setCurrentItem(tempIndex + 1);
        }


        /**
         * Removing Item from pager adapter blocks changing current item
         * animation. This code block delays removing process enough time
         * to show change animation.Trick.
         */
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPagerAdapter.removeApp(tempIndex);
                if(mPagerAdapter.getCount() > 1)
                    mPager.setCurrentItem(tempIndex,false);
            }
        }, 350);
    }

    @Override
    public void onAppsStartFetching() {
        //TODO start showing dialog.
    }

    @Override
    public void onAppsFetchCompleted(ArrayList<App> apps) {
        mPagerAdapter.setAppList(apps);
        lastPosition = mPager.getCurrentItem();
        //TODO dismiss dialog.
    }
}
