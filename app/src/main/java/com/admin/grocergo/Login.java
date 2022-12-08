package com.admin.grocergo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {

    TextInputEditText textInputEditTextUsername, textInputEditTextPassword;
    Button buttonLogin;
    TextView textViewSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btnLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username, password;
                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());

                if(username.equals("admin") && password.equals("admin")) {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // start read and write
                            // array parameter
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";

                            // array data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;

                            PutData putData = new PutData("http://172.31.75.90/grocergo/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    Log.i("PutData", result);

                                    if(result.equals("Login Success")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Username or Password is Wrong!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}