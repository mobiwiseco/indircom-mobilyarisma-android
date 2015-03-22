package co.mobiwise.indircom.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.adapter.ViewPagerAdapter;
import co.mobiwise.indircom.listener.PagerCurrentItemListener;

/**
 * Created by mac on 13/03/15.
 */
public class MainVotingPageFragment extends Fragment {

    private PagerCurrentItemListener pagerCurrentItemListener;
    /**
     * To achieve mPager from MainActivity, define as static
     * The pager widget
     */
    private static ViewPager mPager;

    /**
     * provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

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
        mPagerAdapter = new ViewPagerAdapter(getFragmentManager(), 5);
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Log.v("MainVotingPageFragment", "setOnPageChangeListener");
            }
        });
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            pagerCurrentItemListener = (PagerCurrentItemListener) getActivity();
        } catch (ClassCastException e) {
            throw new RuntimeException("The activity that contains MainVotingPageFragment must implement MainVotingPageFragment.setCurrentPage");
        }
    }


    /**
     * @param position
     */
    public static void setCurrentItem(int position) {
        mPager.setCurrentItem(position);
    }
}