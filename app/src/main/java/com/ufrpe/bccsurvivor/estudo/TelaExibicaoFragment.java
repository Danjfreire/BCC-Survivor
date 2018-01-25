package com.ufrpe.bccsurvivor.estudo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ufrpe.bccsurvivor.R;

/**
 * Created by wallace on 24/01/2018.
 */

public class TelaExibicaoFragment extends Fragment {
    private String titulo;
    private String subTitulo;
    private String conteudo;
    private String nomeBotao;

    public static TelaExibicaoFragment novaIntacia(String titulo, String subTitulo, String conteudo, String nomeBotao){
        TelaExibicaoFragment tela = new TelaExibicaoFragment();
        Bundle args = new Bundle();
        args.putString("titulo", titulo);
        args.putString("subTitulo", subTitulo);
        args.putString("conteudo", conteudo);
        args.putString("nomeBotao", nomeBotao);
        tela.setArguments(args);
        return tela;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            titulo = getArguments().getString("titulo");
            subTitulo = getArguments().getString("subTitulo");
            conteudo = getArguments().getString("conteudo");
            nomeBotao = getArguments().getString("nomeBotao");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewResposta = inflater.inflate(R.layout.tela_exibicao_questao, container, false);
        TextView tituloE = (TextView) viewResposta.findViewById(R.id.tituloExibicao);
        TextView subTituloE = (TextView) viewResposta.findViewById(R.id.subTituloExicao);
        TextView conteudoE = (TextView) viewResposta.findViewById(R.id.conteudoExibicao);
        Button botao = (Button) viewResposta.findViewById(R.id.respostaBotao);
        tituloE.setText(titulo);
        subTituloE.setText(subTitulo);
        conteudoE.setText(conteudo);
        botao.setText(nomeBotao);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                String c, n;
                if (nomeBotao.equals("resposta")) {
                    c = "Pedro Alvares Cabral";
                    n = "pergunta";
                } else{
                    c = "Quem descobriu o Brasil?";
                    n = "resposta";
                }
                TelaExibicaoFragment telaExibicao = TelaExibicaoFragment.novaIntacia(titulo,subTitulo,c,n);
                ft.replace(R.id.areaTransicao, telaExibicao, "exibicao");
                ft.commit();
            }
        });
        return viewResposta;
    }
}
