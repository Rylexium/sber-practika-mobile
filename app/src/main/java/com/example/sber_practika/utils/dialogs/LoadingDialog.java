package com.example.sber_practika.utils.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import com.example.sber_practika.R;


public class LoadingDialog {
    private final Activity activity;
    private AlertDialog dialog;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(false);
        builder.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK)
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    activity.finish();
                    return true;
                }
            return false;
        });

        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }
}

