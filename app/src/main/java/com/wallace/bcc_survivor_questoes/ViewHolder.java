package com.wallace.bcc_survivor_questoes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by wallace on 26/11/2017.
 */

public class ViewHolder {
    final TextView titulo;
    final TextView autor;
    final ImageView img;
    public ViewHolder(View view){
        titulo =(TextView) view.findViewById(R.id.tituloItem);
        autor =(TextView) view.findViewById(R.id.subtituloItem);
        img = (ImageView) view.findViewById(R.id.iconNotification);
    }
}
