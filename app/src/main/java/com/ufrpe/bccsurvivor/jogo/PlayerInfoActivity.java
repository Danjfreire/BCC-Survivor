package com.ufrpe.bccsurvivor.jogo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ufrpe.bccsurvivor.R;

import org.w3c.dom.Text;

public class PlayerInfoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);

        Intent i = getIntent();
        Player p = (Player) i.getParcelableExtra("player");

        TextView tv = (TextView) findViewById(R.id.tv_player);
        tv.setText(p.getNickname());

        TextView tvRecorde = (TextView) findViewById(R.id.tv_infoRecorde);
        tvRecorde.setText(String.valueOf(p.getScoreRecorde()));

        TextView tvVidaAtual = (TextView) findViewById(R.id.infoVidas);
        tvVidaAtual.setText(String.valueOf(p.getNumVidas()));

        TextView tvPulos = (TextView) findViewById(R.id.infoPulos);
        tvPulos.setText(String.valueOf(p.getPulos()));

        TextView tvScore = (TextView) findViewById(R.id.infoScore);
        tvScore.setText(String.valueOf(p.getScore()));


        if(savedInstanceState != null){

        }
    }
}
