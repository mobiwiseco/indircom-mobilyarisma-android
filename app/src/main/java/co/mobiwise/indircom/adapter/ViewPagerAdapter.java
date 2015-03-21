package co.mobiwise.indircom.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import co.mobiwise.indircom.fragment.VotingActionFragment;

/**
 * Created by mac on 21/03/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public static final String TAG = "ViewPagerAdapter";

    private int num_pages;

    public ViewPagerAdapter(FragmentManager fm, int num_pages) {
        super(fm);
        this.num_pages = num_pages;
    }

    @Override
    public Fragment getItem(int position) {
        Log.v(TAG, String.valueOf(position));
        return VotingActionFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return num_pages;
    }

}
