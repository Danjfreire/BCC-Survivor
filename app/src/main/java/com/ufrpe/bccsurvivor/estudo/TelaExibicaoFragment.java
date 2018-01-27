package com.ufrpe.bccsurvivor.estudo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by wallace on 24/01/2018.
 */

public class TelaExibicaoFragment extends Fragment {
    private QuestoesBanco questoesBanco;
    private String nomeBotao;
    private TextView tituloE;
    private TextView subTituloE;
    private TextView conteudoE;
    private TextView conteudoId;
    private Button botao;
    private Button sim;
    private Button nao;
    private TextView perres;

    public static TelaExibicaoFragment novaIntacia(QuestoesBanco questoesBanco, String nomeBotao){
        TelaExibicaoFragment tela = new TelaExibicaoFragment();
        Bundle args = new Bundle();
        args.putParcelable("questao", questoesBanco);
        args.putString("nomeBotao", nomeBotao);
        tela.setArguments(args);
        return tela;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            questoesBanco = (QuestoesBanco) getArguments().getParcelable("questao");
            nomeBotao = getArguments().getString("nomeBotao");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewResposta = inflater.inflate(R.layout.tela_exibicao_questao, container, false);
        tituloE = (TextView) viewResposta.findViewById(R.id.tituloExibicao);
        subTituloE = (TextView) viewResposta.findViewById(R.id.subTituloExicao);
        conteudoE = (TextView) viewResposta.findViewById(R.id.conteudoExibicao);
        conteudoId = (TextView) viewResposta.findViewById(R.id.idTituloExicao);
        botao = (Button) viewResposta.findViewById(R.id.respostaBotao);
        perres = (TextView) viewResposta.findViewById((R.id.perres));
        sim = (Button) viewResposta.findViewById(R.id.sim);
        nao = (Button) viewResposta.findViewById(R.id.nao);
        tituloE.setText("ASSUNTO:"+questoesBanco.getAssuntoQuestao());
        subTituloE.setText("USUÁRIO:"+QuestoesDisciplina.nomeUsuario);
        if(nomeBotao.equals("PERGUNTA")) {
            conteudoE.setText(questoesBanco.getRespostaQuestao());
            perres.setText("RESPOSTA");
        }
        else {
            conteudoE.setText(questoesBanco.getPerguntaQuestao());
            perres.setText("PERGUNTA");
        }
        conteudoId.setText("ID:"+questoesBanco.getIdQuestao().toString());
        botao.setText(nomeBotao);



        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                String n;
                if (nomeBotao.equals("RESPOSTA")) {
                    n = "PERGUNTA";
                } else{
                    n = "RESPOSTA";
                }
                TelaExibicaoFragment telaExibicao = TelaExibicaoFragment.novaIntacia(questoesBanco,n);
                ft.replace(R.id.areaTransicao, telaExibicao, "exibicao");
                ft.commit();
            }
        });

        GetAvaliacao get = new GetAvaliacao();
        get.execute();
        return viewResposta;
    }

    class InserirAvaliacao extends AsyncTask<Integer, Void, Integer> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(getContext(), getString(R.string.aguarde),
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
                if(nomeBotao.equals("RESPOSTA"))
                    opcao = "1";
                else
                    opcao = "0";
                os.write(("{\"idUsuario\": " + QuestoesDisciplina.idUsuario + "," +
                        "\"idQuestao\": " + questoesBanco.getIdQuestao() + "," +
                        "\"valorAvaliacao\": " + integers[0].toString() + "," +
                        "\"tipoAvaliacao\": " + opcao + "}").getBytes());
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
                            if(perres.getText().equals("RESPOSTA"))
                                Toast.makeText(getContext(), "RESPOSTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getContext(), "PERGUNTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                        }
                    });
                    nao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(perres.getText().equals("RESPOSTA"))
                                Toast.makeText(getContext(), "RESPOSTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getContext(), "PERGUNTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                    Toast.makeText(getContext(), ""+integer.intValue(), Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
    }

    class GetAvaliacao extends AsyncTask<Void, Void, Integer> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(getContext(), getString(R.string.aguarde),
                    getString(R.string.baixando));
        }

        @Override
        protected Integer doInBackground(Void... strings) {

            try {
                int opcao;
                if(nomeBotao.equals("RESPOSTA"))
                    opcao = 1;
                else
                    opcao = 0;
                String url = "http://"+getString(R.string.ip)+":5000/bccsurvivor/avaliacao/avaliacao?idQuestao="+ questoesBanco.getIdQuestao() +"&idUsuario="+QuestoesDisciplina.idUsuario+"&tipoAvaliacao="+opcao ;
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
                        if(perres.getText().equals("RESPOSTA"))
                            Toast.makeText(getContext(), "RESPOSTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getContext(), "PERGUNTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                    }
                });
                nao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(perres.getText().equals("RESPOSTA"))
                            Toast.makeText(getContext(), "RESPOSTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getContext(), "PERGUNTA JÁ AVALIADA", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
