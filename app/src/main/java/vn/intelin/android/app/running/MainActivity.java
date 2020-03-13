package vn.intelin.android.app.running;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import vn.intelin.android.running.api.Api;
import vn.intelin.android.running.api.IResult;
import vn.intelin.android.running.api.Server;
import vn.intelin.android.running.model.db.User;
import vn.intelin.android.running.ui.SiroTestActivity;
import vn.intelin.android.running.util.JsonConverter;
import vn.intelin.android.running.util.LogCat;

public class MainActivity extends Activity {
    private final LogCat log = new LogCat(this.getClass());
    private Server server = Server.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        Button btnGetAllUser = findViewById(R.id.btn_test);
        btnGetAllUser.setOnClickListener(view -> server.handle(Api.GET_USERS, "nothing", btnGetAllUserHandler));
        //
        Button btnAddUser = findViewById(R.id.btn_add_user);
        btnAddUser.setOnClickListener(view -> {
            server.handle(Api.USER_LOGIN, JsonConverter.toJson(new User().setName("abc").setAge(444).setId("fffdfd")), btnAddUserHandler);
        });
        //
        Button btnGetUserCondition = findViewById(R.id.btn_get_user_condition);
        btnGetUserCondition.setOnClickListener(view -> {
            server.handle(Api.GET_USER_CONDITION,"",btnGetUserConditionHandler);
        });
        //
        Button btnLoginFb = findViewById(R.id.btn_login_facebook);
        btnLoginFb.setOnClickListener(v->{
            Intent i = new Intent(this, SiroTestActivity.class);
            startActivity(i);
        });
        //
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
}
