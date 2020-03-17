package vn.intelin.android.app.running.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import vn.intelin.android.app.running.R;
import vn.intelin.android.running.util.LogCat;

public class LoadingDialog extends DialogFragment {
    private static final LogCat log = new LogCat(LoadingDialog.class);
    private static final String TAG = "loading";
    private static boolean isEnable = false;
    private static LoadingDialog instance;
    private static Handler handler;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static void showLoading(FragmentManager manager) {
        if (LoadingDialog.isEnable) {
            return;
        }
        LoadingDialog.isEnable = true;
        LoadingDialog d = new LoadingDialog();
        instance = d;
        d.show(manager, LoadingDialog.TAG);
        handler = new Handler();
        handler.postDelayed(() -> {
            LoadingDialog.isEnable = false;
            d.dismiss();
        }, 30000);
    }

    public static void remove() {
        if (instance != null) {
            LoadingDialog.isEnable = false;
            handler.removeCallbacksAndMessages(null);
            instance.dismiss();
        }
    }

}
