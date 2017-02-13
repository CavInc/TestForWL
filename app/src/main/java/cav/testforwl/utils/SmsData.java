package cav.testforwl.utils;


public class SmsData {
    private int id;
    private String sms_from;
    private String sms_body;
    private String sms_data;

    public SmsData(int id,String sms_from,String sms_body,String sms_data){
        this.id = id;
        this.sms_from = sms_from;
        this.sms_body = sms_body;
        this.sms_data = sms_data;

    }

    public int getId() {
        return id;
    }

    public String getSms_from() {
        return sms_from;
    }

    public String getSms_body() {
        return sms_body;
    }

    public String getSms_data() {
        return sms_data;
    }
}
