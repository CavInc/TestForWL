package cav.testforwl.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cav.testforwl.R;
import cav.testforwl.utils.DataManager;
import cav.testforwl.utils.SmsAdapter;
import cav.testforwl.utils.SmsData;


public class SmsActivity extends Activity implements View.OnClickListener {
    private DataManager mDataManager;

    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_activity);
        mDataManager = DataManager.getInstance();

        mListView = (ListView) findViewById(R.id.listView);

        ArrayList<SmsData> record = mDataManager.getAllRecord();
        SmsAdapter adapter = new SmsAdapter(this,R.layout.item_sms_list,record);
        adapter.setNotifyOnChange(true);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(mItemListener);
        mListView.setOnItemLongClickListener(mItemLongListener);

    }

    @Override
    public void onClick(View view) {

    }

    AdapterView.OnItemLongClickListener mItemLongListener = new AdapterView.OnItemLongClickListener(){

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            return false;
        }
    };

    AdapterView.OnItemClickListener mItemListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        }
    };

}
