package cav.testforwl.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import cav.testforwl.R;
import cav.testforwl.utils.ConstantManager;
import cav.testforwl.utils.Func;

public class SmsDetailActivity extends Activity {

    private TextView smsFrom;
    private TextView smsBody;
    private TextView smsDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_detail);

        smsFrom = (TextView) findViewById(R.id.sms_from_tv);
        smsBody = (TextView) findViewById(R.id.sms_body_tv);
        smsDate = (TextView) findViewById(R.id.sms_date_tv);

        smsFrom.setText(getIntent().getStringExtra(ConstantManager.SMS_FROM));
        smsBody.setText(getIntent().getStringExtra(ConstantManager.SMS_BODY));
        smsDate.setText(getString(R.string.sms_detail_recived)+Func.getHumanDate(getIntent().
                getStringExtra(ConstantManager.SMS_DATE)));

    }
}
