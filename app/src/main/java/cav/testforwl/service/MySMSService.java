package cav.testforwl.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import cav.testforwl.utils.GetREST;

public class MySMSService extends Service {

    private static final String TAG = "MYSERVICE";

    public MySMSService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"ON Create servis");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"DESTROY");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"START COMMAND");
        myTask();
        new AsyncReuest().execute();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG,"BIND");

        return null;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    private void myTask() {
        new Thread(new Runnable() {
            public void run() {
                for (int i = 1; i < 6; i ++) {
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("my_tag", "hello from service: " + i);
                }
            }
        }).start();
    }

    class AsyncReuest extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG,"START HTTP");
            new GetREST().registry("000000","TEST");
            return null;
        }
    }
}
