package com.loginregister.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;
import com.loginregister.R;

public class OTPActivity extends AppCompatActivity implements View.OnClickListener {
    Pinview mOtpView;
    TextView txt_phone_number, txt_resend_otp;
    Button btn_verify;
    String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        txt_resend_otp = (TextView) findViewById(R.id.txt_resend_otp);
        txt_phone_number = (TextView) findViewById(R.id.txt_phone_number);
        btn_verify = (Button) findViewById(R.id.btn_verify);

        mOtpView = (Pinview) findViewById(R.id.pinview);
//        mOtpView.setValue("5678");

        mobileNumber = getIntent().getExtras().getString("MOBILE_NUMBER");
        txt_phone_number.setText(mobileNumber);


        btn_verify.setOnClickListener(OTPActivity.this);
        txt_resend_otp.setOnClickListener(OTPActivity.this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.txt_resend_otp:
                break;
            case R.id.btn_verify:
                validateOtp();
                break;

            default:
                break;
        }
    }

    private void validateOtp() {
        String otp = mOtpView.getValue();
        // mOtpView.setValue(otp);
        if (TextUtils.isEmpty(otp)) {
            Toast.makeText(this, "Enter Otp", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Otp is " + otp, Toast.LENGTH_SHORT).show();
        }
    }
}
