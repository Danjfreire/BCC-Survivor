package com.ufrpe.bccsurvivor.jogo;

import android.content.Context;
import android.widget.TextView;

import com.ufrpe.bccsurvivor.R;

/**
 * Created by Dan on 15/01/2018.
 */


public class LevelController {

    public String loadLevel(int level, Context context) {
        String levelName = "";
        switch (level) {
            case 1: levelName = context.getResources().getString(R.string.fase1);
                    break;
            case 2: levelName = context.getResources().getString(R.string.fase2);
                    break;
            case 3: levelName = context.getResources().getString(R.string.fase3);
                    break;
            case 4:levelName = context.getResources().getString(R.string.fase4);
                    break;
            case 5:levelName = context.getResources().getString(R.string.fase5);
                    break;
            case 6:levelName = context.getResources().getString(R.string.fase6);
                    break;
        }

        return levelName;
    }

}

