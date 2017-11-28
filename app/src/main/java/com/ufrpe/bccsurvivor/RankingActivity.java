package com.ufrpe.bccsurvivor;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RankingActivity extends AppCompatActivity {

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



}
