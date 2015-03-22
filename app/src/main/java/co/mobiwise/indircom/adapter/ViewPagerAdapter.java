package co.mobiwise.indircom.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import co.mobiwise.indircom.fragment.MainVotingPageFragment;
import co.mobiwise.indircom.fragment.VotingActionFragment;

/**
 * Created by mac on 21/03/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public static final String TAG = "ViewPagerAdapter";

    private int num_pages;
    private Fragment fragment;

    public ViewPagerAdapter(Fragment fragment, FragmentManager fm, int num_pages) {
        super(fm);
        this.num_pages = num_pages;
        this.fragment = fragment;
    }

    @Override
    public Fragment getItem(int position) {
        VotingActionFragment voting_action_fragment =  VotingActionFragment.newInstance(position);
        voting_action_fragment.setVotingActionCallback((MainVotingPageFragment)fragment);
        return voting_action_fragment;
    }

    @Override
    public int getCount() {
        return num_pages;
    }

}
