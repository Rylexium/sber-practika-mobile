package com.example.sber_practika.utils.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sber_practika.R;


public class ShowCustomDialog {
    private Dialog dialog;
    private Button yes, no;

    public ShowCustomDialog showDialog(Activity activity, String textTitle,
                                       String positiveText, String negativeText) {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialogAnimation; //Setting the animations to dialog

        TextView title = dialog.findViewById(R.id.custom_dialog_title);
        title.setText(textTitle);

        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        no = dialog.findViewById(R.id.custom_dialog_no);
        yes = dialog.findViewById(R.id.custom_dialog_yes);

        no.setText(negativeText);
        yes.setText(positiveText);

        yes.setOnClickListener(view -> dialog.dismiss());
        no.setOnClickListener(view -> dialog.dismiss());

        dialog.show();

        return this;
    }

    public ShowCustomDialog setOnYes(Runnable runnable){
        yes.setOnClickListener(view -> {
            dialog.dismiss();
            new Handler(Looper.getMainLooper()).postDelayed(runnable, 250);
        });
        return this;
    }

    public ShowCustomDialog setOnNo(Runnable runnable){
        no.setOnClickListener(view -> {
            dialog.dismiss();
            new Handler(Looper.getMainLooper()).postDelayed(runnable, 250);
        });
        return this;
    }

    public ShowCustomDialog setOnDismiss(Runnable runnable){
        dialog.setOnDismissListener(view -> new Handler(Looper.getMainLooper()).postDelayed(runnable, 250));
        return this;
    }
}

