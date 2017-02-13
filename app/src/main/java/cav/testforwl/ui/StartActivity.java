package cav.testforwl.ui;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import cav.testforwl.R;

public class StartActivity extends Activity {
    private static final String TAG = "STARTACTIVITY";
    protected ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new waitInStart().execute();
        // проверку активностей
        //PackageManager.PERMISSION_GRANTED
        Log.d(TAG,Manifest.permission.RECEIVE_SMS);
        //int i = this.checkSelfPermission(Manifest.permission.RECEIVE_SMS);
       // System.out.println(this.checkSelfPermission(android.Manifest.permission.RECIVE_SMS));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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
