package co.mobiwise.indircom.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import co.mobiwise.indircom.R;
import co.mobiwise.indircom.adapter.ViewPagerAdapter;
import co.mobiwise.indircom.api.Api;
import co.mobiwise.indircom.controller.UserManager;
import co.mobiwise.indircom.listener.AppFetchControllerListener;
import co.mobiwise.indircom.listener.VotingActionFragmentCallback;
import co.mobiwise.indircom.model.App;
import co.mobiwise.indircom.model.User;
import co.mobiwise.indircom.utils.MaterialDesignDialog;
import co.mobiwise.indircom.views.RobotoMediumTextView;

public class MainVotingPageFragment extends Fragment implements VotingActionFragmentCallback, AppFetchControllerListener, View.OnClickListener {

    /**
     * To achieve mPager from MainActivity, define as static
     * The pager widget
     */
    @InjectView(R.id.pager)
    ViewPager mPager;

    /**
     * provides the pages to the view pager widget.
     */
    private ViewPagerAdapter mPagerAdapter;

    /**
     * Empty page items
     */
    @InjectView(R.id.imageview_big_like)
    ImageView imageviewCongrats;

    @InjectView(R.id.textview_congrats)
    RobotoMediumTextView textviewCongratsHeader;

    @InjectView(R.id.textview_congrats_content)
    RobotoMediumTextView textviewCongratsContent;

    /**
     * Menu widget
     */
    @InjectView(R.id.imageview_menu)
    ImageView imageviewMenu;

    /**
     * Animations for empty page transition.
     */
    private Animation fadeIn;
    private Animation fadeOut;

    /**
     * Tag to log.
     */
    private String TAG = "MainVotingPageFragment";

    private MaterialDialog materialDialog;

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

        ButterKnife.inject(this,rootView);

        /**
         * Fade in/out animation initialization
         */
        fadeIn = new AlphaAnimation(0, 0.8f);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(200);
        fadeIn.setStartOffset(100);

        fadeOut = new AlphaAnimation(0.8f, 0);
        fadeOut.setInterpolator(new DecelerateInterpolator());
        fadeOut.setDuration(200);

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
        Log.v(TAG, "onActivityCreated");
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

        if (mPagerAdapter.getCount() > 1) {
            if (tempIndex == (mPagerAdapter.getCount() - 1)) {
                mPager.setCurrentItem(tempIndex - 1);
            } else {
                mPager.setCurrentItem(tempIndex + 1);
            }
        } else {
            showEmptyPageItems();
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
                if (mPagerAdapter.getCount() > 1)
                    mPager.setCurrentItem(tempIndex, false);
            }
        }, 350);
    }

    @Override
    public void onAppsStartFetching() {
        /**
         * start showing dialog when app fetching starts
         */
        materialDialog = MaterialDesignDialog.newInstance(getActivity()).createScanningDialog(getString(R.string.fetching_app_message));
        materialDialog.show();
    }

    @Override
    public void onAppsFetchCompleted(ArrayList<App> apps) {
        /**
         * set app list to adapter
         */
        mPagerAdapter.setAppList(apps);

        /**
         * No items to vote
         */
        if (apps.size() == 0) {
            showEmptyPageItems();
        }

        /**
         *  dismiss dialog when app fetching completed
         */
        if (materialDialog != null) {
            materialDialog.cancel();
        }
    }

    /**
     * Shows views when all apps are voted.
     */
    private void showEmptyPageItems() {

        mPager.setVisibility(View.INVISIBLE);
        mPager.startAnimation(fadeOut);

        imageviewCongrats.setVisibility(View.VISIBLE);
        textviewCongratsContent.setVisibility(View.VISIBLE);
        textviewCongratsHeader.setVisibility(View.VISIBLE);

        imageviewCongrats.startAnimation(fadeIn);
        textviewCongratsContent.startAnimation(fadeIn);
        textviewCongratsHeader.startAnimation(fadeIn);
    }

    @OnClick(R.id.imageview_menu)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_menu:
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity(), imageviewMenu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu_main, popup.getMenu());
                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.about:
                                openAppAboutPage();
                                break;
                        }
                        return true;
                    }
                });
                //showing popup menu
                popup.show();
                break;
            default:
                break;
        }
    }

    private void openAppAboutPage() {
        //TODO needs animation to fragment transaction
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.container, AboutFragment.newInstance()).addToBackStack("AboutFragment")
                .commit();
    }
}
