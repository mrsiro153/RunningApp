package vn.intelin.android.app.running.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import vn.intelin.android.app.running.R;

public class ShowListDialog  extends DialogFragment {
    private List<String> lable;
    private OnItemClick itemClick;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_show_list,container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout layout = (LinearLayout) view;
        //
        for(String item: lable){
            TextView t = new TextView(this.getContext());
            t.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            t.setText(item);
            t.setOnClickListener(v->{
                dismiss();
                if(itemClick!=null)
                    itemClick.itemClick(t.getText().toString());
            });
            layout.addView(t);
        }
    }

    public static void show(FragmentManager manager, List<String> lable, @Nullable ShowListDialog.OnItemClick itemClick){
        ShowListDialog d = new ShowListDialog();
        d.lable = lable;
        d.itemClick = itemClick;
        d.show(manager,"ABCD");
    }

    @FunctionalInterface
    public interface OnItemClick{
        void itemClick(String item);
    }
}
