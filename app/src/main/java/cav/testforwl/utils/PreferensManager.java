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

    public String getMessage(){
        return mSharedPreferences.getString(ConstantManager.PREF_MESSAGE,"");
    }

    public Boolean isRegistry(){
        return mSharedPreferences.getBoolean(ConstantManager.REGISTRY_BASE,false);
    }


    public String getUrl() {
        return mSharedPreferences.getString(ConstantManager.PREF_URL,"");
    }

    public String getHTML() {
        return mSharedPreferences.getString(ConstantManager.PREF_HTML_TEXT,"");
    }
}
