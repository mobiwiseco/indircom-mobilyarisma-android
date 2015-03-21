package co.mobiwise.indircom.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.listener.PagerCurrentItemListener;

/**
 * Created by mac on 21/03/15.
 */
public class VotingActionFragment extends Fragment implements View.OnClickListener {

    private PagerCurrentItemListener pagerCurrentItemListener;

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

    private ImageView imageview_app_image, imageview_app_like, imageview_app_info, imageview_app_dislike;
    private TextView textview_app_name, textview_app_category;

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

        Log.v(TAG, String.valueOf(getPageNumber()));

        imageview_app_image = (ImageView) view.findViewById(R.id.imageview_app_image);
        imageview_app_like = (ImageView) view.findViewById(R.id.imageview_app_like);
        imageview_app_info = (ImageView) view.findViewById(R.id.imageview_app_info);
        imageview_app_dislike = (ImageView) view.findViewById(R.id.imageview_app_dislike);

        imageview_app_image.setOnClickListener(this);
        imageview_app_like.setOnClickListener(this);
        imageview_app_info.setOnClickListener(this);
        imageview_app_dislike.setOnClickListener(this);


        textview_app_name = (TextView) view.findViewById(R.id.textview_app_name);
        textview_app_category = (TextView) view.findViewById(R.id.textview_app_category);

        textview_app_name.setText(String.valueOf(getPageNumber()));
        textview_app_category.setText(String.valueOf(getPageNumber()));

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
                pagerCurrentItemListener.setCurrentPage(mPageNumber + 1);
                break;
            case R.id.imageview_app_like:
                imageViewAnimatedChange(getActivity().getApplicationContext(), imageview_app_like, convertDrawabletoBitmap(R.drawable.ic_launcher));
                break;
            case R.id.imageview_app_info:
                break;
            case R.id.imageview_app_dislike:
                imageViewAnimatedChange(getActivity().getApplicationContext(), imageview_app_dislike, convertDrawabletoBitmap(R.drawable.ic_launcher));
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            pagerCurrentItemListener = (PagerCurrentItemListener) getActivity();
        } catch (ClassCastException e) {
            throw new RuntimeException("The activity that contains MainVotingPageFragment must implement FacebookLoginFragment.FacebookAuthListener");
        }
    }

    /**
     * Creating animation on ImageView while changing image resource
     *
     * @param c         context
     * @param v         imageview
     * @param new_image image reource
     */
    public void imageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
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
}
