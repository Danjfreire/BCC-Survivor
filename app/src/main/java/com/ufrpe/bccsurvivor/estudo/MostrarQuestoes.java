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

        ImageView imgIcon = (ImageView) findViewById(R.id.botaoCriar);

        imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MostrarQuestoes.this, CriarPergunta.class);
                i.putExtra("disciplina", ((TextView)findViewById(R.id.textView)).getText());
                startActivity(i);
            }
        });
        /*View decorView = getWindow().getDecorView();
        int ui = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(ui);*/
    }
}

