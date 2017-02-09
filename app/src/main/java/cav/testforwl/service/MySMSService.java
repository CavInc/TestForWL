package cav.testforwl.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import cav.testforwl.R;
import cav.testforwl.ui.WebMessageActivity;
import cav.testforwl.utils.ConstantManager;
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
        String deviceId = intent.getStringExtra(ConstantManager.ANDROID_ID);
        String deviceModel = intent.getStringExtra(ConstantManager.ANDROID_MODEL);
        if (isOnline()) {
            new AsyncReuest().execute(new String[]{deviceId, deviceModel});
        }

        myTask(deviceId);
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG,"BIND");

        return null;
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG,"Task REMOVED");
        if (Build.VERSION.SDK_INT == 19){
            // TODO возможно тут косяк и его надо править
            Intent restartIntent = new Intent(this, getClass());
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            PendingIntent pi = PendingIntent.getService(this, 1, restartIntent,
                    PendingIntent.FLAG_ONE_SHOT);
            restartIntent.putExtra("RESTART","");
            am.setExact(AlarmManager.RTC, System.currentTimeMillis() + 3000, pi);
        }
    }

    private void myTask(final String deviceID) {
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
                if (isOnline()) {
                    new GetREST().get_data(deviceID);
                    Log.d(TAG, deviceID);
                    sendNotification();
                }
            }
        }).start();
    }

    private static final int NOTIFY_ID = 101;

    private void sendNotification(){
        Context context = getApplicationContext();
        Intent notificationIntent = new Intent(context, WebMessageActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        Resources res = context.getResources();

        Notification.Builder builder;

        if (Build.VERSION.SDK_INT < 11) {
            builder = new Notification.Builder(context);
        }else {
            builder = new Notification.Builder(context);
        }


        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_sms_failed_black_24dp)
                //.setTicker(res.getString(R.string.warning)) // текст в строке состояния
                .setTicker("Последнее китайское предупреждение!")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                .setContentTitle("Напоминание")
                //.setContentText(res.getString(R.string.notifytext))
                .setContentText("Пора покормить кота"); // Текст уведомления

        Notification notification;

        if (Build.VERSION.SDK_INT < 16) {
            notification = builder.getNotification(); // до API 16
        } else {
            notification = builder.build();
        }

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, notification);
    }

    // проверяем включен ли интернетик
    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    class AsyncReuest extends AsyncTask<String[],Void,Void> {

        @Override
        protected Void doInBackground(String[]... strings) {
            Log.d(TAG,"START HTTP");

            new GetREST().registry(String.valueOf(strings[0][0]),String.valueOf(strings[0][1]));
            return null;
        }

    }
}
