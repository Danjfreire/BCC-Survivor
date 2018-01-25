package com.ufrpe.bccsurvivor.jogo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ufrpe.bccsurvivor.MainActivity;
import com.ufrpe.bccsurvivor.R;

public class GameOverActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        final Player playerAtual = getIntent().getParcelableExtra("player");

        if (playerAtual.getScore() == playerAtual.getScoreRecorde()){
            TextView tv_recorde = (TextView) findViewById(R.id.tv_newrecord);
            tv_recorde.setVisibility(View.VISIBLE);
        }

        TextView tv_score = (TextView) findViewById(R.id.tv_scoreFinal);
        tv_score.setText(String.valueOf(playerAtual.getScore()));

        Button btn_menu = (Button) findViewById(R.id.btn_backToMenu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GameOverActivity.this, MainActivity.class);
                playerAtual.setNumVidas(3);
                playerAtual.setPulos(1);
                playerAtual.setScore(0);
                i.putExtra("player",playerAtual);
                startActivity(i);
            }
        });

        Button btn_plaYAgain = (Button) findViewById(R.id.btn_playAgain);
        btn_plaYAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GameOverActivity.this, GameActivity.class);
                playerAtual.setNumVidas(3);
                playerAtual.setPulos(1);
                playerAtual.setScore(0);
                i.putExtra("player",playerAtual);
                startActivity(i);
            }
        });
    }
}
