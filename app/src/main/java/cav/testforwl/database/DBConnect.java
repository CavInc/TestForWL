package cav.testforwl.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public void insertSMS(){

    }

    public void readSMSAll(){

    }
    public void readSMSId(long id){

    }
    public void deleteSMS(long id){

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
                db.execSQL("");
            }

        }
    }
}
