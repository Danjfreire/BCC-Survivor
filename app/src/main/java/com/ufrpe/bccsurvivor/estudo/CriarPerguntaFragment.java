package com.ufrpe.bccsurvivor.estudo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by wallace on 24/01/2018.
 */

public class CriarPerguntaFragment extends Fragment {
    private String disciplina;
    private EditText ed;
    private TextView tvPergunta;
    private TextView tvResposta;
    private Button salvar;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("assunto", String.valueOf(ed.getText()));
        outState.putString("pergunta", String.valueOf(tvPergunta.getText()));
        outState.putString("resposta", String.valueOf(tvResposta.getText()));
    }

    public static CriarPerguntaFragment novaInstancia(String disciplina){
        CriarPerguntaFragment criacao = new CriarPerguntaFragment();
        Bundle args = new Bundle();
        args.putString("disciplina",disciplina);
        criacao.setArguments(args);
        return criacao;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            disciplina = getArguments().getString("disciplina");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View telaCriacao = inflater.inflate(R.layout.criar_questao, container, false);

        ed = (EditText) telaCriacao.findViewById(R.id.editAssunto);
        tvPergunta = (TextView) telaCriacao.findViewById(R.id.pergunta);
        tvResposta = (TextView) telaCriacao.findViewById(R.id.resposta);
        salvar = (Button) telaCriacao.findViewById(R.id.salvar);

        if(savedInstanceState != null){
            ed.setText(savedInstanceState.getString("assunto"));
            tvPergunta.setText(savedInstanceState.getString("pergunta"));
            tvResposta.setText(savedInstanceState.getString("resposta"));
        }

        TextView tv = (TextView) telaCriacao.findViewById(R.id.textDisciplina);
        tv.setText(disciplina);

        tvPergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaixaTextoQuestao c = CaixaTextoQuestao.newInstace(getString(R.string.pergunta),R.id.pergunta,(String)tvPergunta.getText());
                c.show(getFragmentManager(),"pergunta");
            }
        });

        tvResposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaixaTextoQuestao c = CaixaTextoQuestao.newInstace(getString(R.string.resposta),R.id.resposta,(String)tvResposta.getText());
                c.show(getFragmentManager(),"resposta");
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed.getText().length() > 0 && tvResposta.getText().length() > 0 && tvPergunta.getText().length() > 0){
                    InserirPergunta inserirPergunta = new InserirPergunta();
                    inserirPergunta.execute();
                }else
                    Toast.makeText(getContext(), "Preencha os campos vazios", Toast.LENGTH_SHORT).show();
            }
        });

        return telaCriacao;
    }

    class InserirPergunta extends AsyncTask<Void, Void, Integer> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(getContext(), getString(R.string.aguarde),
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
                        "\"disciplinaQuestao\": \"" + disciplina + "\"," +
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
                Toast.makeText(getContext(), "Questão salva", Toast.LENGTH_SHORT).show();
                GetQuestoes getQuestoes = new GetQuestoes();
                getQuestoes.execute(disciplina);
            }else
                Toast.makeText(getContext(), "ERRO", Toast.LENGTH_SHORT).show();
        }
    }

    class GetQuestoes extends AsyncTask<String, Void, ArrayList<QuestoesBanco>> {

        ProgressDialog dialog;
        String disciplina;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(getContext(), getString(R.string.aguarde),
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

            Intent myIntent = new Intent(getContext(), MostrarQuestoes.class);
            myIntent.putParcelableArrayListExtra("questoes", result);
            myIntent.putExtra("titulo", disciplina);
            startActivity(myIntent);
        }
    }
}
