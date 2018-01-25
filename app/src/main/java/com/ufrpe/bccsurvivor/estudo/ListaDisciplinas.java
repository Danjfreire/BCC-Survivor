package com.ufrpe.bccsurvivor.estudo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufrpe.bccsurvivor.R;

/**
 * Created by wallace on 21/11/2017.
 */

public class ListaDisciplinas extends BaseAdapter {
    Context context;
    private String[] disciplinas;

    public ListaDisciplinas(Context c, String[] disciplinas){
        this.context = c;
        this.disciplinas = disciplinas;
    }

    @Override
    public int getCount() {
        return disciplinas.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v;
        ViewHolder2 h;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_disciplina, viewGroup, false);
            h = new ViewHolder2(v);
            v.setTag(h);
        } else {
            v = view;
            h =(ViewHolder2) v.getTag();
        }
        h.titulo.setText(disciplinas[i]);
        return v;
    }
}
