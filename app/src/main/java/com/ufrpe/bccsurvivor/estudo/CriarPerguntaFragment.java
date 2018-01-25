package com.ufrpe.bccsurvivor.estudo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ufrpe.bccsurvivor.R;

/**
 * Created by wallace on 24/01/2018.
 */

public class CriarPerguntaFragment extends Fragment {
    private String disciplina;
    private EditText ed;
    private TextView tvPergunta;
    private TextView tvResposta;

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

        return telaCriacao;
    }
}
