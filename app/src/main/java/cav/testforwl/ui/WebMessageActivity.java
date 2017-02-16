package cav.testforwl.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

import cav.testforwl.R;
import cav.testforwl.utils.DataManager;


public class WebMessageActivity extends Activity {

    private static final String TAG = "WEB START";
    private DataManager mDataManager;

    private WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webmessage_activity);
        mDataManager=DataManager.getInstance();
        if (mDataManager.getPreferensManager().isLockScreen()) {
            Log.d(TAG,"LOCK SCREEN");
        }

        getWindow().setFlags(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);


        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            mWebView.getSettings().setDisplayZoomControls(false);
        }
        mWebView.setWebViewClient(new WebViewClient(

        ));
        mWebView.setWebChromeClient(new WebChromeClient(

        ));

        mWebView.getSettings().setDefaultTextEncodingName("utf-8");


    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
        ActivityManager am = (ActivityManager) this
                .getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> rs = am.getRunningServices(50);

        for (int i = 0; i < rs.size(); i++) {
            ActivityManager.RunningServiceInfo rsi = rs.get(i);
            Log.i("Service", "Process " + rsi.process + " with component "
                    + rsi.service.getClassName());
        }
        */
    }

    @Override
    public void onBackPressed() {
        if (!mDataManager.getPreferensManager().isLockScreen())
            super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"WEB START");
        String html_url=mDataManager.getPreferensManager().getUrl();
        String html_body = mDataManager.getPreferensManager().getHTML();
        if (html_url.length()!=0) {
            viewUrl(html_url);
        } else {
            viewText(html_body);
        }


    }

    private void viewUrl(final String html_url) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            mWebView.loadUrl(html_url);

        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl(html_url);
                }
            });
        }
    }
    private void viewText(final String htmlMess){
        //mWebView.loadData(htmlMess,"text/html; charset=UTF-8",null);
        mWebView.loadDataWithBaseURL(null, htmlMess, "text/html", "utf-8", null);
    }
}
