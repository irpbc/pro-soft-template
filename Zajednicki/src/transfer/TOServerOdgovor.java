/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer;

import java.io.Serializable;

/**
 *
 * @author ivan
 */
public class TOServerOdgovor implements Serializable{

    private Object rezultat;
    private int statusIzvrsenja;
    private String poruka;

    public Object getRezultat() {
        return rezultat;
    }

    public void setRezultat(Object rezultat) {
        this.rezultat = rezultat;
    }

    public int getStatusIzvrsenja() {
        return statusIzvrsenja;
    }

    public void setStatusIzvrsenja(int statusIzvrsenja) {
        this.statusIzvrsenja = statusIzvrsenja;
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }
}
