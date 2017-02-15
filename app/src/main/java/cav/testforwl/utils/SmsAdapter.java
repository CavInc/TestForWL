package cav.testforwl.utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cav.testforwl.R;


public class SmsAdapter extends ArrayAdapter<SmsData>{
    private LayoutInflater mInflater;
    private int resLayout;

    public SmsAdapter(Context context, int resource, ArrayList<SmsData> objects) {
        super(context, resource, objects);
        resLayout = resource;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View row=convertView;
        if(row==null){
            row = mInflater.inflate(resLayout,parent,false);
            holder = new ViewHolder();
            holder.resipientSMS = (TextView) row.findViewById(R.id.recipient_msm);
            holder.shortSMS = (TextView) row.findViewById(R.id.short_message);
            holder.dataSMS = (TextView) row.findViewById(R.id.date_message);
            row.setTag(holder);
        }else {
            holder = (ViewHolder)row.getTag();
        }

        SmsData data = getItem(position);
        holder.resipientSMS.setText(data.getSms_from());
        holder.shortSMS.setText(data.getSms_body());
        holder.dataSMS.setText(Func.getHumanDate(data.getSms_data()));
        return row;
        //return super.getView(position, convertView, parent);
    }

    class ViewHolder {
        public TextView resipientSMS;
        public TextView shortSMS;
        public TextView dataSMS;

    }
}
