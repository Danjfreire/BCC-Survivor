package com.wallace.bcc_survivor_questoes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by wallace on 24/11/2017.
 */

public class ListaQuestoes extends BaseAdapter {
    private Context contexto;

    public ListaQuestoes(Context c){
        this.contexto = c;
    }
    @Override
    public int getCount() {
        return questoesIntroducaoComputacao.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listView;

        if(convertView == null){
            listView = inflater.inflate(R.layout.item_lista, null);
        }else{
            listView = (View) convertView;
            ImageView img2 = (ImageView) listView.findViewById(R.id.iconNotification);
            img2.clearColorFilter();

        }

        TextView titulo =(TextView) listView.findViewById(R.id.tituloItem);
        TextView autor =(TextView) listView.findViewById(R.id.subtituloItem);
        ImageView img = (ImageView) listView.findViewById(R.id.iconNotification);
        titulo.setText(questoesIntroducaoComputacao[position].getAssunto() + "-" + position);
        autor.setText(questoesIntroducaoComputacao[position].getAutor());

        if(questoesIntroducaoComputacao[position].getResposta() == null){
            img.setColorFilter(R.color.colorPrimaryDark);
        }

        return listView;
    }

    private Questoes[] questoesIntroducaoComputacao = {
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte","8"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte","8"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte","8"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte","8"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte","8"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte","8"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte","8"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte","8"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte","8"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte","8"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte","8"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte"),
            new Questoes("Arquietura", "wallace", "Quantos bits têm um byte")
    };
}