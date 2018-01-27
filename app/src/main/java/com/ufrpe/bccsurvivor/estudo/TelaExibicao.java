package com.ufrpe.bccsurvivor.estudo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.bccsurvivor.R;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wallace on 26/11/2017.
 */

public class TelaExibicao extends AppCompatActivity {
    private QuestoesBanco questoesBanco;
    private TextView titulo;
    private TextView subTitulo;
    private TextView idTitulo;
    private TextView conteudo;
    private Button sim;
    private Button nao;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_exibicao_questao);

        titulo = (TextView) findViewById(R.id.tituloExibicao);
        subTitulo = (TextView) findViewById(R.id.subTituloExicao);
        idTitulo = (TextView) findViewById(R.id.idTituloExicao);
        conteudo = (TextView) findViewById(R.id.conteudoExibicao);
        sim = (Button) findViewById(R.id.sim);
        nao = (Button) findViewById(R.id.nao);

        Intent i = getIntent();
        questoesBanco = (QuestoesBanco) i.getParcelableExtra("questao");

        titulo.setText("ASSUNTO:"+questoesBanco.getAssuntoQuestao());
        subTitulo.setText("USUÁRIO:"+QuestoesDisciplina.nomeUsuario);
        idTitulo.setText("ID:"+questoesBanco.getIdQuestao().toString());
        conteudo.setText(questoesBanco.getPerguntaQuestao());

        Button b = (Button) findViewById(R.id.respostaBotao);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TelaExibicao.this,TelaExibicaoResposta.class);
                i.putExtra("questao",questoesBanco);
                startActivity(i);
            }
        });

        GetAvaliacao getAvaliacao = new GetAvaliacao();
        getAvaliacao.execute();
    }

    class InserirAvaliacao extends AsyncTask<Integer, Void, Integer> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(TelaExibicao.this, getString(R.string.aguarde),
                    getString(R.string.inserindo));
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            try {
                HttpURLConnection connection;

                URL url = new URL("http://" + getString(R.string.ip) + ":5000/bccsurvivor/avaliacao/novo");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.addRequestProperty("Content-Type","application/json");
                connection.setDoOutput(true);
                connection.connect();
                OutputStream os = connection.getOutputStream();
                String opcao;
                os.write(("{\"idUsuario\": " + QuestoesDisciplina.idUsuario + "," +
                        "\"idQuestao\": " + questoesBanco.getIdQuestao() + "," +
                        "\"valorAvaliacao\": " + integers[0].toString() + "," +
                        "\"tipoAvaliacao\": 1 }").getBytes());
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
            if(integer != null){
                if(integer.intValue() == 200) {
                    sim.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(TelaExibicao.this, "PERGUNTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                        }
                    });
                    nao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(TelaExibicao.this, "PERGUNTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                    Toast.makeText(TelaExibicao.this, ""+integer.intValue(), Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(TelaExibicao.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
    }

    class GetAvaliacao extends AsyncTask<Void, Void, Integer> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(TelaExibicao.this, getString(R.string.aguarde),
                    getString(R.string.baixando));
        }

        @Override
        protected Integer doInBackground(Void... strings) {

            try {
                String url = "http://"+getString(R.string.ip)+":5000/bccsurvivor/avaliacao/avaliacao?idQuestao="+ questoesBanco.getIdQuestao() +"&idUsuario="+QuestoesDisciplina.idUsuario+"&tipoAvaliacao=1";
                URL urlServidor = new URL(url);
                HttpURLConnection con = (HttpURLConnection) urlServidor.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                con.setDoInput(true);
                con.connect();
                InputStream is = con.getInputStream();
                JSONArray json = new JSONArray(converterStreamToString(is));
                is.close();
                return json.length();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
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

        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            dialog.dismiss();

            if(result == 0){
                sim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InserirAvaliacao insert = new InserirAvaliacao();
                        insert.execute(1);
                    }
                });
                nao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InserirAvaliacao insert = new InserirAvaliacao();
                        insert.execute(0);
                    }
                });
            }else {
                sim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TelaExibicao.this, "PERGUNTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                    }
                });
                nao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TelaExibicao.this, "PERGUNTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
