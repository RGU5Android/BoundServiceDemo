package rgu5android.bound.service.demo.services.messenger_method;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import rgu5android.bound.service.demo.R;

public class ActivityMessenger extends AppCompatActivity implements View.OnClickListener, Handler.Callback {

    private Button mStartMessengerServiceButton;
    private Messenger mServiceMessenger;
    private boolean mBound;
    private Handler mHandler;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.wtf("Activity", "ServiceConnection:onServiceConnected " + name.toString());
            mServiceMessenger = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.wtf("Activity", "ServiceConnection:onServiceDisconnected " + name.toString());
            mServiceMessenger = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        mStartMessengerServiceButton = (Button) findViewById(R.id.button_start_messenger_service);
        mStartMessengerServiceButton.setOnClickListener(this);
        mHandler = new Handler(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_start_messenger_service) {
            if (!mBound) return;
            Message message = Message.obtain(null, MessengerService.MSG_SAY_HELLO, 0, 0);
            try {
                mServiceMessenger.send(message);
            } catch (RemoteException e) {
                Log.wtf("Activity", e.toString());
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == MessengerService.MSG_SAY_HELLO) {
            Toast.makeText(this, "Random Number:", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
