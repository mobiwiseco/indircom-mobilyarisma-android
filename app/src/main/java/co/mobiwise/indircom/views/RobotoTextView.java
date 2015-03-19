package co.mobiwise.indircom.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mac on 13/03/15.
 */
public class RobotoTextView extends TextView {

    public RobotoTextView(Context context) {
        super(context);
        init();
    }

    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RobotoTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getResources().getAssets(), "myriad.ttf");
        setTypeface(tf);
    }
}