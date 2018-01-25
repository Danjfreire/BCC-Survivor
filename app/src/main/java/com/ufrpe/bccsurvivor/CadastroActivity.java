package com.ufrpe.bccsurvivor;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

                if (checarCampos()) {
                    if(editSenha.getText().toString().equals(editConfirmSenha.getText().toString()))
                        controller.execute();
                    else
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.falhaCadastroSenha), Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.camposIncompletos), Toast.LENGTH_LONG).show();
            }
        });
    }

    private class RestController extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            URL url = null;
            try {
                checarCampos();
                url = new URL("http://"+getString(R.string.ip)+":5000/user/cadastro?login=" + editLogin.getText().toString() +
                        "&senha=" + Encrypter.getInstance().md5(editSenha.getText().toString()) +
                        "&email=" + editEmail.getText().toString() + "&nickname=" + editNickname.getText().toString());
                HttpURLConnection conection = (HttpURLConnection) url.openConnection();
                Reader reader = new InputStreamReader(conection.getInputStream());
                Gson gson = new GsonBuilder().create();
                String resultado = gson.fromJson(reader,String.class);
                Log.v("CADASTRO", url.getQuery());
                Log.v("CADASTRO", editLogin.getText().toString());
                Log.v("CADASTRO", editSenha.getText().toString());
                Log.v("CADASTRO", editEmail.getText().toString());
                Log.v("CADASTRO", editNickname.getText().toString());

                return resultado;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            concluirCadastro(s);
        }
    }

    private void concluirCadastro(String s) {
        if (s.equals("Saved")){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.sucessoCadastro), Toast.LENGTH_LONG).show();
            Intent i = new Intent(CadastroActivity.this, LoginActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.falhaCadastro), Toast.LENGTH_LONG).show();
            controller = new RestController();
        }


    }

    private boolean checarCampos() {
        if (editLogin.getText().toString().equals(""))
            return false;
        else if (editSenha.getText().toString().equals(""))
            return false;
        else if (editConfirmSenha.toString().equals(""))
            return false;
        else if (editEmail.getText().toString().equals(""))
            return false;
        else if (editNickname.getText().toString().equals(""))
            return false;
        else
            return true;

    }
}
