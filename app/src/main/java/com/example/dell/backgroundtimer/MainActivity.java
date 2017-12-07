package com.example.dell.backgroundtimer;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.BroadcastReceiver;

public class MainActivity extends AppCompatActivity {
    PowerManager.WakeLock wl;
    int h;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = (Button) findViewById(R.id.start);
        Button stop = (Button) findViewById(R.id.stop);

        //register broadcast receiver
        IntentFilter filt = new IntentFilter("filter");
        MainActivity.this.registerReceiver(br, filt);

        final Intent serviceIntent = new Intent(MainActivity.this, TimerService.class);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
                wl.acquire();

                startService(serviceIntent);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopService(serviceIntent);

            }
        });

    }

    //BroadcastReceiver to print the counter value when activity!=null
    BroadcastReceiver br=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent c) {

              h = (int) c.getExtras().get("msg");
            Log.e("mainActivity_counter", " " + h);

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("log","onResume");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("log","onStart");
    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.e("log","onPause");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("log","onDestroy");
        unregisterReceiver(br);

    }


    @Override
    protected void onStop() {
        super.onStop();

    }
}
