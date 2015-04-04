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
    private static final String ARG_APP = "app";

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
     * The fragment's app
     */
    private App app;

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
        args.putParcelable(ARG_APP,app);
        votingActionFragment.setArguments(args);
        return votingActionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Gets app parcelable from bundle.
         */
        app = getArguments().getParcelable("app");
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
        Picasso.with(getActivity().getApplicationContext()).load(app.getAppImageUrl()).into(imageviewApp);
        textviewAppName.setText(app.getAppName());

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
        ft.add(R.id.container, AppDetailFragment.newInstance(app), "appDetailFragment");
        ft.addToBackStack("appDetailFragment");
        /** Start fragment */
        ft.commitAllowingStateLoss();
    }
}
