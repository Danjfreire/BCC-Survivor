package com.ufrpe.bccsurvivor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ufrpe.bccsurvivor.estudo.QuestoesDisciplina;
import com.ufrpe.bccsurvivor.jogo.Player;
import com.ufrpe.bccsurvivor.jogo.RankingActivity;
import com.ufrpe.bccsurvivor.jogo.StartGameActivity;

public class MainActivity extends AppCompatActivity {

    private Player playerLogado;
    private String tipo;
    private int admId;
    private String admLogin;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent i = getIntent();
        tipo = i.getStringExtra("tipo");

        if(tipo.equals("adm")){
            admId = i.getExtras().getInt("amdId");
            admLogin = i.getStringExtra("admLogin");
        }else {
            playerLogado = (Player) i.getParcelableExtra("player");
            Log.v("ENTROU",playerLogado.getNickname());
        }

        Button botaoQuestoes = (Button) findViewById(R.id.botaoQuestoes);
        botaoQuestoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, QuestoesDisciplina.class);
                if(tipo.equals("adm")){
                    myIntent.putExtra("tipo",tipo);
                    myIntent.putExtra("nomeUsuario", admLogin);
                    myIntent.putExtra("idUsuario",admId);
                }else{
                    myIntent.putExtra("tipo","usuario");
                    myIntent.putExtra("nomeUsuario", playerLogado.getNickname());
                    myIntent.putExtra("idUsuario",playerLogado.getId());
                }
                startActivity(myIntent);
            }
        });

        Button botaoJogo = (Button)findViewById(R.id.button);
        botaoJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this, StartGameActivity.class);
                myintent.putExtra("player",playerLogado);
                startActivity(myintent);
            }
        });

        Button botaoRanking = (Button)findViewById(R.id.botaoRanking);
        botaoRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this, RankingActivity.class);
                startActivity(myintent);
            }
        });

        if(tipo.equals("adm")){
            TextView tv = (TextView) findViewById(R.id.menuMain);
            tv.setText("ADM");
            botaoJogo.setVisibility(View.GONE);
            botaoRanking.setVisibility(View.GONE);
        }
    }
}
