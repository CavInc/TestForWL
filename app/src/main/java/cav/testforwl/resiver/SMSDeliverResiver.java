package cav.testforwl.resiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SMSDeliverResiver extends BroadcastReceiver {
    private static final String TAG = "SMSDELIVER";

    public SMSDeliverResiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // throw new UnsupportedOperationException("Not yet implemented");
        Log.d(TAG,"BROADCAST_SMS");
    }
}
