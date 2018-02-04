package com.ufrpe.bccsurvivor.estudo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufrpe.bccsurvivor.R;

import java.util.ArrayList;

/**
 * Created by wallace on 24/11/2017.
 */

public class ListaQuestoes extends BaseAdapter {
    private Context contexto;
    private String disciplina;
    private ArrayList<QuestoesBanco> questoesBanco;

    public ListaQuestoes(Context c, ArrayList<QuestoesBanco> questoesBanco){
        this.contexto = c;
        this.disciplina = disciplina;
        this.questoesBanco = questoesBanco;
    }
    @Override
    public int getCount() {
        return questoesBanco.size();
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
        View view;
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_lista, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.assunto.setText(questoesBanco.get(position).getAssuntoQuestao());
        holder.autor.setText(questoesBanco.get(position).getAutorQuestao().toString());
        holder.id.setText(questoesBanco.get(position).getIdQuestao().toString());
        return view;
    }
    private boolean isSmartphone() {
        return contexto.getResources().getBoolean(R.bool.smartphone);
    }
}
