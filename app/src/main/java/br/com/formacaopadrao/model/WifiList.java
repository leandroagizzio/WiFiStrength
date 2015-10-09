package br.com.formacaopadrao.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by User on 07/10/2015.
 */
public class WifiList {

    private List<Wifi> l;

    public WifiList(){
        l = new ArrayList<Wifi>();
    }

    public void addWifi(String nome){
        l.add(new Wifi(nome));
    }

    public boolean atualizar(String nome, int forca){
        for (Wifi w : l) {
            if (w.getNome().equals(nome)) {
                w.setForca(forca);
                return true;
            }
        }
        return false;
    }

    public void zerarTuto(){
        for (Wifi w : l) {
            w.setForca(0);
        }
    }

    public int getForcaByNome(String nome){
        for (Wifi w : l) {
            if (w.getNome().equals(nome)) {
                return w.getForca();
            }
        }
        return -1;
    }

    public String getMaior(){
        String maiorWifi = "null";
        int maior = 0;
        for (Wifi w : l) {
            if (w.getForca() > maior){
                maior = w.getForca();
                maiorWifi = w.getNome();
            }
        }
        //zerarTuto();
        return maiorWifi;
    }

    public List<Wifi> getList(){
        return l;
    }

}
