package com.ufrpe.bccsurvivor.estudo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ufrpe.bccsurvivor.R;

/**
 * Created by wallace on 21/11/2017.
 */

public class MostrarQuestoes extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_questoes);

        Intent i = getIntent();
        String titulo = i.getExtras().getString("titulo");

        TextView toolbarTextView = (TextView) findViewById(R.id.textView);
        toolbarTextView.setVisibility(View.VISIBLE);
        toolbarTextView.setText(titulo);

        ListaQuestoes l = new ListaQuestoes(this);

        ListView listaQuestoes = (ListView) findViewById(R.id.listaQuestoes);
        listaQuestoes.setAdapter(l);

        ImageView imgIcon = (ImageView) findViewById(R.id.imageView);

        imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MostrarQuestoes.this, CriarPergunta.class);
                i.putExtra("disciplina", ((TextView)findViewById(R.id.textView)).getText());
                startActivity(i);
            }
        });

        listaQuestoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MostrarQuestoes.this,TelaExibicao.class);
                TextView titulo = (TextView) view.findViewById(R.id.tituloItem);
                TextView subTitulo = (TextView) findViewById(R.id.textView);
                i.putExtra("titulo", titulo.getText());
                i.putExtra("subtitulo", subTitulo.getText());
                startActivity(i);
            }
        });
        /*View decorView = getWindow().getDecorView();
        int ui = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(ui);*/
    }
}

