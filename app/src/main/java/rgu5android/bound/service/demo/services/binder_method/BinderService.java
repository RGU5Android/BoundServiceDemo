package rgu5android.bound.service.demo.services.binder_method;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class BinderService extends Service {

    private final LocalBinder mLocalBinder = new LocalBinder();
    private final Random mRandom = new Random();

    public BinderService() {
        Log.wtf("Service", "Constructor");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.wtf("Service", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.wtf("Service", "onBind");
        return mLocalBinder;
    }

    public int getRandom() {
        Log.wtf("Service", "getRandom");
        return mRandom.nextInt(1000);
    }

    @Override
    public void onDestroy() {
        Log.wtf("Service", "onDestroy");
        super.onDestroy();
    }

    public class LocalBinder extends Binder {
        public BinderService getService() {
            Log.wtf("Service", "LocalBinder:getService");
            return BinderService.this;
        }
    }
}
