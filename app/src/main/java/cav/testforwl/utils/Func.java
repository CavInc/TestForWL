package cav.testforwl.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Func {
    private static final String TAG = "FUNC";

    public static String getHumanDate(String rawStringDate){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date ldate =new Date();
        Date sms_date;
        try {
            sms_date = format.parse(rawStringDate);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(TAG,e.getLocalizedMessage(),e);
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(ldate);
        Calendar c1 = Calendar.getInstance();
        c1.setTime(sms_date);
        if (c.get(Calendar.DAY_OF_MONTH)==c1.get(Calendar.DAY_OF_MONTH)){
            DateFormat frm = new SimpleDateFormat("HH:mm");
            return frm.format(sms_date);
        } else {
            // показать дату в виде ДЕНЬ МЕСЯЦ
            DateFormat frm = new SimpleDateFormat("dd MMM");
            return  frm.format(sms_date);
        }


    }
}
