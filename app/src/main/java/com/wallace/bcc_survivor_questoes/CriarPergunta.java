package com.wallace.bcc_survivor_questoes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wallace on 25/11/2017.
 */

public class CriarPergunta extends AppCompatActivity implements CaixaTextoQuestao.EditNameDialogListener {
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criar_questao);

        Intent i = getIntent();

        TextView tv = (TextView) findViewById(R.id.textDisciplina);
        tv.setText(i.getExtras().getString("disciplina"));

        TextView tvPergunta = (TextView) findViewById(R.id.pergunta);
        TextView tvResposta = (TextView) findViewById(R.id.resposta);

        tvPergunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                CaixaTextoQuestao c = CaixaTextoQuestao.newInstace(getString(R.string.pergunta),R.id.pergunta);
                c.show(fm,"pergunta");
            }
        });

        tvResposta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                CaixaTextoQuestao c = CaixaTextoQuestao.newInstace(getString(R.string.resposta),R.id.resposta);
                c.show(fm,"resposta");
            }
        });
    }

    public void receberConteudo(String texto, int id){
        TextView tv = (TextView) findViewById(id);
        tv.setText(texto);
    }
}
