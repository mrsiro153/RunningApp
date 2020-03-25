package vn.intelin.android.app.running.ui.children;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import vn.intelin.android.app.running.R;
import vn.intelin.android.app.running.util.DataAccess;
import vn.intelin.android.app.running.widget.InfoEditText;
import vn.intelin.android.running.model.db.User;
import vn.intelin.android.running.util.JsonConverter;
import vn.intelin.android.running.util.LogCat;

public class UserInfoFragment extends Fragment {
    private final LogCat log = new LogCat(this.getClass());
    private User userInf;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_info,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInf = JsonConverter.fromJson(String.valueOf(DataAccess.getAs(DataAccess.DataKey.USER,String.class)), User.class);
        setUpView();
    }
    private void setUpView(){
        ((TextView) this.getView().findViewById(R.id.txt_customer_name)).setText(userInf.getName());
        ((InfoEditText) this.getView().findViewById(R.id.item_user_info_name)).setEditTextValue(userInf.getName());
        ((InfoEditText) this.getView().findViewById(R.id.item_user_info_email)).setEditTextValue(userInf.getEmail());
        ((InfoEditText) this.getView().findViewById(R.id.item_user_info_dob)).setEditTextValue(""+userInf.getDob());
        ((InfoEditText) Objects.requireNonNull(this.getView()).findViewById(R.id.item_user_info_gender)).setEditTextValue(""+userInf.getGender());
        ((InfoEditText) Objects.requireNonNull(this.getView()).findViewById(R.id.item_user_info_height)).setEditTextValue(""+userInf.getHeight());
        ((InfoEditText) Objects.requireNonNull(this.getView()).findViewById(R.id.item_user_info_weight)).setEditTextValue(""+userInf.getWeight());
        ((InfoEditText) Objects.requireNonNull(this.getView()).findViewById(R.id.item_user_info_phone)).setEditTextValue(""+userInf.getPhone());
    }
    public void refresh(){
        setUpView();
    }
}
