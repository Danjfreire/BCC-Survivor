package com.ufrpe.bccsurvivor;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Daniel on 28/11/2017.
 */

public class ViewHolderRanking {

     final TextView name;
     final TextView score;

    public ViewHolderRanking(View view){
        name = (TextView)view.findViewById(R.id.playername);
        score =(TextView)view.findViewById(R.id.playerscore);
    }
}
