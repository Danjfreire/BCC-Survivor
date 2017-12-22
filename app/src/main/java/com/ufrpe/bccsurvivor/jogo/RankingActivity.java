package com.ufrpe.bccsurvivor.jogo;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        Intent it = new Intent(this, PlayerInfoActivity.class);
        it.putExtra("player",p);
        startActivity(it);
    }
}
