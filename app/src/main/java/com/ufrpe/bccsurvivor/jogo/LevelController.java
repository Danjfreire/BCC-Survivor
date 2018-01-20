package com.ufrpe.bccsurvivor.jogo;

import android.content.Context;
import android.widget.TextView;

import com.ufrpe.bccsurvivor.R;

/**
 * Created by Dan on 15/01/2018.
 */


public class LevelController {

    private String faseAtual;


    public String loadLevel(int level, Context context) {
        String levelName = "";
        switch (level) {
            case 1: levelName = context.getResources().getString(R.string.fase1);
                    break;
            case 2: levelName = context.getResources().getString(R.string.fase2);
                    break;
        }

        return levelName;
    }

    public void updateLevel(int levelAtual) {
        //procurar a proxima fase e atualizar e setar faseAtual
    }

}
