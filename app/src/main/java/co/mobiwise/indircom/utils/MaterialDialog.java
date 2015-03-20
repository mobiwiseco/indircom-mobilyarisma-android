package co.mobiwise.indircom.utils;

import android.app.Activity;
import android.content.Context;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.views.RobotoMediumTextView;

/**
 * Created by Bahadır on 20.3.2015.
 */
public class MaterialDialog extends com.afollestad.materialdialogs.MaterialDialog.Builder{


    public static MaterialDialog newInstance(Context context){
        return new MaterialDialog(context);
    }


    private MaterialDialog(Context context) {
        super(context);
    }

    /**
     * createScanningDialog
     * @param content
     * @param activity
     * @return
     */
    public com.afollestad.materialdialogs.MaterialDialog createScanningDialog(String content,Activity activity){

        com.afollestad.materialdialogs.MaterialDialog dialog = new com.afollestad.materialdialogs.MaterialDialog.Builder(activity)
                .customView(R.layout.dialog_loading)
                .build();
        RobotoMediumTextView textview_dialog = (RobotoMediumTextView) dialog.getCustomView().findViewById(R.id.textview_cache_dialog_info);
        textview_dialog.setText(content);

        dialog.setCancelable(false);
        return dialog;
    }


}