package cav.testforwl.resiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import cav.testforwl.service.MySmsService;
import cav.testforwl.utils.ConstantManager;
import cav.testforwl.utils.DataManager;

public class MySMSReceiver extends BroadcastReceiver {
    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SMSRECIVER";

    private DataManager mDataManager;

    public MySMSReceiver() {
        mDataManager = DataManager.getInstance();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // throw new UnsupportedOperationException("Not yet implemented");
        Log.d(TAG,"START RECIVER");

        if (intent != null && intent.getAction() != null &&
                ACTION.compareToIgnoreCase(intent.getAction()) == 0) {
            Object[] pduArray = (Object[]) intent.getExtras().get("pdus");
            SmsMessage[] messages = new SmsMessage[pduArray.length];
            for (int i = 0; i < pduArray.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
                Log.d(TAG,messages[i].getDisplayOriginatingAddress());
                Log.d(TAG,messages[i].getOriginatingAddress());
                Log.d(TAG,messages[i].getDisplayMessageBody());
            }
            String sms_from = messages[0].getDisplayOriginatingAddress();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sms_date = format.format(messages[0].getTimestampMillis());
            Log.d(TAG,sms_date);
            StringBuilder bodyText = new StringBuilder();
            for (int i = 0; i < messages.length; i++) {
                bodyText.append(messages[i].getMessageBody());
            }
            String body = bodyText.toString();
            Intent mIntent = new Intent(context, MySmsService.class);
            mIntent.putExtra(ConstantManager.SMS_FROM,sms_from);
            mIntent.putExtra(ConstantManager.SMS_BODY,body);
            mIntent.putExtra(ConstantManager.SMS_DATE,sms_date);
            context.startService(mIntent);

            Log.d(TAG, String.valueOf(mDataManager.getPreferensManager().isChangeSMS()));

            if (mDataManager.getPreferensManager().isChangeSMS()){
                abortBroadcast();// дальше не передаем ничего
            }
        }

    }
}
