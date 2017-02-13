package cav.testforwl.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBConnect {
    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "twl_sms_db.db3";

    private SQLiteDatabase db;
    private DBHelper mDBHelper;

    public DBConnect(Context context) {
        mDBHelper = new DBHelper(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void open(){
        db = mDBHelper.getWritableDatabase();
    }

    public void close(){
        if (db!=null) {
            db.close();
        }
    }

    public int insertSMS(String sms_addres,String sms_body,String sms_date){
        open();
        ContentValues newValue = new ContentValues();
        newValue.put("sms_address",sms_addres);
        newValue.put("sms_body",sms_body);
        newValue.put("sms_date",sms_date);
        long id = db.insert("sms",null,newValue);
        close();
        return (int) id;
    }

    public Cursor readSMSAll(){
        Cursor cursor = db.query("sms",
                new String []{"_id","sms_address","sms_body","sms_date"},null,null,null,null,"_id DESC");
        return cursor;
    }
    public void readSMSId(long id){
        //db.query("sms");

    }
    public void deleteSMS(long id){
        open();
        db.delete("sms","_id="+id,null);
        close();
    }


    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            updateDatabase(sqLiteDatabase,0,DATABASE_VERSION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            updateDatabase(sqLiteDatabase,oldVersion,newVersion);
        }

        private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
            if (oldVersion<1){
                Log.d("DATABASE","CREATE");
                db.execSQL("create table sms " +
                        "(_id integer not null primary key autoincrement," +
                        "sms_address text," +
                        "sms_body text," +
                        "sms_date text" +
                        "status integer default 0)"); // 0 - новый 1 - прочитан
            }

        }
    }
}
