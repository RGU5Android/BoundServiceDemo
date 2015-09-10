package rgu5android.bound.service.demo.services.binder_method;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import rgu5android.bound.service.demo.R;

public class ActivityBinder extends AppCompatActivity implements View.OnClickListener {

    private BinderService mBinderService;
    private boolean mBound;
    private Button mStartBinderServiceButton;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.wtf("Activity", "ServiceConnection:onServiceConnected " + name.toString());
            BinderService.LocalBinder localBinder = (BinderService.LocalBinder) service;
            mBinderService = localBinder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.wtf("Activity", "ServiceConnection:onServiceDisconnected " + name.toString());
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);
        mStartBinderServiceButton = (Button) findViewById(R.id.button_start_binder_service);
        mStartBinderServiceButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, BinderService.class);
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
        if (v.getId() == R.id.button_start_binder_service) {
            Toast.makeText(this, "Random Number is: " +
                    mBinderService.getRandom(), Toast.LENGTH_SHORT).show();
        }
    }
}
