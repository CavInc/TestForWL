package cav.testforwl.utils;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import cav.testforwl.database.DBConnect;

public class DataManager {
    private static DataManager INSTANCE = null;
    private PreferensManager mPreferensManager;
    private Context mContext;

    public DataManager() {
        this.mPreferensManager = new PreferensManager();
        this.mContext = TestWLApp.getContext();
    }

    public static DataManager getInstance() {
        if (INSTANCE==null){
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public PreferensManager getPreferensManager() {
        return mPreferensManager;
    }

    public ArrayList<SmsData> getAllRecord(){
        ArrayList<SmsData> record = new ArrayList<>();
        DBConnect db = new DBConnect(mContext);
        db.open();

        Cursor cursor = db.readSMSAll();
        while (cursor.moveToNext()){
            record.add(new SmsData(cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("sms_address")),
                    cursor.getString(cursor.getColumnIndex("sms_body")),
                    cursor.getString(cursor.getColumnIndex("sms_date"))));

        }
        db.close();
        return record;
    }

}
