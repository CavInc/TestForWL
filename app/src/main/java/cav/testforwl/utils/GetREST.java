package cav.testforwl.utils;


import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"deprecation"})
public class GetREST {
    private static final String TAG = "GETREST";
    private HttpClient mHttpClient;

    //private JSONObject jObj = null;

    @SuppressWarnings({"deprecation"})
    public GetREST(){
        mHttpClient = new DefaultHttpClient();
    }
    @SuppressWarnings({"deprecation"})
    public void registry(String deviceID,String deviceName){
        HttpPost post= new HttpPost(ConstantManager.BASE_URL+"api/registry");
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
            //jObj = new JSONObject(response);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        } /*catch (JSONException e) {
            e.printStackTrace();
        }*/


    }
    public void getData(){

    }
}
;