package com.loginregister.activity;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.loginregister.R;
import com.loginregister.constant.InterfaceConsts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edt_first_name, edt_last_name, edt_email, edt_mobile;
    private Button btn_register;
    private CheckBox chk_terms;
    boolean chk_change = false;
    String firstName, lastName, email, mobile, possibleEmail;
    private static final int PERMISSIONS_REQUEST = 132;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edt_first_name = (EditText) findViewById(R.id.edt_first_name);
        edt_last_name = (EditText) findViewById(R.id.edt_last_name);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_mobile = (EditText) findViewById(R.id.edt_mobile);
        btn_register = (Button) findViewById(R.id.btn_register);
        chk_terms = (CheckBox) findViewById(R.id.chk_terms);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Check Mobile Version Code  of MarshMallow to get Permission From User
            getPermission();
        } else {
            // Get Email id From Device automatically
            setDeviceEmail();
        }

        btn_register.setOnClickListener(RegisterActivity.this);
        chk_terms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
                if (isCheck) {
                    chk_change = true;
                } else {
                    chk_change = false;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                validateForm();
                break;
            default:
                break;
        }
    }

    //******   Get Email of Device     ****//
    private void setDeviceEmail() {
        try {
            // GET Email Id
            Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
            possibleEmail = accounts[0].name; // get account
            String divEmail = edt_email.getText().toString().trim();
            if (divEmail.equals("") || divEmail.equals("null")) {
                edt_email.setText(possibleEmail); // set account
                Log.e("Device Email", possibleEmail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validateForm() {
        firstName = edt_first_name.getText().toString().trim();
        lastName = edt_last_name.getText().toString().trim();
        email = edt_email.getText().toString().trim();
        mobile = edt_mobile.getText().toString().trim();

        // Pattern match for email id
        Pattern pattern = Pattern.compile(InterfaceConsts.EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(RegisterActivity.this, "Enter First Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(RegisterActivity.this, "Enter Last Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
        } else if (!matcher.find()) {
            Toast.makeText(RegisterActivity.this, "Invalid Email ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(mobile)) {
            Toast.makeText(RegisterActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
        } else if (mobile.length() != 10) {
            Toast.makeText(RegisterActivity.this, "Enter 10 Digit Mobile Number", Toast.LENGTH_SHORT).show();
        } else if (chk_change == false) {
            Toast.makeText(RegisterActivity.this, "Check Terms and Conditions", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, OTPActivity.class);
            intent.putExtra("MOBILE_NUMBER", mobile);
            startActivity(intent);
        }
    }


    //*******Get Permission of MarshMallow from User **********//
    public void getPermission() {
        try {
            int readAccountFlag = 0;
            int permissionCount = 0;
            int readSMSFlag = 0;

            int permissionCountNew = 0;
            int flag = 0;

            /** check permission is GRANTED or Not in Marshmallow */
            int readAccountPer = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);
            int readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

            if (readAccountPer != PackageManager.PERMISSION_GRANTED) {
                readAccountFlag = 1;
                permissionCount += 1;
                flag = 1;
            }

            if (readSMS != PackageManager.PERMISSION_GRANTED) {
                readSMSFlag = 1;
                permissionCount += 1;
                flag = 1;
            }

            String[] permissionCArr = new String[permissionCount];
            if (readAccountFlag == 1) {
                permissionCArr[permissionCountNew] = Manifest.permission.GET_ACCOUNTS;
                permissionCountNew += 1;
            } else {
                // Get Email id From Device automatically
                setDeviceEmail();
            }

            if (readSMSFlag == 1) {
                permissionCArr[permissionCountNew] = Manifest.permission.READ_SMS;
                permissionCountNew += 1;
            }
            if (flag == 1) {
                ActivityCompat.requestPermissions(
                        this,
                        permissionCArr,
                        PERMISSIONS_REQUEST
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            if (requestCode == PERMISSIONS_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Get Email id From Device automatically
                setDeviceEmail();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
