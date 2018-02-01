package com.ufrpe.bccsurvivor;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    TextView t1;
    TextView t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        restController = new RestController();

        //começo da inicialização da(s) fonte(s)
        Typeface adobeFont = Typeface.createFromAsset(getAssets(), "fonts/adobe.ttf");

        t2 = (TextView) findViewById(R.id.textView2); //Título login pt2
        t2.setTypeface(adobeFont);

        t1 = (TextView) findViewById(R.id.textView);  //Título login pt1
        t1.setTypeface(adobeFont);
        //fim da inicialização da(s) fonte(s)


        Button btnCadastrar = (Button) findViewById(R.id.btn_entrarCadastro);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(i);
            }
        });

        Button btnLogin = (Button) findViewById(R.id.btnLogar);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editLogin = (EditText) findViewById(R.id.editLogin);
                EditText editSenha = (EditText) findViewById(R.id.editSenha);

                String dados[] = new String[2];
                dados[0] = editLogin.getText().toString();
                dados[1] = Encrypter.getInstance().md5(editSenha.getText().toString());
                Log.v("Senha", dados[1]);

                restController.execute(dados);

            }
        });
    }


    private class RestController extends AsyncTask<String[], Void, Player> {

        @Override
        protected Player doInBackground(String[]... params) {

            URL url = null;
            try {
                String dados[] = params[0];
                url = new URL("http://"+getString(R.string.ip)+":5000/user/login?login=" + dados[0] + "&senha=" + dados[1]);
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
            if (player != null) {
                Intent myintent = new Intent(LoginActivity.this, MainActivity.class);
                Log.v("ToMenu", player.getNickname());
                myintent.putExtra("player", player);
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
