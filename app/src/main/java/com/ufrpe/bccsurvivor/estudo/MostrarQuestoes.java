package com.ufrpe.bccsurvivor.estudo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ufrpe.bccsurvivor.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wallace on 21/11/2017.
 */

public class MostrarQuestoes extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_questoes);

        if(!isSmartphone()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        Intent i = getIntent();
        final String titulo = i.getExtras().getString("titulo");

        TextView toolbarTextView = (TextView) findViewById(R.id.textView);
        toolbarTextView.setVisibility(View.VISIBLE);
        toolbarTextView.setText(titulo);

        ImageView imgIcon = (ImageView) findViewById(R.id.botaoCriar);

        imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSmartphone()){
                    Intent i = new Intent(MostrarQuestoes.this, CriarPergunta.class);
                    i.putExtra("disciplina", ((TextView)findViewById(R.id.textView)).getText());
                    startActivity(i);
                }
                else{
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    CriarPerguntaFragment criacao = CriarPerguntaFragment.novaInstancia(titulo);
                    ft.replace(R.id.areaTransicao, criacao, "telaCriacao");
                    ft.commit();
                }
            }
        });

        if(QuestoesDisciplina.tipo.equals("adm")){
            imgIcon.setVisibility(View.GONE);
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ListaFragmento lista = ListaFragmento.novaInstancia(i.<QuestoesBanco>getParcelableArrayListExtra("questoes"));
        ft.replace(R.id.fragmentoMostrarQuestoes, lista, "lista");
        ft.commit();
        /*View decorView = getWindow().getDecorView();
        int ui = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(ui);*/
    }

    private boolean isSmartphone() {
        return getResources().getBoolean(R.bool.smartphone);
    }

    public void receberConteudo(String texto, int id){
        TextView tv = (TextView) findViewById(id);
        tv.setText(texto);
    }
}

