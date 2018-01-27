package com.ufrpe.bccsurvivor.estudo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.bccsurvivor.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by wallace on 25/11/2017.
 */

public class CriarPergunta extends AppCompatActivity implements CaixaTextoQuestao.EditNameDialogListener {
    private EditText ed;
    private TextView tvPergunta;
    private TextView tvResposta;
    private TextView tv;
    private Button salvar;

    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putString("assunto", String.valueOf(ed.getText()));
        bundle.putString("pergunta", String.valueOf(tvPergunta.getText()));
        bundle.putString("resposta", String.valueOf(tvResposta.getText()));
    }

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criar_questao);

        ed = (EditText) findViewById(R.id.editAssunto);
        tvPergunta = (TextView) findViewById(R.id.pergunta);
        tvResposta = (TextView) findViewById(R.id.resposta);
        salvar = (Button) findViewById(R.id.salvar);
        tv = (TextView) findViewById(R.id.textDisciplina);

        if(savedInstanceState != null){
            ed.setText(savedInstanceState.getString("assunto"));
            tvPergunta.setText(savedInstanceState.getString("pergunta"));
            tvResposta.setText(savedInstanceState.getString("resposta"));
        }

        Intent i = getIntent();
        tv.setText(i.getExtras().getString("disciplina"));

        tvPergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                CaixaTextoQuestao c = CaixaTextoQuestao.newInstace(getString(R.string.pergunta),R.id.pergunta,(String)tvPergunta.getText());
                c.show(fm,"pergunta");
            }
        });

        tvResposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                CaixaTextoQuestao c = CaixaTextoQuestao.newInstace(getString(R.string.resposta),R.id.resposta,(String)tvResposta.getText());
                c.show(fm,"resposta");
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed.getText().length() > 0 && tvResposta.getText().length() > 0 && tvPergunta.getText().length() > 0){
                    InserirPergunta inserirPergunta = new InserirPergunta();
                    inserirPergunta.execute();
                }else
                    Toast.makeText(CriarPergunta.this, "Preencha os campos vazios", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class InserirPergunta extends AsyncTask<Void, Void, Integer> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(CriarPergunta.this, getString(R.string.aguarde),
                    getString(R.string.inserindo));
        }

        @Override
        protected Integer doInBackground(Void... integers) {
            try {
                HttpURLConnection connection;

                URL url = new URL("http://" + getString(R.string.ip) + ":5000/bccsurvivor/banco/novo");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.addRequestProperty("Content-Type","application/json");
                connection.setDoOutput(true);
                connection.connect();
                OutputStream os = connection.getOutputStream();
                String opcao;
                os.write(("{\"perguntaQuestao\": \"" + tvPergunta.getText() + "\"," +
                        "\"respostaQuestao\": \"" + tvResposta.getText() + "\"," +
                        "\"disciplinaQuestao\": \"" + tv.getText() + "\"," +
                        "\"assuntoQuestao\": \"" + ed.getText().toString() + "\"," +
                        "\"autorQuestao\": "+ QuestoesDisciplina.idUsuario +" }").getBytes());
                return connection.getResponseCode();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dialog.cancel();
            if(integer == 200){
                Toast.makeText(CriarPergunta.this, "Questão salva", Toast.LENGTH_SHORT).show();
                GetQuestoes getQuestoes = new GetQuestoes();
                getQuestoes.execute(tv.getText().toString());
            }else
                Toast.makeText(CriarPergunta.this, "ERRO", Toast.LENGTH_SHORT).show();
        }
    }

    class GetQuestoes extends AsyncTask<String, Void, ArrayList<QuestoesBanco>> {

        ProgressDialog dialog;
        String disciplina;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(CriarPergunta.this, getString(R.string.aguarde),
                    getString(R.string.baixando));
        }

        @Override
        protected ArrayList<QuestoesBanco> doInBackground(String... strings) {
            disciplina = strings[0];
            ArrayList<QuestoesBanco> questoesBancos;
            try {
                String url = "http://"+getString(R.string.ip)+":5000/bccsurvivor/banco/disciplina_questao/" + URLEncoder.encode(disciplina,"UTF-8");
                URL urlServidor = new URL(url);
                HttpURLConnection con = (HttpURLConnection) urlServidor.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                con.setDoInput(true);
                con.connect();
                InputStream is = con.getInputStream();
                JSONArray json = new JSONArray(converterStreamToString(is));
                is.close();
                JSONObject objeto;
                questoesBancos = new ArrayList<QuestoesBanco>();
                for(int i = 0; i < json.length(); i++){
                    objeto = json.getJSONObject(i);
                    questoesBancos.add(new QuestoesBanco(
                            objeto.getInt("idQuestao"),
                            objeto.getString("perguntaQuestao"),
                            objeto.getString("respostaQuestao"),
                            objeto.getString("disciplinaQuestao"),
                            objeto.getString("assuntoQuestao"),
                            objeto.getInt("autorQuestao")));
                }
                return questoesBancos;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new ArrayList<>();
        }

        private String converterStreamToString(final InputStream input) throws IOException {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            final StringBuilder sBuf = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                sBuf.append(line);
            }
            input.close();
            return  sBuf.toString();
        }

        protected void onPostExecute(ArrayList<QuestoesBanco> result) {
            super.onPostExecute(result);
            dialog.dismiss();

            Intent myIntent = new Intent(CriarPergunta.this, MostrarQuestoes.class);
            myIntent.putParcelableArrayListExtra("questoes", result);
            myIntent.putExtra("titulo", disciplina);
            startActivity(myIntent);
        }
    }

    public void receberConteudo(String texto, int id){
        TextView tv = (TextView) findViewById(id);
        tv.setText(texto);
    }
}