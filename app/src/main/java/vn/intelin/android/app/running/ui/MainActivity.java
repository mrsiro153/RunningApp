package vn.intelin.android.app.running.ui;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import vn.intelin.android.app.running.R;
import vn.intelin.android.app.running.ui.children.EventFragment;
import vn.intelin.android.app.running.ui.children.MapFragment;
import vn.intelin.android.app.running.ui.children.UserInfoFragment;
import vn.intelin.android.app.running.widget.tab.MainTabAdapter;
import vn.intelin.android.running.api.Server;
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

        setUpTab();
    }

    private void setUpListener() {

    }

    private void setUpTab() {
        TabLayout tabs = findViewById(R.id.tab_main);
        ViewPager viewPager = findViewById(R.id.vp_main);
        MainTabAdapter tabAdapter = new MainTabAdapter(getSupportFragmentManager(), this);
        tabAdapter.addFragment(new UserInfoFragment(), "user");
        tabAdapter.addFragment(new EventFragment(), "event");
        tabAdapter.addFragment(new MapFragment(), "map");
        viewPager.setAdapter(tabAdapter);
        tabs.setupWithViewPager(viewPager);
        //
        for (int i = 0; i < tabs.getTabCount(); i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);
            if (tab == null) throw new AssertionError();
            tab.setCustomView(null);
            tab.setCustomView(tabAdapter.getTabView(i));
            //
        }
        tabs.getTabAt(0).setCustomView(null);
        tabs.getTabAt(0).setCustomView(tabAdapter.getSelectedTabView(0));
        //
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //nothing
            }

            @Override
            public void onPageSelected(int position) {
                for(int i =0;i<tabAdapter.getCount();i++){
                    TabLayout.Tab tab = tabs.getTabAt(i);
                    if(i==position){
                        tab.setCustomView(null);
                        tab.setCustomView(tabAdapter.getSelectedTabView(position));
                    }else {
                        tab.setCustomView(null);
                        tab.setCustomView(tabAdapter.getTabView(i));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //nothing
            }
        });
    }
}
