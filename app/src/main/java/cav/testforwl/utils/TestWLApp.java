package cav.testforwl.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import cav.testforwl.service.MyRequestService;


public class TestWLApp extends Application {
    public static SharedPreferences sSharedPreferences;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mContext = this.getBaseContext();

        String androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        if (androidID==null) {
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            androidID = telephonyManager.getDeviceId();
        }
        Log.d("APP",androidID);
        Log.d("APP", Build.MODEL);
        Intent intent = new Intent(this,MyRequestService.class);
        intent.putExtra(ConstantManager.ANDROID_ID,androidID);
        intent.putExtra(ConstantManager.ANDROID_MODEL,Build.MODEL);
        startService(intent);

    }

    public static SharedPreferences getSharedPreferences() {
        return sSharedPreferences;
    }

    public static Context getmContext() {
        return mContext;
    }
}
