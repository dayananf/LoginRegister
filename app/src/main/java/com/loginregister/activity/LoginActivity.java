package com.loginregister.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loginregister.R;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private TextView txt_forgot_password;
    private LinearLayout ll_sign_up;
    private Button btn_login;
    String userName, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        txt_forgot_password = (TextView) findViewById(R.id.txt_forgot_password);
        ll_sign_up = (LinearLayout) findViewById(R.id.ll_sign_up);
        btn_login = (Button) findViewById(R.id.btn_login);


        btn_login.setOnClickListener(LoginActivity.this);
        ll_sign_up.setOnClickListener(LoginActivity.this);
        txt_forgot_password.setOnClickListener(LoginActivity.this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_forgot_password:
                break;

            case R.id.ll_sign_up:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_login:
                validateForm();
                break;

        }

    }

    private void validateForm() {
        userName = mEmailView.getText().toString().trim();
        userPassword = mPasswordView.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(LoginActivity.this, "Enter User Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(LoginActivity.this, "Enter User Password", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
        }
    }
}

