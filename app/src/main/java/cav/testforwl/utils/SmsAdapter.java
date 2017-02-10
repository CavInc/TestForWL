package cav.testforwl.utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class SmsAdapter extends ArrayAdapter {
    private LayoutInflater mInflater;

    public SmsAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
    }


    class ViewHolder {
        public TextView resipientSMS;
        public TextView shortSMS;
        public TextView dataSMS;

    }
}
