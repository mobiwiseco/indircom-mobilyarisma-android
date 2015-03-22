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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.nineoldandroids.animation.Animator;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.listener.VotingActionFragmentCallback;

/**
 * Created by mac on 21/03/15.
 */
public class VotingActionFragment extends Fragment implements View.OnClickListener{

    /**
     * To notify necessary method when vote action fragment process happen.
     */
    private VotingActionFragmentCallback voting_action_fragment_callback;

    /**
     * TAG to log.
     */
    public static final String TAG = "VotingActionFragment";

    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number
     */
    private int mPageNumber;

    /**
     * VotingActionFragment views
     */

    private RoundedImageView imageview_app;
    private ImageView imageview_like;
    private ImageView imageview_dislike;

    /**
     * Factory method for this fragment class
     */
    public static VotingActionFragment newInstance(int pageNumber) {
        VotingActionFragment votingActionFragment = new VotingActionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        votingActionFragment.setArguments(args);
        return votingActionFragment;
    }

    public VotingActionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
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

        imageview_app = (RoundedImageView) view.findViewById(R.id.imageview_app_image);
        imageview_like = (ImageView) view.findViewById(R.id.image_like);
        imageview_dislike = (ImageView) view.findViewById(R.id.image_dislike);

        imageview_like.setOnClickListener(this);
        imageview_dislike.setOnClickListener(this);
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {

        return mPageNumber;
    }

    /**
     * OnClick method for imageview's
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.imageview_app_image:
                voting_action_fragment_callback.setCurrentPage(mPageNumber + 1);
                break;
            case R.id.image_like:
                //TODO send request to request queue
                imageview_like.setBackgroundResource(R.drawable.icon_like_selected);
                animateVoteImagesOnVote(imageview_like);
                break;
            case R.id.image_dislike:
                //TODO send request to request queue
                imageview_dislike.setBackgroundResource(R.drawable.icon_dislike_selected);
                animateVoteImagesOnVote(imageview_dislike);
                break;
            default:
                break;
        }
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
    private void animateVoteImagesOnVote(ImageView imageView){

        /**
         * Set like buttons non-clickable
         */
        imageview_dislike.setClickable(false);
        imageview_like.setClickable(false);

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
                voting_action_fragment_callback.onVotingAnimationEnd();
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
     * @param voting_action_fragment_callback
     */
    public void setVotingActionCallback(VotingActionFragmentCallback voting_action_fragment_callback){
        this.voting_action_fragment_callback = voting_action_fragment_callback;
    }

}
