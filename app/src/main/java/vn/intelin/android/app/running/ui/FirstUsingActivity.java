package vn.intelin.android.app.running.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import vn.intelin.android.app.running.R;
import vn.intelin.android.app.running.util.DataAccess;
import vn.intelin.android.app.running.widget.InfoEditText;
import vn.intelin.android.app.running.widget.ToastUtil;
import vn.intelin.android.running.api.Api;
import vn.intelin.android.running.api.CodeResponse;
import vn.intelin.android.running.api.Server;
import vn.intelin.android.running.model.db.User;
import vn.intelin.android.running.util.JsonConverter;

public class FirstUsingActivity extends AppCompatActivity {
    private Server server = Server.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_using);
        //
        setUpView();
        setUpListener();
    }

    private void setUpView() {
        if (getActionBar() != null) getActionBar().hide();
        if (getSupportActionBar() != null) getSupportActionBar().hide();

    }

    private void setUpListener() {
        findViewById(R.id.btn_first_login_im_done).setOnClickListener(v -> {
            try {
                User user = new User();
                String fullName = ((InfoEditText) findViewById(R.id.idt_first_login_full_name)).getEditTextValue();
                String phoneNumber = ((InfoEditText) findViewById(R.id.idt_first_login_phone_number)).getEditTextValue();
                String dob = ((InfoEditText) findViewById(R.id.idt_first_login_dob)).getEditTextValue();
                String gender = ((InfoEditText) findViewById(R.id.idt_first_login_gender)).getEditTextValue();
                String height = ((InfoEditText) findViewById(R.id.idt_first_login_height)).getEditTextValue();
                String weight = ((InfoEditText) findViewById(R.id.idt_first_login_weight)).getEditTextValue();

                String pattern = "dd/MM/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                Date date = simpleDateFormat.parse(dob);

                int sex = 0;
                switch (gender) {
                    case "male":
                        sex = 1;
                        break;
                    case "female":
                        sex = 2;
                        break;
                    default:
                        sex = 3;
                }

                user.setName(fullName)
                        .setPhone(phoneNumber)
                        .setDob(date.getTime())
                        .setGender(sex)
                        .setHeight(Double.valueOf(height))
                        .setWeight(Double.valueOf(weight));
                server.handle(Api.USER_NEW_USER_INFO, JsonConverter.toJson(user), response -> {
                    CodeResponse code = CodeResponse.find(response.getCode());
                    switch (code) {
                        case OK:
                            DataAccess.push(DataAccess.DataKey.USER, response.getData());
                            Intent i = new Intent(this, MainActivity.class);
                            startActivity(i);
                            finish();
                            break;
                        default:
                            Toast t = Toast.makeText(this, "Error in register new info", Toast.LENGTH_SHORT);
                            ToastUtil.errorToast(t).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
