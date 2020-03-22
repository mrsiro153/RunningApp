package vn.intelin.android.app.running.widget;

import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {
    public static Toast errorToast(Toast toast) {
        toast.getView().setBackgroundColor(Color.parseColor("#ff3333"));
        ((TextView) toast.getView().findViewById(android.R.id.message)).setTextColor(Color.parseColor("#ffffff"));
        return toast;
    }

    public static Toast successToast(Toast toast) {
        toast.getView().setBackgroundColor(Color.parseColor("#1f7a1f"));
        ((TextView) toast.getView().findViewById(android.R.id.message)).setTextColor(Color.parseColor("#ffffff"));
        return toast;
    }
}
