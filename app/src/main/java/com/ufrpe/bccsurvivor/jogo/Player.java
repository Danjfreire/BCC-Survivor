package com.ufrpe.bccsurvivor.jogo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Daniel on 25/11/2017.
 */

public class Player implements Comparable<Player> ,Parcelable{

    private String nickname;
    private int score;


    public Player(String nickname, int score){
        this.nickname = nickname;
        this.score = score;
    }

    public Player(Parcel parcel){
        this.nickname = parcel.readString();
        this.score = parcel.readInt();
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

    public int getScore() {
        return score;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return nickname + " - "+ score;
    }


    @Override
    public int compareTo(Player o) {
        if(this.score < o.getScore())
            return 1;
        if(this.score > o.getScore())
            return -1;
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nickname);
        dest.writeInt(score);
    }
}
