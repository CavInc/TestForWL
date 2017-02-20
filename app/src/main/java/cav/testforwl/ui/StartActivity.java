package cav.testforwl.ui;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import cav.testforwl.R;

public class StartActivity extends Activity {
    private static final String TAG = "STARTACTIVITY";
    private static final int PERMISSION_REQUEST_CODE = 1001;
    protected ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23 ) {
            int i = this.checkSelfPermission(Manifest.permission.RECEIVE_SMS);
            if (i == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "YES PERMISSION ");
            } else {
                Log.d(TAG, "NO PERMISSION");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_CONTACTS},PERMISSION_REQUEST_CODE);
            }
        }
        new waitInStart().execute();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // если получили права
            }
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    class waitInStart extends AsyncTask <Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finish();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
