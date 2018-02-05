package com.ufrpe.bccsurvivor.estudo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

public class TelaExibicaoResposta extends AppCompatActivity {
    private QuestoesBanco questoesBanco;
    private TextView titulo;
    private TextView subTitulo;
    private TextView id;
    private EditText resposta;
    private Button sim;
    private Button nao;
    private TextView admCont;
    private TextView gostouR;
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_exibicao_resposta);

        titulo = (TextView)findViewById(R.id.tituloExibicaoResposta);
        subTitulo = (TextView)findViewById(R.id.subTituloExicaoResposta);
        id = (TextView) findViewById(R.id.idTituloExicaoResposta);
        resposta = (EditText) findViewById(R.id.areaExibicaoResposta);
        sim = (Button) findViewById(R.id.simr);
        nao = (Button) findViewById(R.id.naor);
        admCont = (TextView) findViewById(R.id.admContPP);
        gostouR = (TextView) findViewById(R.id.gostouR);

        Intent i = getIntent();

        questoesBanco = (QuestoesBanco) i.getParcelableExtra("questao");

        titulo.setText("ASSUNTO:"+questoesBanco.getAssuntoQuestao());
        subTitulo.setText("USUÁRIO:"+questoesBanco.getAutorQuestao());
        id.setText("ID:"+questoesBanco.getIdQuestao().toString());
        resposta.setText(questoesBanco.getRespostaQuestao());

        if(QuestoesDisciplina.tipo.equals("adm")){
            gostouR.setVisibility(View.GONE);
            sim.setVisibility(View.GONE);
            nao.setVisibility(View.GONE);
            admCont.setVisibility(View.VISIBLE);
            GetAvaliacaoNUM getnum = new GetAvaliacaoNUM();
            getnum.execute();
        }else {
            gostouR.setVisibility(View.VISIBLE);
            sim.setVisibility(View.VISIBLE);
            nao.setVisibility(View.VISIBLE);
            admCont.setVisibility(View.GONE);
            GetAvaliacao getAvaliacao = new GetAvaliacao();
            getAvaliacao.execute();
        }
        View view = getCurrentFocus();
        if(view != null){
            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    class GetAvaliacaoNUM extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        private int avPositiva;
        private int avNegativa;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(TelaExibicaoResposta.this, getString(R.string.aguarde),
                    getString(R.string.baixando));
        }

        @Override
        protected Void doInBackground(Void... strings) {

            try {
                String url = "http://"+getString(R.string.ip)+":5000/bccsurvivor/avaliacao/quantidade?idQuestao="+ questoesBanco.getIdQuestao().toString() +"&tipoAvaliacao=0&valorAvaliacao=1";
                URL urlServidor = new URL(url);
                HttpURLConnection con = (HttpURLConnection) urlServidor.openConnection();
                con.setRequestMethod("GET");
                con.setDoInput(true);
                con.connect();
                InputStream is = con.getInputStream();
                JSONArray json = new JSONArray(converterStreamToString(is));
                is.close();
                avPositiva = json.length();

                url = "http://"+getString(R.string.ip)+":5000/bccsurvivor/avaliacao/quantidade?idQuestao="+ questoesBanco.getIdQuestao().toString() +"&tipoAvaliacao=0&valorAvaliacao=0";
                urlServidor = new URL(url);
                con = (HttpURLConnection) urlServidor.openConnection();
                con.setRequestMethod("GET");
                con.setDoInput(true);
                con.connect();
                is = con.getInputStream();
                json = new JSONArray(converterStreamToString(is));
                is.close();
                avNegativa = json.length();

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

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dialog.dismiss();

            admCont.setText("Avaliações\nPositivas: "+avPositiva+"\nNegativas: "+avNegativa);
        }
    }

    class InserirAvaliacao extends AsyncTask<Integer, Void, Integer> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(TelaExibicaoResposta.this, getString(R.string.aguarde),
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
                os.write(("{\"idUsuario\": " + QuestoesDisciplina.idUsuario + "," +
                        "\"idQuestao\": " + questoesBanco.getIdQuestao() + "," +
                        "\"valorAvaliacao\": " + integers[0].toString() + "," +
                        "\"tipoAvaliacao\": 0 }").getBytes());
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
                            Toast.makeText(TelaExibicaoResposta.this, "RESPOSTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                        }
                    });
                    nao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(TelaExibicaoResposta.this, "RESPOSTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                    Toast.makeText(TelaExibicaoResposta.this, ""+integer.intValue(), Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(TelaExibicaoResposta.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
    }

    class GetAvaliacao extends AsyncTask<Void, Void, Integer> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(TelaExibicaoResposta.this, getString(R.string.aguarde),
                    getString(R.string.baixando));
        }

        @Override
        protected Integer doInBackground(Void... strings) {

            try {
                String url = "http://"+getString(R.string.ip)+":5000/bccsurvivor/avaliacao/avaliacao?idQuestao="+ questoesBanco.getIdQuestao() +"&idUsuario="+QuestoesDisciplina.idUsuario+"&tipoAvaliacao=0";
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
                        Toast.makeText(TelaExibicaoResposta.this, "RESPOSTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                    }
                });
                nao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TelaExibicaoResposta.this, "RESPOSTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    public void setResposta(String resposta){
        this.resposta.setText(resposta);
    }
}
