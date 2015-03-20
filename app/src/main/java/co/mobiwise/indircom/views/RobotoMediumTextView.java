package co.mobiwise.indircom.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mertsimsek on 20/12/14.
 */
public class RobotoMediumTextView extends TextView {
    public RobotoMediumTextView(Context context) {
        super(context);
        init();
    }

    public RobotoMediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoMediumTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getResources().getAssets(), "robotomedium.ttf");
        setTypeface(tf);
    }
}
