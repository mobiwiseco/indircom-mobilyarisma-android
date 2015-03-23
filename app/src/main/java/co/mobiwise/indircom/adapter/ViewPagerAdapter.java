package co.mobiwise.indircom.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import co.mobiwise.indircom.fragment.MainVotingPageFragment;
import co.mobiwise.indircom.fragment.VotingActionFragment;
import co.mobiwise.indircom.model.App;

/**
 * Created by mac on 21/03/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public static final String TAG = "ViewPagerAdapter";

    Fragment fragment;

    ArrayList<App> appList;

    public ViewPagerAdapter(Fragment fragment, FragmentManager fm) {
        super(fm);
        this.fragment = fragment;
        appList = new ArrayList<>();
    }

    public void setAppList(ArrayList<App> app_list){
        this.appList = app_list;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        VotingActionFragment votingActionFragment =  VotingActionFragment.newInstance(appList.get(position));
        votingActionFragment.setVotingActionCallback((MainVotingPageFragment) fragment);
        return votingActionFragment;
    }

    @Override
    public int getCount() {
        return appList.size();
    }

}
