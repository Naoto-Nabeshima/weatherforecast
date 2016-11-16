package com.org.app.weatherforecast;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ConnectionService extends Service {
    private static final String LOG_TAG = ConnectionService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        new Thread(new ConnectionThread("", "")).run();//スレッドの起動
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
