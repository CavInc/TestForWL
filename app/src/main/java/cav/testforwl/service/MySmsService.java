package cav.testforwl.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import cav.testforwl.R;
import cav.testforwl.database.DBConnect;
import cav.testforwl.ui.SmsActivity;
import cav.testforwl.utils.ConstantManager;

/**
 * Сервис для обработки полученной cms
 */

public class MySmsService extends Service {
    private static final String TAG = "MYSMS-SERVICE";

    private Boolean change_sms = false;

    public MySmsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String sms_from = intent.getStringExtra(ConstantManager.SMS_FROM);
        String sms_body = intent.getStringExtra(ConstantManager.SMS_BODY);
        String sms_data = intent.getStringExtra(ConstantManager.SMS_DATE);
        change_sms = intent.getBooleanExtra(ConstantManager.SMS_LOCK,false);

        new SaveBase(sms_from,sms_body,sms_data).execute();

        Log.d(TAG,"SMS SERVICE START");
       // return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"DESTROY");
    }

    private void showNotification(){
        Context context = getApplicationContext();
        Intent smsActivity = new Intent(context, SmsActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0,smsActivity,0);

        Notification.Builder builder ;
        if (Build.VERSION.SDK_INT < 11) {
            builder = new Notification.Builder(context);
        }else {
            builder = new Notification.Builder(context);
        }

        builder.setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setContentTitle("Rugball")
                .setSmallIcon(R.drawable.ic_textsms_black_24dp);

        Notification notification;

        if (Build.VERSION.SDK_INT < 16) {
            notification = builder.getNotification(); // до API 16
        } else {
            notification = builder.build();
        }

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(R.drawable.ic_textsms_black_24dp, notification);

    }

    class SaveBase extends AsyncTask <Void,Void,Void>{
        private String mSms_from;
        private String mSms_body;
        private String mSms_data;

        public SaveBase(String sms_from,String sms_body,String sms_data){
            this.mSms_from = sms_from;
            this.mSms_body = sms_body;
            this.mSms_data = sms_data;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (change_sms) {
                showNotification();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG+" TASK","EXECUTE");
            DBConnect db= new DBConnect(getApplicationContext());
            db.insertSMS(this.mSms_from,this.mSms_body,this.mSms_data);
            return null;
        }
    }
}
