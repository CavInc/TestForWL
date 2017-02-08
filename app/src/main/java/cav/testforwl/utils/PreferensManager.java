package cav.testforwl.utils;


import android.content.SharedPreferences;
import android.os.Build;

public class PreferensManager {
    private SharedPreferences mSharedPreferences;

    public PreferensManager(){
        this.mSharedPreferences = TestWLApp.getSharedPreferences();
    }

    public void saveFlag(){

    }
    public void saveMessage(String message){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.PREF_MESSAGE,message);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        }else{
            editor.commit();
        }

    }
    public void saveHtmlText(){

    }
    public void saveHTMLUrl(){

    }


}
