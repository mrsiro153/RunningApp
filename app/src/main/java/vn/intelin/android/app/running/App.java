package vn.intelin.android.app.running;

import android.app.Application;

import vn.intelin.android.running.Backend;

public class App  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new Backend(this);
    }
}
