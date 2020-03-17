package vn.intelin.android.app.running.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import vn.intelin.android.app.running.R;
import vn.intelin.android.app.running.util.DataAccess;
import vn.intelin.android.running.model.db.User;
import vn.intelin.android.running.util.JsonConverter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpView();
    }
    private void setUpView(){
        if(getActionBar()!=null)
            getActionBar().hide();
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();
        //
        User u = JsonConverter.fromJson(String.valueOf(DataAccess.get(DataAccess.USER)), User.class);

        TextView textView = findViewById(R.id.txt_userName);
        textView.setText(u.getName());
    }
    private void setUpListener(){

    }
}
