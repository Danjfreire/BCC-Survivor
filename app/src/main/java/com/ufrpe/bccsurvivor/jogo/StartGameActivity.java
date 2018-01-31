package com.ufrpe.bccsurvivor.jogo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ufrpe.bccsurvivor.R;

public class StartGameActivity extends AppCompatActivity {

    private Player playerLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        playerLogado = getIntent().getParcelableExtra("player");

        Button btn_newGame = (Button) findViewById(R.id.btn_newGame);
        btn_newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewGame();
                startGame();
            }
        });

        Button btn_continue = (Button) findViewById(R.id.btn_continue);
        if(playerLogado.getFaseAtual() == 1 && playerLogado.getScore() == 0){
            btn_continue.setEnabled(false);
        }else{
            btn_continue.setBackground(getResources().getDrawable(R.drawable.btn_red_effect));
        }
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });
    }

    private void startGame() {
        Intent i = new Intent(StartGameActivity.this, GameActivity.class);
        i.putExtra("player", playerLogado);
        startActivity(i);
    }

    private void setNewGame() {
        playerLogado.setScore(0);
        playerLogado.setNumVidas(3);
        playerLogado.setPulos(1);
        playerLogado.setFaseAtual(1);
    }

}
