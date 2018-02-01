package com.ufrpe.bccsurvivor.jogo;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ufrpe.bccsurvivor.R;

/**
 * Created by Dan on 20/01/2018.
 */

public class GameController {


    private int numVidas;
    private int numPulos;
    private int scoreAtual;

    public GameController(int numVidas, int numPulos, int scoreAtual) {
        this.numVidas = numVidas;
        this.numPulos = numPulos;
        this.scoreAtual = scoreAtual;
    }

    public int getNumVidas() {
        return numVidas;
    }

    public void aumentarScore(View tvScore) {
        TextView score = (TextView) tvScore;
        //scoreAtual = Integer.parseInt(score.getText().toString());
        scoreAtual += 100;

        score.setText(String.valueOf(scoreAtual));
    }

    public void diminuirVida(View tvVidas) {
        TextView vidas = (TextView) tvVidas;
        numVidas = Integer.parseInt(vidas.getText().toString());
        numVidas--;

        vidas.setText(String.valueOf(numVidas));
    }

    public int getScoreAtual() {
        return scoreAtual;
    }

    public void diminuirPulos(View tvPulos) {
        TextView pulos = (TextView) tvPulos;
        numPulos = Integer.parseInt(pulos.getText().toString());
        numPulos--;

        pulos.setText(String.valueOf(numPulos));
    }

    public int getNumPulos() {
        return numPulos;
    }

    public void setNumVidas(Integer numVidas) {
        this.numVidas = numVidas;
    }

    public void setNumPulos(Integer numPulos) {
        this.numPulos = numPulos;
    }


}
