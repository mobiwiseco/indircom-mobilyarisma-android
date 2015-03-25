package co.mobiwise.indircom.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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

/**
 * Created by mac on 21/03/15.
 */
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
     * To notify necessary method when vote action fragment process happen.
     */
    private VotingActionFragmentCallback votingActionFragmentCallback;

    /**
     * TAG to log.
     */
    public static final String TAG = "VotingActionFragment";

    /**
     * VotingActionFragment views
     */
    public static final String ARG_PAGE = "page";

    private RoundedImageView imageviewApp;
    private ImageView imageviewLike;
    private ImageView imageviewDislike;
    private RobotoMediumTextView textviewAppName;

    /**
     * The fragment's values to show users
     */
    private int  mAppId;
    private String mAppName, mAppDescription, mAppDownloadUrl, mAppImageUrl;

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

    public VotingActionFragment() {
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
        /**
         * sets click listeners
         */
        imageviewLike.setOnClickListener(this);
        imageviewDislike.setOnClickListener(this);

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
        App app = new App(mAppId,mAppName,mAppDescription,mAppImageUrl,mAppDownloadUrl);
        int vote = 0;

        /**
         * On button click cases.
         */
        switch (v.getId()) {
            case R.id.image_like:
                vote = ApiConstants.LIKE;
                imageviewLike.setBackgroundResource(R.drawable.icon_like_selected);
                animateVoteImagesOnVote(imageviewLike);
                break;
            case R.id.image_dislike:
                vote = ApiConstants.DISLIKE;
                imageviewDislike.setBackgroundResource(R.drawable.icon_dislike_selected);
                animateVoteImagesOnVote(imageviewDislike);
                break;
            default:
                break;
        }

        /**
         * set like or dislike and add app request queue.
         */
        app.setUserVote(vote);
        VoteRequestQueue.getInstance(getActivity().getApplicationContext()).addVote(app);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * convert a Drawable to a Bitmap
     *
     * @param image_resource
     * @return
     */
    private Bitmap convertDrawabletoBitmap(int image_resource) {
        return BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(),
                image_resource);
    }

    /**
     * Animator of clicked vote button
     * @param imageView
     */
    private void animateVoteImagesOnVote(ImageView imageView) {

        /**
         * Set like buttons non-clickable
         */
        imageviewDislike.setClickable(false);
        imageviewLike.setClickable(false);

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
     * @param votingActionFragmentCallback
     */
    public void setVotingActionCallback(VotingActionFragmentCallback votingActionFragmentCallback){
        this.votingActionFragmentCallback = votingActionFragmentCallback;
    }

    /**
     * show app detail page method
     */
    private void openAppDetailPage() {

        //TODO remove the comment after merge

        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.container, AppDetailFragment.newInstance("mAppName", "mAppDescription", "mAppDownloadUrl")).addToBackStack("AppDetailFragment").setCustomAnimations(R.animator.slide_up,
                R.animator.slide_down,
                R.animator.slide_up,
                R.animator.slide_down)
                .commit();
    }
}
