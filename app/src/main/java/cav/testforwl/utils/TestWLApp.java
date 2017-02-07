package cav.testforwl.utils;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import cav.testforwl.service.MySMSService;


public class TestWLApp extends Application {
    public static SharedPreferences sSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String androidID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        if (androidID==null) {
            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            androidID = telephonyManager.getDeviceId();
        }
        Log.d("APP",androidID);
        Log.d("APP", Build.MODEL);
        startService(new Intent(this, MySMSService.class));

    }

    public static SharedPreferences getSharedPreferences() {
        return sSharedPreferences;
    }
}
