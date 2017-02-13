
package cav.testforwl.utils;


import android.content.SharedPreferences;
import android.os.Build;

public class PreferensManager {
    private SharedPreferences mSharedPreferences;


    public PreferensManager(){
        this.mSharedPreferences = TestWLApp.getSharedPreferences();
    }

    public void saveLockScreen(boolean val){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(ConstantManager.PREF_LOCK_SCREEN,val);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        }else{
            editor.commit();
        }
    }
    public void saveSmsChange(boolean val){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(ConstantManager.PREF_SMS_CHANGE,val);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        }else{
            editor.commit();
        }
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
    public void saveHtmlText(String val){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.PREF_HTML_TEXT,val);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        }else{
            editor.commit();
        }
    }
    public void saveHTMLUrl(String val){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(ConstantManager.PREF_URL,val);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        }else{
            editor.commit();
        }

    }
    public void saveRegistry(boolean val){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(ConstantManager.REGISTRY_BASE,val);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        }else{
            editor.commit();
        }
    }

    public void saveFlagStart(boolean val){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(ConstantManager.ONE_FIRST,val);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        }else{
            editor.commit();
        }
    }

    public String getMessage(){
        return mSharedPreferences.getString(ConstantManager.PREF_MESSAGE,"");
    }

    public Boolean isRegistry(){
        return mSharedPreferences.getBoolean(ConstantManager.REGISTRY_BASE,false);
    }

    public Boolean isChangeSMS(){
        return mSharedPreferences.getBoolean(ConstantManager.PREF_SMS_CHANGE,false);
    }


    public String getUrl() {
        return mSharedPreferences.getString(ConstantManager.PREF_URL,"");
    }

    public String getHTML() {
        return mSharedPreferences.getString(ConstantManager.PREF_HTML_TEXT,"");
    }
}
