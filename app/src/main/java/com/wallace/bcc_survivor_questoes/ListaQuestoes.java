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
        View listView;
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listView = inflater.inflate(R.layout.item_lista, null);
            holder = new ViewHolder(listView);
            listView.setTag(holder);
        }else{
            listView = convertView;
            holder = (ViewHolder) listView.getTag();
            holder.img.clearColorFilter();
        }
        holder.titulo.setText(questoesIntroducaoComputacao[position].getAssunto() + "-" + position);
        holder.autor.setText(questoesIntroducaoComputacao[position].getAutor());

        if(questoesIntroducaoComputacao[position].getResposta() == null){
            holder.img.setColorFilter(R.color.colorPrimaryDark);
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