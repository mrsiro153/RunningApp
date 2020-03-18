package vn.intelin.android.app.running.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.intelin.android.app.running.R;
import vn.intelin.android.app.running.util.DataAccess;
import vn.intelin.android.app.running.widget.InfoEditText;
import vn.intelin.android.app.running.widget.recycler.adapter.EventAdapter;
import vn.intelin.android.running.api.Api;
import vn.intelin.android.running.api.Server;
import vn.intelin.android.running.model.db.Event;
import vn.intelin.android.running.model.db.User;
import vn.intelin.android.running.util.JsonConverter;
import vn.intelin.android.running.util.LogCat;

public class MainActivity extends AppCompatActivity {
    private Server server = Server.getInstance();
    private LogCat log = new LogCat(this.getClass());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpView();
        setUpListener();
    }

    private void setUpView() {
        if (getActionBar() != null)
            getActionBar().hide();
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        //
        User u = JsonConverter.fromJson(String.valueOf(DataAccess.get(DataAccess.USER)), User.class);

        TextView textView = findViewById(R.id.txt_customer_name);
        textView.setText(u.getName());
        //
        ((InfoEditText) findViewById(R.id.item_user_info_name)).setEditTextValue(u.getName());
        ((InfoEditText) findViewById(R.id.item_user_info_email)).setEditTextValue(u.getEmail());
        InfoEditText infoAge = findViewById(R.id.item_user_info_age);
        if (u.getAge() != null)
            infoAge.setEditTextValue(String.valueOf(u.getAge()));
        infoAge.focusableEditText(true);
        //
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView = ((RecyclerView) findViewById(R.id.lst_event));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new EventAdapter(new ArrayList<>()));
    }

    private void setUpListener() {
        findViewById(R.id.btn_get_event).setOnClickListener(v->{
            server.handle(Api.GET_EVENT,"",rs->{
                log.d("SERVER RESPONSE OF API: "+Api.GET_EVENT+" with value: "+rs.getData());
                List<Event> lst = JsonConverter.fromJsonToList(JsonConverter.toJsonElement(String.valueOf(rs.getData())),Event[].class);
                log.i("ahahahahaahahah: "+lst.size());
                RecyclerView recyclerView = ((RecyclerView) findViewById(R.id.lst_event));
                ((EventAdapter)recyclerView.getAdapter()).addAll(lst);
            });
        });
    }
}
