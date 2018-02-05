package com.ufrpe.bccsurvivor.estudo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ufrpe.bccsurvivor.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by wallace on 29/11/2017.
 */

public class ListaFragmento extends Fragment {
    private FragmentManager fragmentManager;
    private ArrayList<QuestoesBanco> questoesBancos;

    public static ListaFragmento novaInstancia(ArrayList<QuestoesBanco> questoesBancos){
        ListaFragmento listaFragmento = new ListaFragmento();
        Bundle args = new Bundle();
        args.putParcelableArrayList("questoes",questoesBancos);
        listaFragmento.setArguments(args);
        return listaFragmento;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null)
            questoesBancos = getArguments().getParcelableArrayList("questoes");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View lista = inflater.inflate(R.layout.lista_fragmento, container, false);
        ListView lv = (ListView) lista.findViewById(R.id.listaFragmento);
        lv.setDivider(null);
        lv.setDividerHeight(60);

        fragmentManager = getFragmentManager();

        ListaQuestoes l = new ListaQuestoes(getContext(),questoesBancos);

        lv.setAdapter(l);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isSmartphone()){
                    Intent i = new Intent(getContext(),TelaExibicao.class);
                    i.putExtra("questao",questoesBancos.get(position));
                    startActivity(i);
                }else{
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    TelaExibicaoFragment telaExibicao = TelaExibicaoFragment.novaIntacia(questoesBancos.get(position),"RESPOSTA");
                    ft.replace(R.id.areaTransicao, telaExibicao, "exibicao");
                    ft.commit();
                }

                }});

        return lv;
    }



    private boolean isSmartphone() {
        return getResources().getBoolean(R.bool.smartphone);
    }
}
