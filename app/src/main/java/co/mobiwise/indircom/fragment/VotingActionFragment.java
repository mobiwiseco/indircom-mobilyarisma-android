package co.mobiwise.indircom.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.nineoldandroids.animation.Animator;
import com.squareup.picasso.Picasso;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.api.ApiConstants;
import co.mobiwise.indircom.controller.VoteRequestQueue;
import co.mobiwise.indircom.listener.VotingActionFragmentCallback;
import co.mobiwise.indircom.model.App;
import co.mobiwise.indircom.views.RobotoMediumTextView;


public class VotingActionFragment extends Fragment implements View.OnClickListener {

    /**
     * The argument keys
     */
    public static final String ARG_APP_ID = "app_id";
    public static final String ARG_APP_NAME = "app_name";
    public static final String ARG_APP_DESCRIPTION = "app_description";
    public static final String ARG_APP_DOWNLOAD_URL = "app_download_url";
    public static final String ARG_APP_IMAGE_URL = "app_image_url";
    /**
     * TAG to log.
     */
    public static final String TAG = "VotingActionFragment";
    /**
     * To notify necessary method when vote action fragment process happen.
     */
    private VotingActionFragmentCallback votingActionFragmentCallback;
    /**
     * VotingActionFragment views
     */
    private RoundedImageView imageviewApp;
    private ImageView imageviewLike;
    private ImageView imageviewDislike;
    private ImageView imageviewInfo;
    private RobotoMediumTextView textviewAppName;
    private ImageView imageViewMenu;

    /**
     * The fragment's values to show users
     */
    private int mAppId;
    private String mAppName, mAppDescription, mAppDownloadUrl, mAppImageUrl;

    public VotingActionFragment() {
    }

    /**
     * Factory method for this fragment class
     */
    public static VotingActionFragment newInstance(App app) {
        VotingActionFragment votingActionFragment = new VotingActionFragment();
        /**
         * Puts app values to bundle
         */
        Bundle args = new Bundle();
        args.putInt(ARG_APP_ID, app.getAppId());
        args.putString(ARG_APP_NAME, app.getAppName());
        args.putString(ARG_APP_DESCRIPTION, app.getAppDescription());
        args.putString(ARG_APP_DOWNLOAD_URL, app.getAppDownloadUrl());
        args.putString(ARG_APP_IMAGE_URL, app.getAppImageUrl());
        votingActionFragment.setArguments(args);
        return votingActionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Gets app values from bundle.
         */
        mAppId = getArguments().getInt(ARG_APP_ID);
        mAppName = getArguments().getString(ARG_APP_NAME);
        mAppDownloadUrl = getArguments().getString(ARG_APP_DOWNLOAD_URL);
        mAppImageUrl = getArguments().getString(ARG_APP_IMAGE_URL);
        mAppDescription = getArguments().getString(ARG_APP_DESCRIPTION);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_voting_action, container, false);

        initializeView(rootView);
        return rootView;
    }

    /**
     * method that initialize all Views
     *
     * @param view
     */
    private void initializeView(View view) {
        /**
         * Find views by id.
         */
        textviewAppName = (RobotoMediumTextView) view.findViewById(R.id.textview_app_name);
        imageviewApp = (RoundedImageView) view.findViewById(R.id.imageview_app_image);
        imageviewLike = (ImageView) view.findViewById(R.id.image_like);
        imageviewDislike = (ImageView) view.findViewById(R.id.image_dislike);
        imageViewMenu = (ImageView) view.findViewById(R.id.imageview_menu);
        imageviewInfo = (ImageView) view.findViewById(R.id.image_info);
        /**
         * sets click listeners
         */
        imageviewLike.setOnClickListener(this);
        imageviewDislike.setOnClickListener(this);
        imageViewMenu.setOnClickListener(this);
        imageviewInfo.setOnClickListener(this);
        /**
         * Load values to widgets
         */
        Picasso.with(getActivity().getApplicationContext()).load(mAppImageUrl).into(imageviewApp);
        textviewAppName.setText(mAppName);
    }

    /**
     * OnClick method for imageview's
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        /**
         *create app object with values
         */
        App app = new App(mAppId, mAppName, mAppDescription, mAppImageUrl, mAppDownloadUrl);
        int vote = 0;
        /**
         * On button click cases.
         */
        switch (v.getId()) {
            case R.id.image_like:
                vote = ApiConstants.LIKE;
                imageviewLike.setBackgroundResource(R.drawable.icon_like_selected);
                animateVoteImagesOnVote(imageviewLike);
                /**
                 * set like or dislike and add app request queue.
                 */
                sendUserVote(app, vote);
                break;
            case R.id.image_dislike:
                vote = ApiConstants.DISLIKE;
                imageviewDislike.setBackgroundResource(R.drawable.icon_dislike_selected);
                animateVoteImagesOnVote(imageviewDislike);
                /**
                 * set like or dislike and add app request queue.
                 */
                sendUserVote(app, vote);
                break;
            case R.id.imageview_menu:
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity(), imageViewMenu);
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
            case R.id.image_info:
                openAppDetailPage();
                break;
            default:
                break;
        }
    }

    /**
     * set like or dislike and add app request queue.
     *
     * @param app  voted app instance
     * @param vote vote type like=1 or dislike =0
     */
    private void sendUserVote(App app, int vote) {
        app.setUserVote(vote);
        VoteRequestQueue.getInstance(getActivity().getApplicationContext()).addVote(app);
    }

    private void openAppAboutPage() {
        /**
         * creating transaction for fragment
         */
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        /**
         * creating animation for transaction
         */
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        ft.add(R.id.container, AboutFragment.newInstance(), "aboutFragment");
        ft.addToBackStack("aboutFragment");
        /** Start fragment */
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Animator of clicked vote button
     *
     * @param imageView
     */
    private void animateVoteImagesOnVote(ImageView imageView) {
        /**
         * Set like buttons non-clickable
         */
        imageviewDislike.setClickable(false);
        imageviewLike.setClickable(false);
        imageviewInfo.setClickable(false);

        /**
         * Animate clicked vote button and notify to change app when animation end.
         * It is enough to set listener to one of them. Could not found set multiple
         * animation setter in @YoYo library.
         */
        YoYo.with(Techniques.Pulse).duration(700).playOn(imageView);
        YoYo.with(Techniques.Swing).duration(700).withListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                votingActionFragmentCallback.onVotingAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        }).playOn(imageView);
    }

    /**
     * set MainVotingFragment to give call back.
     *
     * @param votingActionFragmentCallback
     */
    public void setVotingActionCallback(VotingActionFragmentCallback votingActionFragmentCallback) {
        this.votingActionFragmentCallback = votingActionFragmentCallback;
    }

    /**
     * show app detail page method
     */
    private void openAppDetailPage() {
        /**
         * creating transaction for fragment
         */
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        /**
         * creating animation for transaction
         */
        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
        ft.add(R.id.container, AppDetailFragment.newInstance(mAppName, mAppDescription, mAppDownloadUrl, mAppImageUrl), "appDetailFragment");
        ft.addToBackStack("appDetailFragment");
        /** Start fragment */
        ft.commitAllowingStateLoss();
    }
}
