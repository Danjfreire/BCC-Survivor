package com.ufrpe.bccsurvivor.estudo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wallace on 25/01/2018.
 */

public class QuestoesBanco implements Parcelable{
    private Integer idQuestao;
    private String perguntaQuestao;
    private String respostaQuestao;
    private String disciplinaQuestao;
    private String assuntoQuestao;
    private Integer autorQuestao;

    protected QuestoesBanco(Parcel in) {
        if (in.readByte() == 0) {
            idQuestao = null;
        } else {
            idQuestao = in.readInt();
        }
        perguntaQuestao = in.readString();
        respostaQuestao = in.readString();
        disciplinaQuestao = in.readString();
        assuntoQuestao = in.readString();
        if (in.readByte() == 0) {
            autorQuestao = null;
        } else {
            autorQuestao = in.readInt();
        }
    }

    public static final Creator<QuestoesBanco> CREATOR = new Creator<QuestoesBanco>() {
        @Override
        public QuestoesBanco createFromParcel(Parcel in) {
            return new QuestoesBanco(in);
        }

        @Override
        public QuestoesBanco[] newArray(int size) {
            return new QuestoesBanco[size];
        }
    };

    public Integer getIdQuestao() {
        return idQuestao;
    }

    public void setIdQuestao(Integer idQuestao) {
        this.idQuestao = idQuestao;
    }

    public String getPerguntaQuestao() {
        return perguntaQuestao;
    }

    public void setPerguntaQuestao(String perguntaQuestao) {
        this.perguntaQuestao = perguntaQuestao;
    }

    public String getRespostaQuestao() {
        return respostaQuestao;
    }

    public void setRespostaQuestao(String respostaQuestao) {
        this.respostaQuestao = respostaQuestao;
    }

    public String getDisciplinaQuestao() {
        return disciplinaQuestao;
    }

    public void setDisciplinaQuestao(String disciplinaQuestao) {
        this.disciplinaQuestao = disciplinaQuestao;
    }

    public String getAssuntoQuestao() {
        return assuntoQuestao;
    }

    public void setAssuntoQuestao(String assuntoQuestao) {
        this.assuntoQuestao = assuntoQuestao;
    }

    public Integer getAutorQuestao() {
        return autorQuestao;
    }

    public void setAutorQuestao(Integer autorQuestao) {
        this.autorQuestao = autorQuestao;
    }

    public QuestoesBanco(Integer idQuestao, String perguntaQuestao, String respostaQuestao, String disciplinaQuestao, String assuntoQuestao, Integer autorQuestao) {
        this.idQuestao = idQuestao;
        this.perguntaQuestao = perguntaQuestao;
        this.respostaQuestao = respostaQuestao;
        this.disciplinaQuestao = disciplinaQuestao;
        this.assuntoQuestao = assuntoQuestao;
        this.autorQuestao = autorQuestao;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idQuestao == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(idQuestao);
        }
        dest.writeString(perguntaQuestao);
        dest.writeString(respostaQuestao);
        dest.writeString(disciplinaQuestao);
        dest.writeString(assuntoQuestao);
        if (autorQuestao == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(autorQuestao);
        }
    }
}
