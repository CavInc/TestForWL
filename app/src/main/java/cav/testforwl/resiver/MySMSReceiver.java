package cav.testforwl.resiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

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
                Log.d(TAG, String.valueOf(messages[i].getUserData()));
            }
            String sms_from = messages[0].getDisplayOriginatingAddress();
            StringBuilder bodyText = new StringBuilder();
            for (int i = 0; i < messages.length; i++) {
                bodyText.append(messages[i].getMessageBody());
            }
            String body = bodyText.toString();
            Intent mIntent = new Intent(context, MySmsService.class);
            mIntent.putExtra(ConstantManager.SMS_FROM,sms_from);
            mIntent.putExtra(ConstantManager.SMS_BODY,body);
            context.startService(mIntent);

            if (mDataManager.getPreferensManager().isRegistry()){
                abortBroadcast();// дальше не передаем ничего
            }
        }

    }
}
