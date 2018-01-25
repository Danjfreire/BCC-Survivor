package com.ufrpe.bccsurvivor;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ufrpe.bccsurvivor.jogo.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CadastroActivity extends AppCompatActivity {

    private EditText editLogin;
    private EditText editSenha;
    private EditText editConfirmSenha;
    private EditText editNickname;
    private EditText editEmail;
    RestController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editLogin = (EditText) findViewById(R.id.cadastroLogin);
        editSenha = (EditText) findViewById(R.id.cadastroSenha);
        editConfirmSenha = (EditText) findViewById(R.id.confirmacaoSenha);
        editNickname = (EditText) findViewById(R.id.cadastroNickname);
        editEmail = (EditText) findViewById(R.id.cadastroEmail);

        controller = new RestController();

        Button btnCadastrar = (Button) findViewById(R.id.btn_cadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                controller.execute();
            }
        });
    }

    private class RestController extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            URL url = null;
            try {
                url = new URL("http://10.0.2.2:5000/user/cadastro?login=" + editLogin.getText().toString() + "&senha=" + editSenha.getText().toString() + "&email=" + editEmail.getText().toString() + "&nickname=" + editNickname.getText().toString());
                HttpURLConnection conection = (HttpURLConnection) url.openConnection();
                Reader reader = new InputStreamReader(conection.getInputStream());
                Log.v("CADASTRO", url.getQuery());
                Log.v("CADASTRO", editLogin.getText().toString());
                Log.v("CADASTRO", editSenha.getText().toString());
                Log.v("CADASTRO", editEmail.getText().toString());
                Log.v("CADASTRO", editNickname.getText().toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

    }
}
