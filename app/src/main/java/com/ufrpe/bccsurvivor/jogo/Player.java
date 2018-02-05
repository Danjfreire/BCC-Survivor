package com.ufrpe.bccsurvivor.jogo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Daniel on 25/11/2017.
 */

public class Player implements Comparable<Player>, Parcelable {

    private int id;

    private String nickname;
    private Integer faseAtual;
    private Integer numVidas;
    private Integer pulos;
    private Integer score;
    private Integer scoreRecorde;
//    public Player(String nickname, int score, int faseAtual, int numVidas, int pulos, int scoreRecorde) {

    public Player(int id, String admLogin){
        this.id =  id;
        this.nickname = admLogin;
    }

    public Player(Parcel parcel) {
        this.id = parcel.readInt();
        this.nickname = parcel.readString();
        this.score = parcel.readInt();
        this.scoreRecorde = parcel.readInt();
        this.pulos = parcel.readInt();
        this.numVidas = parcel.readInt();
        this.faseAtual = parcel.readInt();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    //    }
//        this.scoreRecorde = scoreRecorde;
//        this.pulos = pulos;
//        this.numVidas = numVidas;
//        this.faseAtual = faseAtual;
//        this.score = score;
//        this.nickname = nickname;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return nickname + " - " + score;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getFaseAtual() {
        return faseAtual;
    }

    public void setFaseAtual(Integer faseAtual) {
        this.faseAtual = faseAtual;
    }

    public Integer getNumVidas() {
        return numVidas;
    }

    public void setNumVidas(Integer numVidas) {
        this.numVidas = numVidas;
    }

    public Integer getPulos() {
        return pulos;
    }

    public void setPulos(Integer pulos) {
        this.pulos = pulos;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getScoreRecorde() {
        return scoreRecorde;
    }

    public void setScoreRecorde(Integer scoreRecorde) {
        this.scoreRecorde = scoreRecorde;
    }

    @Override
    public int compareTo(Player o) {
        if (this.scoreRecorde < o.getScoreRecorde())
            return 1;
        if (this.scoreRecorde > o.getScoreRecorde())
            return -1;
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nickname);
        dest.writeInt(score);
        dest.writeInt(scoreRecorde);
        dest.writeInt(pulos);
        dest.writeInt(numVidas);
        dest.writeInt(faseAtual);
    }
}
