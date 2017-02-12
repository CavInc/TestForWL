package cav.testforwl.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import cav.testforwl.database.DBConnect;
import cav.testforwl.utils.ConstantManager;

/**
 * Сервис для обработки полученной cms
 */

public class MySmsService extends Service {
    private static final String TAG = "MYSMS-SERVICE";

    public MySmsService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String sms_from = intent.getStringExtra(ConstantManager.SMS_FROM);
        String sms_body = intent.getStringExtra(ConstantManager.SMS_BODY);
        String sms_data = intent.getStringExtra(ConstantManager.SMS_DATE);
        new SaveBase(sms_from,sms_body,sms_data).execute();

        Log.d(TAG,"SMS SERVICE START");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"DESTROY");
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
