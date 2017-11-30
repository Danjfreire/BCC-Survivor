package com.ufrpe.bccsurvivor.estudo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ufrpe.bccsurvivor.R;

/**
 * Created by wallace on 26/11/2017.
 */

public class TelaExibicaoResposta extends AppCompatActivity {
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_exibicao_resposta);

        TextView titulo = (TextView)findViewById(R.id.tituloExibicaoResposta);
        TextView subTitulo = (TextView)findViewById(R.id.subTituloExicaoResposta);
        Intent i = getIntent();
        titulo.setText(i.getStringExtra("titulo"));
        subTitulo.setText(i.getStringExtra("subtitulo"));
    }
}
