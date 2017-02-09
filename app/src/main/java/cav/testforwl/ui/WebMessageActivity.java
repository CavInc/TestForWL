package cav.testforwl.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import cav.testforwl.R;


public class WebMessageActivity extends Activity {

    private static final String TAG = "WEB START";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webmessage_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"Web START ACTIVITY");

        ActivityManager am = (ActivityManager) this
                .getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> rs = am.getRunningServices(50);

        for (int i = 0; i < rs.size(); i++) {
            ActivityManager.RunningServiceInfo rsi = rs.get(i);
            Log.i("Service", "Process " + rsi.process + " with component "
                    + rsi.service.getClassName());
        }
    }
}
