package com.ufrpe.bccsurvivor.estudo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.ufrpe.bccsurvivor.R;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wallace on 05/02/2018.
 */

public class DialogAtualizar extends DialogFragment {
    private QuestoesBanco questoesBanco;
    private String tipo;
    private String conteudo;

    public static DialogAtualizar novaInstancia(QuestoesBanco questao, String tipo, String conteudo){
        DialogAtualizar dialog = new DialogAtualizar();
        Bundle args = new Bundle();
        args.putParcelable("questao", questao);
        args.putString("tipo", tipo);
        args.putString("conteudo", conteudo);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            questoesBanco = getArguments().getParcelable("questao");
            tipo = getArguments().getString("tipo");
            conteudo = getArguments().getString("conteudo");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_atualizar, container, false);
        Button sim = (Button) view.findViewById(R.id.simAtua);
        Button nao = (Button) view.findViewById(R.id.naoAtua);

        sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtualizarQuestao at = new AtualizarQuestao();
                at.execute();
            }
        });
        nao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipo.equals("pergunta")){
                    TelaExibicao tela = (TelaExibicao) getActivity();
                    tela.setConteudo(questoesBanco.getPerguntaQuestao());
                }else{
                    TelaExibicaoResposta tela = (TelaExibicaoResposta) getActivity();
                    tela.setResposta(questoesBanco.getRespostaQuestao());
                }
                dismiss();
            }
        });
        return view;
    }

    class AtualizarQuestao extends AsyncTask<Void, Void, Integer> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(getContext(), getString(R.string.aguarde),
                    "atualizando");
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            HttpURLConnection connection;
            try {
                URL url = new URL("http://" + getString(R.string.ip) + ":5000/bccsurvivor/banco/atualizar/" + questoesBanco.getIdQuestao().toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.addRequestProperty("Content-Type","application/json");
                connection.setDoOutput(true);
                connection.connect();
                OutputStream os = connection.getOutputStream();
                String textoPer;
                String textoRes;
                if(tipo.equals("pergunta")){
                    textoPer = conteudo;
                    textoRes = questoesBanco.getRespostaQuestao();
                }else{
                    textoPer = questoesBanco.getRespostaQuestao();
                    textoRes = conteudo;
                }
                os.write(("{\"autorQuestao\": " + questoesBanco.getAutorQuestao() + "," +
                        "\"assuntoQuestao\": \"" + questoesBanco.getAssuntoQuestao() + "\"," +
                        "\"disciplinaQuestao\": \"" + questoesBanco.getDisciplinaQuestao() + "\"," +
                        "\"respostaQuestao\": \"" + textoRes + "\"," +
                        "\"perguntaQuestao\": \""+ textoPer +"\"}").getBytes());
                return connection.getResponseCode();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            dialog.cancel();
            if(integer != null){
                if(integer.intValue() == 200)
                    Toast.makeText(getActivity(), getString(R.string.realizada), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), ""+integer.intValue(), Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }
}
