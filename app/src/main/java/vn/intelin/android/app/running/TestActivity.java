package vn.intelin.android.app.running;

import androidx.appcompat.app.AppCompatActivity;
import vn.intelin.android.running.api.Api;
import vn.intelin.android.running.api.Server;
import vn.intelin.android.running.model.request.EventByTagRequest;
import vn.intelin.android.running.model.request.UserUpdateLocationRequest;
import vn.intelin.android.running.util.JsonConverter;
import vn.intelin.android.running.util.LogCat;

import android.os.Bundle;

public class TestActivity extends AppCompatActivity {
    private Server server = Server.getInstance();
    private final LogCat log = new LogCat(this.getClass());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        //
        findViewById(R.id.btn_test_get_user_by_condition).setOnClickListener(v -> {
            server.handle(Api.TEST_GET_USER_CONDITION, "", response -> {
                log.d("SERVER RESPONSE: " + JsonConverter.toJson(response));
            });
        });
        findViewById(R.id.btn_test_increase_participants).setOnClickListener(v -> {
            server.handle(Api.TEST_INCREASE_PARTICIPANTS,"",response -> {
                log.d("SERVER RESPONSE: "+JsonConverter.toJson(response));
            });
        });
        findViewById(R.id.btn_test_get_event_by_tag).setOnClickListener(v->{
            server.handle(Api.GET_EVENT_BY_TAG,JsonConverter.toJson(new EventByTagRequest().setTagId("chauTag")), response -> {
                log.d("SERVER RESPONSE: "+response.getData());
            });
        });
        findViewById(R.id.btn_insert_location).setOnClickListener(v->{
            UserUpdateLocationRequest request = new UserUpdateLocationRequest()
                    .setEventId("E000002")
                    .setUserId("doannh")
                    .setLat(12.36d)
                    .setLng(55.2d)
                    .setTime(System.currentTimeMillis());
            server.handle(Api.USER_UPDATE_LOCATION,JsonConverter.toJson(request),response -> {

            });
        });
    }
}
