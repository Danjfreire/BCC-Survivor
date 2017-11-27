package com.wallace.bcc_survivor_questoes;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
            v = new TextView(context);
            h = new ViewHolder2(v);
            v.setTag(h);
        } else {
            v = (TextView) view;
            h =(ViewHolder2) v.getTag();
        }
        h.titulo.setText(disciplinas[i]);
        return v;
    }
}
