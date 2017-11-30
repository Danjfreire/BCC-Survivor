package com.ufrpe.bccsurvivor.estudo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.bccsurvivor.R;

/**
 * Created by wallace on 25/11/2017.
 */

public class CriarPergunta extends AppCompatActivity implements CaixaTextoQuestao.EditNameDialogListener {
    private EditText ed;
    private TextView tvPergunta;
    private TextView tvResposta;

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

        if(savedInstanceState != null){
            ed.setText(savedInstanceState.getString("assunto"));
            tvPergunta.setText(savedInstanceState.getString("pergunta"));
            tvResposta.setText(savedInstanceState.getString("resposta"));
        }

        Intent i = getIntent();

        TextView tv = (TextView) findViewById(R.id.textDisciplina);
        tv.setText(i.getExtras().getString("disciplina"));

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