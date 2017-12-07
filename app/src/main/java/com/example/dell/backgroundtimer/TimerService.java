package com.example.dell.backgroundtimer;

/**
 * Created by DELL on 07-12-2017.
 */

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class TimerService  extends Service {
    public static String LOG_TAG = "TimerService";


    private IBinder mBinder = new MyBinder();

    Handler onlineTimeHandler = new Handler();

    int i;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.v(LOG_TAG, "in onCreate");


        startTimer();

        // tStartTime = System.currentTimeMillis();  //for milliseconds

    }

    private void startTimer() {
        onlineTimeHandler.postDelayed(updateTimerThread,1000);

    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(LOG_TAG, "in onBind");
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v(LOG_TAG, "in onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(LOG_TAG, "in onUnbind");
        return true;
    }

    //onDestroy called when the service is stopped
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "in onDestroy");
        if(onlineTimeHandler!=null){
            onlineTimeHandler.removeCallbacks(updateTimerThread);

        }
    }



    public class MyBinder extends Binder {
        TimerService getService() {
            return TimerService.this;
        }
    }


    //will print the counter value in loop
    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            i++;

            Log.e("counter" ," "+i);

            //sending counter value to mainActivity
            Intent c=new Intent();
            c.putExtra("msg",+i);
            c.setAction("filter");
            sendBroadcast(c);

            onlineTimeHandler.postDelayed(this, 1 * 1000);
        }

    };

}
