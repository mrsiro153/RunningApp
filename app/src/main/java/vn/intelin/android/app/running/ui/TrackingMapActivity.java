package vn.intelin.android.app.running.ui;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AppCompatActivity;
import vn.intelin.android.app.running.R;
import vn.intelin.android.app.running.widget.InfoEditText;
import vn.intelin.android.app.running.widget.LoadingDialog;
import vn.intelin.android.app.running.widget.ToastUtil;
import vn.intelin.android.running.api.Api;
import vn.intelin.android.running.api.CodeResponse;
import vn.intelin.android.running.api.Server;
import vn.intelin.android.running.model.db.EventUser;
import vn.intelin.android.running.model.request.UserUpdateLocationRequest;
import vn.intelin.android.running.util.JsonConverter;
import vn.intelin.android.running.util.LogCat;

public class TrackingMapActivity extends AppCompatActivity {
    private final LogCat log = new LogCat(this.getClass());
    private EventUser eventUser;
    private Server server = Server.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_map);
        setUpView();
        setUpEventListener();
        doFirst();
    }

    private void setUpView() {
        if (this.getActionBar() != null) {
            this.getActionBar().hide();
        }
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().hide();
        }
    }

    private void setUpEventListener() {
        MaterialButton btnSubmit = findViewById(R.id.btn_tracking_map_submit);
        btnSubmit.setOnClickListener(view -> {
            try {
                Double lat = Double.valueOf(((InfoEditText) findViewById(R.id.idt_tracking_map_lat)).getEditTextValue());
                Double lng = Double.valueOf(((InfoEditText) findViewById(R.id.idt_tracking_map_lng)).getEditTextValue());

            UserUpdateLocationRequest request = new UserUpdateLocationRequest()
                    .setUserId(eventUser.getUserId())
                    .setEventId(eventUser.getEventId())
                    .setTime(System.currentTimeMillis())
                    .setLat(lat)
                    .setLng(lng);
            LoadingDialog.showLoading(this.getSupportFragmentManager());
            server.handle(Api.USER_UPDATE_LOCATION, JsonConverter.toJson(request), response -> {
                LoadingDialog.remove();
                if (!response.getCode().equals(CodeResponse.OK.code)) {
                    ToastUtil.errorToast(Toast.makeText(this, "Can not save location", Toast.LENGTH_SHORT)).show();
                }

                ((InfoEditText) findViewById(R.id.idt_tracking_map_lat)).clear();
                ((InfoEditText) findViewById(R.id.idt_tracking_map_lng)).clear();
            });
            }catch (Exception e){
                log.e("ERRRRRRROR! "+e.getMessage());
                ToastUtil.errorToast(Toast.makeText(this,"check you lat and lng value",Toast.LENGTH_SHORT)).show();
            }
        });
    }

    private void doFirst() {
        eventUser = JsonConverter.fromJson(getIntent().getStringExtra("eventUser"), EventUser.class);
        ((InfoEditText) findViewById(R.id.idt_tracking_map_username)).setEditTextValue(eventUser.getName());
        ((InfoEditText) findViewById(R.id.idt_tracking_map_event)).setEditTextValue(eventUser.getEventId());
    }


}
