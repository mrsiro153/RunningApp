package vn.intelin.android.app.running.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.intelin.android.app.running.R;
import vn.intelin.android.app.running.util.DataAccess;
import vn.intelin.android.app.running.widget.LoadingDialog;
import vn.intelin.android.running.api.Api;
import vn.intelin.android.running.api.CodeResponse;
import vn.intelin.android.running.api.IResult;
import vn.intelin.android.running.api.Server;
import vn.intelin.android.running.ui.SiroTestActivity;
import vn.intelin.android.running.util.JsonConverter;
import vn.intelin.android.running.util.LogCat;

public class GreetingActivity extends AppCompatActivity {
    private final LogCat log = new LogCat(this.getClass());
    private Server server = Server.getInstance();
    public static int FB_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);
        setUpView();
        setUpListener();
        doFirst();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FB_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                log.i("login with fb success");
                handleAutoLogin();
            }
        }
    }

    private IResult btnGetAllUserHandler = response -> {
        log.i("SERVER RESPONSE: " + JsonConverter.toJson(response));
        Toast.makeText(this, "get all user success", Toast.LENGTH_SHORT).show();
    };

    private IResult btnAddUserHandler = response -> {
        log.i("SERVER RESPONSE: " + JsonConverter.toJson(response));
        Toast.makeText(this, "add user success", Toast.LENGTH_SHORT).show();
    };

    private IResult btnGetUserConditionHandler = response -> {
        log.i("SERVER RESPONSE: " + JsonConverter.toJson(response));
        Toast.makeText(this, "get User condition success", Toast.LENGTH_SHORT).show();
    };

    private void setUpView() {
        //todo setupView
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void setUpListener() {
        //todo setup listener
        findViewById(R.id.btn_login_fb).setOnClickListener(v->{
            startLoginFb();
        });
    }

    private void doFirst() {
        LoadingDialog.showLoading(this.getSupportFragmentManager());
        server.handle(Api.USER_CHECK_LOGIN, "", result -> {
            log.d("api : " + Api.USER_CHECK_LOGIN + " with response value: " + JsonConverter.toJson(result));
            LoadingDialog.remove();
            if (result.getCode().equals(CodeResponse.NOT_LOGIN.code)) {
                //startLoginFb();
                return;
            }
            if (result.getCode().equals(CodeResponse.OK.code)) {
                handleAutoLogin();
                return;
            }

        });
    }

    private void handleAutoLogin() {
        LoadingDialog.showLoading(this.getSupportFragmentManager());
        server.handle(Api.USER_LOGIN, "", result -> {
            log.d("server response of api: " + Api.USER_LOGIN + " with response: " + JsonConverter.toJson(result));
            LoadingDialog.remove();
            if (result.getCode().equals(CodeResponse.OK.code)) {
                DataAccess.push(DataAccess.USER, result.getData());
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void startLoginFb() {
        Intent i = new Intent(this, SiroTestActivity.class);
        startActivityForResult(i, FB_REQUEST);
    }
}
