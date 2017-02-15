package cav.testforwl.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import cav.testforwl.R;
import cav.testforwl.utils.ConstantManager;
import cav.testforwl.utils.DataManager;
import cav.testforwl.utils.SmsAdapter;
import cav.testforwl.utils.SmsData;
import cav.testforwl.ui.SmsDetailActivity;



public class SmsActivity extends Activity implements View.OnClickListener {
    private DataManager mDataManager;

    private ListView mListView;
    private SmsAdapter adapter;

    private ImageButton delBtn;
    private SmsData mItem = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sms_activity);
        mDataManager = DataManager.getInstance();

        mListView = (ListView) findViewById(R.id.listView);
        delBtn = (ImageButton) findViewById(R.id.delete_btn);
        delBtn.setOnClickListener(this);

        ArrayList<SmsData> record = mDataManager.getAllRecord();
        adapter = new SmsAdapter(this,R.layout.item_sms_list,record);
        adapter.setNotifyOnChange(true);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(mItemListener);
        mListView.setOnItemLongClickListener(mItemLongListener);

        if (!mDataManager.getPreferensManager().isRegistry()){
            Intent intent = new Intent(this,StartActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_btn:
                mSelView.setBackgroundResource(android.R.color.white);
                mDataManager.delRecord(mItem.getId());
                adapter.remove(mItem);
                delBtn.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                //delBtn.setVisibility(View.GONE);
                break;
        }

    }
    private boolean select_del = false;
    private View mSelView = null;

    @Override
    public void onBackPressed() {
        if (select_del) {
            delBtn.setVisibility(View.GONE);
            mItem = null;
            mSelView.setBackgroundResource(android.R.color.white);
            select_del = false;
        }else {
            super.onBackPressed();
        }
    }

    private void viewDetial(){
        Intent intent =new Intent (this,SmsDetailActivity.class);
        intent.putExtra(ConstantManager.SMS_FROM,mItem.getSms_from());
        intent.putExtra(ConstantManager.SMS_BODY,mItem.getSms_body());
        intent.putExtra(ConstantManager.SMS_DATE,mItem.getSms_data());
        startActivity(intent);
    }

    AdapterView.OnItemLongClickListener mItemLongListener = new AdapterView.OnItemLongClickListener(){

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
            delBtn.setVisibility(View.VISIBLE);
            view.setBackgroundResource(R.color.selected_item);
            mSelView = view;
            mItem = adapter.getItem(position);
            select_del = true;
            return true;
        }
    };

    AdapterView.OnItemClickListener mItemListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            mItem = adapter.getItem(position);
            viewDetial();
        }
    };

}
