package com.wallace.bcc_survivor_questoes;

import android.media.Image;

import java.util.Date;

/**
 * Created by wallace on 25/11/2017.
 */

public class Questoes {
    private String assunto;
    private String autor;
    private String conteudo;
    private String resposta;

    public Questoes(String assunto, String autor, String conteudo){
        this.assunto = assunto;
        this.autor = autor;
        this.conteudo = conteudo;
    }
    public Questoes(String assunto, String autor, String conteudo, String resposta){
        this.assunto = assunto;
        this.autor = autor;
        this.conteudo = conteudo;
        this.resposta = resposta;
    }

    public String getAssunto(){
        return this.assunto;
    }

    public String getAutor(){
        return this.autor;
    }

    public String getConteudo(){
        return this.conteudo;
    }
    public String getResposta(){
        return this.resposta;
    }
}
