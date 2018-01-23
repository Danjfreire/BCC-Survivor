package com.ufrpe.bccsurvivor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ufrpe.bccsurvivor.estudo.QuestoesDisciplina;
import com.ufrpe.bccsurvivor.jogo.GameActivity;
import com.ufrpe.bccsurvivor.jogo.Player;
import com.ufrpe.bccsurvivor.jogo.RankingActivity;

public class MainActivity extends AppCompatActivity {

    private Player playerLogado;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //if(savedInstanceState != null){
            Intent i = getIntent();
            playerLogado = (Player) i.getParcelableExtra("player");
             Log.v("ENTROU",playerLogado.getNickname());
        //}

        Button botaoQuestoes = (Button) findViewById(R.id.botaoQuestoes);
        botaoQuestoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, QuestoesDisciplina.class);
                startActivity(myIntent);
            }
        });

        Button botaoJogo = (Button)findViewById(R.id.button);
        botaoJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this, GameActivity.class);
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
    }
}
