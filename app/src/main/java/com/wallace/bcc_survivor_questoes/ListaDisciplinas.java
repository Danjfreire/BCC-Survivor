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

    public ListaDisciplinas(Context c){
        this.context = c;
    }

    private String[] disciplinas = {
            "CÁLCULO NI",
            "CÁLCULO NII",
            "INTRODUÇÃO A CIÊNCIA DA COMPUTAÇÃO",
            "INTRODUÇÃO À PROGRAMAÇÃO I",
            "MATEMÁTICA DISCRETA I",
            "ALGORITMOS E ESTRUTURAS DE DADOS",
            "ÁLGEBRA VETORIAL E LINEAR PARA COMPUTAÇÃO",
            "INTRODUÇÃO À PROGRAMAÇÃO II",
            "MATEMÁTICA DISCRETA II",
            "METODOLOGIA CIENTÍFICA APLICADA À COMPUTAÇÃO",
            "CIRCUITOS DIGITAIS",
            "ESTATÍSTICA EXPLORATÓRIA I",
            "FÍSICA APLICADA À COMPUTAÇÃO",
            "PROJETO E ANÁLISE DE ALGORITMOS",
            "TEORIA DA COMPUTAÇÃO",
            "ARQUITETURA E ORGANIZAÇÃO DE COMPUTADORES",
            "BANCO DE DADOS S",
            "ENGENHARIA DE SOFTWARE",
            "PARADIGMAS DE PROGRAMAÇÃO",
            "REDES DE COMPUTADORES",
            "COMPILADORES",
            "INTELIGÊNCIA ARTIFICIAL",
            "PROJETO DE DESENVOLVIMENTO DE SOFTWARE",
            "SISTEMAS DISTRIBUÍDOS",
            "SISTEMAS OPERACIONAIS",
            "COMPUTAÇÃO QUÂNTICA"};

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

        TextView textView = (TextView) view;
        if (view == null) {
            // if it's not recycled, initialize some attributes
            textView = new TextView(context);
        } else {
            textView = (TextView) view;
        }
        textView.setText(disciplinas[i]);
        return textView;
    }
}
