package rgu5android.bound.service.demo.services.messenger_method;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

public class MessengerService extends Service implements Handler.Callback {

    static final int MSG_SAY_HELLO = 1;
    final Messenger mMessenger;
    Handler mHandler;
    private Random mRandom;

    public MessengerService() {
        Log.wtf("Service", "Constructor");
        mHandler = new Handler(this);
        mMessenger = new Messenger(mHandler);
        mRandom = new Random();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.wtf("Service", "onBind");
        Toast.makeText(getApplicationContext(), "Binding", Toast.LENGTH_SHORT).show();
        return mMessenger.getBinder();
    }

    @Override
    public boolean handleMessage(Message msg) {
        Log.wtf("Service", msg.toString());
        switch (msg.what) {
            case MSG_SAY_HELLO:
                Bundle bundle = new Bundle();
                bundle.putInt("number", getRandom());
                msg.setData(bundle);

                break;
        }
        return false;
    }

    public int getRandom() {
        Log.wtf("Service", "getRandom");
        return mRandom.nextInt(1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.wtf("Service", "onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.wtf("Service", "onUnbind");
        return super.onUnbind(intent);
    }
}
