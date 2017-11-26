package com.wallace.bcc_survivor_questoes;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by wallace on 26/11/2017.
 */

public class TelaExibicao extends AppCompatActivity {
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_exibicao_questao);

        final TextView titulo = (TextView)findViewById(R.id.tituloExibicao);
        final TextView subTitulo = (TextView)findViewById(R.id.subTituloExicao);
        Intent i = getIntent();
        titulo.setText(i.getStringExtra("titulo"));
        subTitulo.setText(i.getStringExtra("subtitulo"));

        Button b = (Button) findViewById(R.id.respostaBotao);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TelaExibicao.this,TelaExibicaoResposta.class);
                i.putExtra("titulo",titulo.getText());
                i.putExtra("subtitulo",subTitulo.getText());
                startActivity(i);
            }
        });
    }
}
