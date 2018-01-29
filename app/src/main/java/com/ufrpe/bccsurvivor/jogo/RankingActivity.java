package com.ufrpe.bccsurvivor.jogo;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.bccsurvivor.R;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity implements RankingFragment.AoClicarNoPlayer{

    private ArrayList<Player>players;
    private FragmentManager fragManager;
    private RankingFragment rankFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        fragManager = getFragmentManager();
        rankFragment = (RankingFragment)fragManager.findFragmentById(R.id.fragmentRank);
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList("lista",players);
    }


    @Override
    public void clicouNoPlayer(Player p) {
        if(getResources().getBoolean(R.bool.smartphone)){
            Intent it = new Intent(this, PlayerInfoActivity.class);
            it.putExtra("player",p);
            startActivity(it);
        }else{
            exibirPlayer(p);
        }
    }

    private void exibirPlayer(Player p) {

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

        tv.setVisibility(View.VISIBLE);
        tvRecorde.setVisibility(View.VISIBLE);
        findViewById(R.id.stats_atuais).setVisibility(View.VISIBLE);
    }
}
