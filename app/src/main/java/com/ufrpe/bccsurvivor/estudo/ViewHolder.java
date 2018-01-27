package com.ufrpe.bccsurvivor.estudo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ufrpe.bccsurvivor.R;

/**
 * Created by wallace on 26/11/2017.
 */

public class ViewHolder {
    final TextView assunto;
    final TextView autor;
    final TextView id;
    public ViewHolder(View view){
        assunto =(TextView) view.findViewById(R.id.conteudoAssunto);
        autor =(TextView) view.findViewById(R.id.conteudoAutor);
        id = (TextView) view.findViewById(R.id.conteudoId);
    }
}
