package com.ufrpe.bccsurvivor.estudo;

import android.app.Activity;
import android.content.Intent;
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

/**
 * Created by wallace on 29/11/2017.
 */

public class ListaFragmento extends Fragment {
    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View lista = inflater.inflate(R.layout.lista_fragmento, container, false);
        ListView lv = (ListView) lista.findViewById(R.id.listaFragmento);

        fragmentManager = getFragmentManager();

        ListaQuestoes l = new ListaQuestoes(getContext());

        lv.setAdapter(l);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isSmartphone()){
                    Intent i = new Intent(getContext(),TelaExibicao.class);
                    TextView titulo = (TextView) view.findViewById(R.id.tituloItem);
                    TextView subTitulo = (TextView) view.findViewById(R.id.subtituloItem);
                    i.putExtra("titulo", titulo.getText());
                    i.putExtra("subtitulo", subTitulo.getText());
                    startActivity(i);
                }else{
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    TextView titulo = (TextView) view.findViewById(R.id.tituloItem);
                    TextView subTitulo = (TextView) view.findViewById(R.id.subtituloItem);
                    TelaExibicaoFragment telaExibicao = TelaExibicaoFragment.novaIntacia((String)titulo.getText(),(String)subTitulo.getText(),"Quem descobriu o Brasil?","resposta");
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
