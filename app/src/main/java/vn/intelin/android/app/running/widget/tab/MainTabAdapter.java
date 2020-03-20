package vn.intelin.android.app.running.widget.tab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import vn.intelin.android.app.running.R;

public class MainTabAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private Context context;

    public MainTabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public MainTabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public MainTabAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment,String title){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tab_main, null);
        TextView tabTextView = view.findViewById(R.id.txt_item_tab_title);
        tabTextView.setText(mFragmentTitleList.get(position));
        return view;
    }
    public View getSelectedTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tab_main, null);
        TextView tabTextView = view.findViewById(R.id.txt_item_tab_title);
        tabTextView.setText(mFragmentTitleList.get(position));
        tabTextView.setTextColor(context.getResources().getColor(R.color.yellow));
        return view;
    }
}
