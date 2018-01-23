package com.ufrpe.bccsurvivor.jogo;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ufrpe.bccsurvivor.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Daniel on 28/11/2017.
 */

public class RankingFragment extends ListFragment {

    private ArrayList<Player>players;

    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ranking, container, false);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState == null){
            carregaPlayers();
        }else{
            players = savedInstanceState.getParcelableArrayList("lista");
        }

        //ListView ranking = (ListView)view.findViewById(R.id.ranking);
        AdapterRanking adapter = new AdapterRanking(getActivity(), R.layout.item_ranking,players);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Activity activity = getActivity();
        if (activity instanceof AoClicarNoPlayer) {
            Player player = (Player) l.getItemAtPosition(position);
            AoClicarNoPlayer listener = (AoClicarNoPlayer) activity;
            listener.clicouNoPlayer(player);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList("lista",players);
    }

    private void carregaPlayers(){
        this.players = new ArrayList<Player>();
        Random rand = new Random();

        for(int i = 1; i<=100;i++){
            //players.add(new Player("Player"+i,rand.nextInt(1000)));
        }
        Collections.sort(players);
    }

    public interface AoClicarNoPlayer{
        void clicouNoPlayer(Player p);
    }

}
