package com.ufrpe.bccsurvivor.jogo;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ufrpe.bccsurvivor.R;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Daniel on 28/11/2017.
 */

public class RankingFragment extends ListFragment {

    private ArrayList<Player> players;

    public View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ranking, container, false);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            carregaPlayers();
        } else {
            players = savedInstanceState.getParcelableArrayList("lista");
            listarPLayers(players);
        }

        //ListView ranking = (ListView)view.findViewById(R.id.ranking);
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
        bundle.putParcelableArrayList("lista", players);
    }

    public interface AoClicarNoPlayer {

        void clicouNoPlayer(Player p);

    }

    private void carregaPlayers() {
        RestController controller = new RestController();
        controller.execute();
    }

    private void listarPLayers(List<Player> allPlayers) {
        this.players = new ArrayList<>(allPlayers);
        Collections.sort(players);
        listar();
    }

    private void listar() {
        AdapterRanking adapter = new AdapterRanking(getActivity(), R.layout.item_ranking, players);
        setListAdapter(adapter);
    }

    private class RestController extends AsyncTask<Void, Void, List<Player>> {

        @Override
        protected List<Player> doInBackground(Void... voids) {

            URL url = null;
            try {
                url = new URL("http://"+getString(R.string.ip)+":5000/user/players");
                HttpURLConnection conection = (HttpURLConnection) url.openConnection();
                Reader reader = new InputStreamReader(conection.getInputStream());
                Gson gson = new GsonBuilder().create();
                List<Player> allPlayers = new ArrayList<>();
                allPlayers = Arrays.asList(gson.fromJson(reader, Player[].class));
                return allPlayers;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }
        @Override
        protected void onPostExecute(List<Player> allPlayers) {
            listarPLayers(allPlayers);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
