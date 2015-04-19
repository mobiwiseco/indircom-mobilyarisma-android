package co.mobiwise.indircom.utils;

import android.content.Context;

import co.mobiwise.indircom.R;
import co.mobiwise.indircom.views.RobotoMediumTextView;

public class MaterialDesignDialog extends com.afollestad.materialdialogs.MaterialDialog.Builder {

    private Context context;

    private MaterialDesignDialog(Context context) {

        super(context);
        this.context = context;
    }

    public static MaterialDesignDialog newInstance(Context context) {
        return new MaterialDesignDialog(context);
    }

    /**
     * createScanningDialog
     *
     * @param content
     * @return
     */
    public com.afollestad.materialdialogs.MaterialDialog createScanningDialog(String content) {

        com.afollestad.materialdialogs.MaterialDialog dialog = new com.afollestad.materialdialogs.MaterialDialog.Builder(context)
                .customView(R.layout.dialog_loading)
                .build();
        RobotoMediumTextView textview_dialog = (RobotoMediumTextView) dialog.getCustomView().findViewById(R.id.textview_cache_dialog_info);
        textview_dialog.setText(content);

        dialog.setCancelable(false);
        return dialog;
    }
}