package vn.intelin.android.app.running;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import vn.intelin.android.running.api.Api;
import vn.intelin.android.running.api.IResult;
import vn.intelin.android.running.api.Server;
import vn.intelin.android.running.util.LogCat;

public class MainActivity extends AppCompatActivity {
    private final LogCat log = new LogCat(this.getClass());
    private Server server = Server.getInstance();

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        button = (Button) findViewById(R.id.btn_test);
        button.setOnClickListener(view -> server.handle(Api.GET_USERS, "nothing", btnTestResult));
    }

    private IResult btnTestResult = response -> {
        log.e("SERVER RESPONSE: " + response.getData());
        button.setText("AHsHSHAHSAHS");
    };
}
