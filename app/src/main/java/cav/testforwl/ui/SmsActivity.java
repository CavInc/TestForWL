package cav.testforwl.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import cav.testforwl.R;


public class SmsActivity extends Activity {

    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_activity);

        mListView = (ListView) findViewById(R.id.listView);

    }
}
