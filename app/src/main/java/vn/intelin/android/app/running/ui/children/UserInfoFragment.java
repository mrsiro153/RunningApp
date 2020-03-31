package vn.intelin.android.app.running.ui.children;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import vn.intelin.android.app.running.R;
import vn.intelin.android.app.running.util.DataAccess;
import vn.intelin.android.app.running.widget.InfoEditText;
import vn.intelin.android.app.running.widget.LoadingDialog;
import vn.intelin.android.app.running.widget.ShowListDialog;
import vn.intelin.android.running.api.Api;
import vn.intelin.android.running.api.CodeResponse;
import vn.intelin.android.running.api.Server;
import vn.intelin.android.running.model.db.User;
import vn.intelin.android.running.model.request.UploadRequest;
import vn.intelin.android.running.model.response.UploadAvatarResponse;
import vn.intelin.android.running.util.JsonConverter;
import vn.intelin.android.running.util.LogCat;

public class UserInfoFragment extends Fragment {
    private final LogCat log = new LogCat(this.getClass());
    private final Server server = Server.getInstance();
    private User userInf;
    //key zone
    private static final String GALLERY = "Choose from gallery";
    private static final String CAMERA = "Choose from camera";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInf = JsonConverter.fromJson(String.valueOf(DataAccess.getAs(DataAccess.DataKey.USER, String.class)), User.class);
        setUpView();
        setUpListener();
    }

    private void setUpView() {
        ((TextView) this.getView().findViewById(R.id.txt_customer_name)).setText(userInf.getName());
        ((InfoEditText) this.getView().findViewById(R.id.item_user_info_name)).setEditTextValue(userInf.getName());
        ((InfoEditText) this.getView().findViewById(R.id.item_user_info_email)).setEditTextValue(userInf.getEmail());
        ((InfoEditText) this.getView().findViewById(R.id.item_user_info_dob)).setEditTextValue("" + userInf.getDob());
        ((InfoEditText) Objects.requireNonNull(this.getView()).findViewById(R.id.item_user_info_gender)).setEditTextValue("" + userInf.getGender());
        ((InfoEditText) Objects.requireNonNull(this.getView()).findViewById(R.id.item_user_info_height)).setEditTextValue("" + userInf.getHeight());
        ((InfoEditText) Objects.requireNonNull(this.getView()).findViewById(R.id.item_user_info_weight)).setEditTextValue("" + userInf.getWeight());
        ((InfoEditText) Objects.requireNonNull(this.getView()).findViewById(R.id.item_user_info_phone)).setEditTextValue("" + userInf.getPhone());
        if(userInf.getPhoto()!=null){
            Picasso.with(this.getActivity())
                    .load(userInf.getPhoto())
                    .error(R.drawable.ic_next)
                    .noFade()
                    .into(((CircleImageView) getView().findViewById(R.id.img_user_avatar)));
        }
    }

    public void refresh() {
        setUpView();
    }

    private void setUpListener() {
        Objects.requireNonNull(getView()).findViewById(R.id.img_user_avatar).setOnClickListener(v -> {
            List<String> lst = Arrays.asList(GALLERY, CAMERA);
            ShowListDialog.show(((AppCompatActivity) this.getContext()).getSupportFragmentManager(), lst, resp -> {
                Sources imgSrc = Sources.GALLERY;
                switch (resp) {
                    case GALLERY:
                        //user choose from gallery
                        imgSrc = Sources.GALLERY;
                        break;
                    case CAMERA:
                        imgSrc = Sources.CAMERA;
                        break;
                }
                //todo upload image to server
                RxImagePicker.with(getActivity())
                        .requestImage(imgSrc)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(it->{
                            handleUploadImage(it);
                            Picasso.with(this.getActivity())
                                    .load(it.toString())
                                    .error(R.drawable.ic_next)
                                    .noFade()
                                    .into(((CircleImageView) getView().findViewById(R.id.img_user_avatar)));
                        }, er -> {
                            log.e("Error upload image", er);
                        });
            });
        });
    }

    private void handleUploadImage(Uri uri) {
        //todo doannh
        LoadingDialog.showLoading(this.getFragmentManager());
        server.handle(Api.USER_UPLOAD_AVATAR, JsonConverter.toJson(new UploadRequest().setUriFile(uri.toString()).setUserId(userInf.getId())), response -> {
            LoadingDialog.remove();
//            if (response.getCode().equals(CodeResponse.OK.code)) {
//                UploadAvatarResponse resp = JsonConverter.fromJson(response.getData(), UploadAvatarResponse.class);
//                CircleImageView img = ((CircleImageView) getView().findViewById(R.id.img_user_avatar));
//                //todo maybe don't care!
//
//            }
        });
    }
}
