package com.ufrpe.bccsurvivor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ufrpe.bccsurvivor.estudo.*;

/**
 * Created by Raylison-WindowsN on 27/11/2017.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = (Button) findViewById(R.id.button6);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(LoginActivity.this, com.ufrpe.bccsurvivor.estudo.MainActivity.class);
                startActivity(myintent);
            }
        });
    }
}
