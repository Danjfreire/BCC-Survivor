package com.ufrpe.bccsurvivor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ufrpe.bccsurvivor.jogo.Player;
import com.ufrpe.bccsurvivor.jogo.Usuario;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Raylison-WindowsN on 27/11/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private RestController restController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        restController = new RestController();

        Button btnLogin = (Button) findViewById(R.id.btnLogar);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editLogin = (EditText) findViewById(R.id.editLogin);
                EditText editSenha = (EditText) findViewById(R.id.editSenha);

                String dados[] = new String[2];
                dados[0] = editLogin.getText().toString();
                dados[1] = md5(editSenha.getText().toString());
                Log.v("Senha", dados[1]);

                restController.execute(dados);


            }
        });
    }

    private String md5(String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";

    }

    private class RestController extends AsyncTask<String[], Void, Player> {

        @Override
        protected Player doInBackground(String[]... params) {

            URL url = null;
            try {
                String dados[] = params[0];
                url = new URL("http://10.0.2.2:5000/user/login?login=" + dados[0] + "&senha=" + dados[1]);
                HttpURLConnection conection = (HttpURLConnection) url.openConnection();
                Reader reader = new InputStreamReader(conection.getInputStream());
                Gson gson = new GsonBuilder().create();
                Player player = gson.fromJson(reader, Player.class);
                //Log.v("PLAYERNAME",player.getNickname());
                return player;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Player player) {
            if (player.getNickname() != null) {
                Intent myintent = new Intent(LoginActivity.this, MainActivity.class);
                Log.v("ToMenu",player.getNickname());
                myintent.putExtra("player",player);
                startActivity(myintent);
            } else {
                Log.v("Tag", "Entrou");
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginFailure), Toast.LENGTH_LONG).show();
                failedLogin();
            }
        }
    }

    private void failedLogin() {
        this.restController = new RestController();

    }
}
