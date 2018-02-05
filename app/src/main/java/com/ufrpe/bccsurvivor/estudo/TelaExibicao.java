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
import android.widget.ImageView;
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
 * Created by wallace on 26/11/2017.
 */

public class TelaExibicao extends AppCompatActivity {
    private QuestoesBanco questoesBanco;
    private TextView titulo;
    private TextView subTitulo;
    private TextView idTitulo;
    private EditText conteudo;
    private TextView gostouP;
    private TextView admCont;
    private ImageView admApagar;
    private Button sim;
    private Button nao;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_exibicao_questao);

        titulo = (TextView) findViewById(R.id.tituloExibicao);
        subTitulo = (TextView) findViewById(R.id.subTituloExicao);
        idTitulo = (TextView) findViewById(R.id.idTituloExicao);
        conteudo = (EditText) findViewById(R.id.conteudoExibicao);
        sim = (Button) findViewById(R.id.sim);
        nao = (Button) findViewById(R.id.nao);
        gostouP = (TextView) findViewById(R.id.gostouP);
        admCont = (TextView) findViewById(R.id.admCont);
        admApagar = (ImageView) findViewById(R.id.admApagar);

        Intent i = getIntent();
        questoesBanco = (QuestoesBanco) i.getParcelableExtra("questao");

        titulo.setText("ASSUNTO:" + questoesBanco.getAssuntoQuestao());
        subTitulo.setText("USUÁRIO:" + questoesBanco.getAutorQuestao());
        idTitulo.setText("ID:" + questoesBanco.getIdQuestao().toString());
        conteudo.setText(questoesBanco.getPerguntaQuestao());

        Button b = (Button) findViewById(R.id.respostaBotao);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!questoesBanco.getPerguntaQuestao().equals(conteudo.getText().toString())) {
                    DialogAtualizar d = DialogAtualizar.novaInstancia(questoesBanco, "pergunta", conteudo.getText().toString());
                    d.show(getSupportFragmentManager(), "dialog");
                }else {
                    Intent i = new Intent(TelaExibicao.this, TelaExibicaoResposta.class);
                    i.putExtra("questao", questoesBanco);
                    startActivity(i);
                }
            }
        });

        admApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeletarQuestao de = new DeletarQuestao();
                de.execute();
            }
        });

        if (QuestoesDisciplina.tipo.equals("adm")) {
            gostouP.setVisibility(View.GONE);
            sim.setVisibility(View.GONE);
            nao.setVisibility(View.GONE);
            admCont.setVisibility(View.VISIBLE);
            GetAvaliacaoNUM getnum = new GetAvaliacaoNUM();
            getnum.execute();
        } else {
            gostouP.setVisibility(View.VISIBLE);
            sim.setVisibility(View.VISIBLE);
            nao.setVisibility(View.VISIBLE);
            admCont.setVisibility(View.GONE);
            GetAvaliacao getAvaliacao = new GetAvaliacao();
            getAvaliacao.execute();
        }
        if (!(QuestoesDisciplina.tipo.equals("adm")) && (questoesBanco.getAutorQuestao() != QuestoesDisciplina.idUsuario)) {
            conteudo.setEnabled(false);
            admApagar.setVisibility(View.GONE);
        } else {
            conteudo.setEnabled(true);
            admApagar.setVisibility(View.VISIBLE);
        }

        View view = getCurrentFocus();
        if(view != null){
            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
    class DeletarQuestao extends AsyncTask<Void, Void, ArrayList<QuestoesBanco>> {

        ProgressDialog dialog;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(TelaExibicao.this, getString(R.string.aguarde),
                    getString(R.string.deletando));
        }


        protected ArrayList<QuestoesBanco> doInBackground(Void... voids) {

            ArrayList<QuestoesBanco> questoes;
            try {
                HttpURLConnection connection;

                URL url = new URL("http://" + getString(R.string.ip) + ":5000/bccsurvivor/banco/remover?idQuestao="+questoesBanco.getIdQuestao()+"&disciplinaQuestao="+ URLEncoder.encode(questoesBanco.getDisciplinaQuestao(),"UTF-8"));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("DELETE");
                connection.addRequestProperty("Content-Type","application/json");
                connection.setDoInput(true);
                connection.connect();
                InputStream is = connection.getInputStream();
                JSONArray json = new JSONArray(converterStreamToString(is));
                is.close();
                JSONObject objeto;
                questoes = new ArrayList<QuestoesBanco>();
                for(int i = 0; i < json.length(); i++){
                    objeto = json.getJSONObject(i);
                    questoes.add(new QuestoesBanco(
                            objeto.getInt("idQuestao"),
                            objeto.getString("perguntaQuestao"),
                            objeto.getString("respostaQuestao"),
                            objeto.getString("disciplinaQuestao"),
                            objeto.getString("assuntoQuestao"),
                            objeto.getInt("autorQuestao")));
                }
                return questoes;
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

        @Override
        protected void onPostExecute(ArrayList<QuestoesBanco> questoes) {
            super.onPostExecute(questoes);
            dialog.cancel();
            Intent myIntent = new Intent(TelaExibicao.this, MostrarQuestoes.class);
            myIntent.putParcelableArrayListExtra("questoes", questoes);
            myIntent.putExtra("titulo", questoesBanco.getDisciplinaQuestao());
            startActivity(myIntent);
        }
    }

    class GetAvaliacaoNUM extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        private int avPositiva;
        private int avNegativa;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(TelaExibicao.this, getString(R.string.aguarde),
                    getString(R.string.baixando));
        }

        @Override
        protected Void doInBackground(Void... strings) {

            try {
                String url = "http://"+getString(R.string.ip)+":5000/bccsurvivor/avaliacao/quantidade?idQuestao="+ questoesBanco.getIdQuestao().toString() +"&tipoAvaliacao=1&valorAvaliacao=1";
                URL urlServidor = new URL(url);
                HttpURLConnection con = (HttpURLConnection) urlServidor.openConnection();
                con.setRequestMethod("GET");
                con.setDoInput(true);
                con.connect();
                InputStream is = con.getInputStream();
                JSONArray json = new JSONArray(converterStreamToString(is));
                is.close();
                avPositiva = json.length();

                url = "http://"+getString(R.string.ip)+":5000/bccsurvivor/avaliacao/quantidade?idQuestao="+ questoesBanco.getIdQuestao().toString() +"&tipoAvaliacao=1&valorAvaliacao=0";
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
                            if(!questoesBanco.getPerguntaQuestao().equals(conteudo.getText().toString())) {
                                DialogAtualizar d = DialogAtualizar.novaInstancia(questoesBanco, "pergunta", conteudo.getText().toString());
                                d.show(getSupportFragmentManager(), "dialog");
                            }else {
                                Toast.makeText(TelaExibicao.this, "PERGUNTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    nao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!questoesBanco.getPerguntaQuestao().equals(conteudo.getText().toString())) {
                                DialogAtualizar d = DialogAtualizar.novaInstancia(questoesBanco, "pergunta", conteudo.getText().toString());
                                d.show(getSupportFragmentManager(), "dialog");
                            }else {
                                Toast.makeText(TelaExibicao.this, "PERGUNTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                            }
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
                        if(!questoesBanco.getPerguntaQuestao().equals(conteudo.getText().toString())) {
                            DialogAtualizar d = DialogAtualizar.novaInstancia(questoesBanco, "pergunta", conteudo.getText().toString());
                            d.show(getSupportFragmentManager(), "dialog");
                        }else {
                            InserirAvaliacao insert = new InserirAvaliacao();
                            insert.execute(1);
                        }
                    }
                });
                nao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!questoesBanco.getPerguntaQuestao().equals(conteudo.getText().toString())) {
                            DialogAtualizar d = DialogAtualizar.novaInstancia(questoesBanco, "pergunta", conteudo.getText().toString());
                            d.show(getSupportFragmentManager(), "dialog");
                        }else {
                            InserirAvaliacao insert = new InserirAvaliacao();
                            insert.execute(0);
                        }
                    }
                });
            }else {
                sim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!questoesBanco.getPerguntaQuestao().equals(conteudo.getText().toString())) {
                            DialogAtualizar d = DialogAtualizar.novaInstancia(questoesBanco, "pergunta", conteudo.getText().toString());
                            d.show(getSupportFragmentManager(), "dialog");
                        }else{
                            Toast.makeText(TelaExibicao.this, "PERGUNTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                nao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!questoesBanco.getPerguntaQuestao().equals(conteudo.getText().toString())) {
                            DialogAtualizar d = DialogAtualizar.novaInstancia(questoesBanco, "pergunta", conteudo.getText().toString());
                            d.show(getSupportFragmentManager(), "dialog");
                        }else {
                            Toast.makeText(TelaExibicao.this, "PERGUNTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
    public void setConteudo(String conteudo){
        this.conteudo.setText(conteudo);
    }


}
