package br.com.formacaopadrao.model;

/**
 * Created by User on 07/10/2015.
 */
public class Wifi {

    private String nome;
    private int forca;

    public Wifi (String nome){
        this.nome = nome;
        forca = 0;
    }

    public String getNome(){
        return nome;
    }

    public int getForca(){
        return forca;
    }

    public void setForca(int forca){
        this.forca = forca;
    }

}
