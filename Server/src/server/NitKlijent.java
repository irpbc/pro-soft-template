/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import domen.OpstiDomenskiObjekat;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.TOKlijentZahtev;
import static kodovi.Kodovi.*;
import kontroler.Kontroler;
import transfer.TOServerOdgovor;

/**
 *
 * @author ivan
 */
public class NitKlijent extends Thread{

    private final Socket soket;
    
    NitKlijent(Socket soket) {
        this.soket = soket;
    }
    
    @Override
    public void run() {
        try {
            pokreniKomunikaciju(soket);
        } catch (IOException ex) {
            Logger.getLogger(NitKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NitKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    private void pokreniKomunikaciju(Socket soket) throws IOException, ClassNotFoundException {
        while (true) {
            ObjectInputStream inputStream
                    = new ObjectInputStream(soket.getInputStream());
            TOKlijentZahtev klijentZahtev
                    = (TOKlijentZahtev) inputStream.readObject();
            obradiZahtev(klijentZahtev, soket);
        }
    }
    
    private void posaljiOdgovor(TOServerOdgovor serverOdgovor, Socket soket) throws IOException {
        ObjectOutputStream outputStream
                = new ObjectOutputStream(soket.getOutputStream());
        outputStream.writeObject(serverOdgovor);
    }   
    
    private void obradiZahtev(TOKlijentZahtev klijentZahtev, Socket soket) throws IOException {
        int operacija = klijentZahtev.getOperacija();
        switch (operacija) {
           //za class1  
            case SELECT_class1:   /* poziv funkcije11 */      break;
            case INSERT_class1:   /* poziv funkcije12 */      break;
            case UPDATE_class1:   /* poziv funkcije13 */      break;
            case DELETE_class1:   /* poziv funkcije14 */      break;   
           //za class2     
            case SELECT_class2:   /* poziv funkcije21 */      break;
            case INSERT_class2:   /* poziv funkcije22 */      break;    
            case UPDATE_class2:   /* poziv funkcije23 */      break;    
            case DELETE_class2:   /* poziv funkcije24 */      break;   
           //za class3     
            case SELECT_class3:   /* poziv funkcije31 */      break;
            case INSERT_class3:   /* poziv funkcije32 */      break;
            case UPDATE_class3:   /* poziv funkcije33 */      break;
            case DELETE_class3:   /* poziv funkcije34 */      break;    
	}
    }
   //11,21,31
    public void funkcija_SELECT(Socket s) throws IOException{
        List<OpstiDomenskiObjekat> lista = null;
        String poruka = "";
        int status = STATUS_ODGOVOR_SERVER_OK;
        try {
            /*------------------------------------------------
            |lista = Kontroler.getInstance.vratiSve_class[1-3]|
            -------------------------------------------------*/
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            status = STATUS_ODGOVOR_SERVER_NOT_OK;
            System.out.println("NIJE USPESNO URADJENA OPERACIJA PREKO MREZE");
            poruka = ex.getMessage();
        }
        //pravimo server odgovor
        TOServerOdgovor serverOdgovor = new TOServerOdgovor();
        serverOdgovor.setRezultat(lista);
        serverOdgovor.setStatusIzvrsenja(status);
        serverOdgovor.setPoruka(poruka);
        //saljemo odgovor klijentu
        posaljiOdgovor(serverOdgovor, soket);
	}
   //...ostale
    private void funkcijaINSERT_UPDATE_DELETE(Socket soket /*, class[1-4] objekat*/ ) throws IOException {
        String poruka = "Uspesno sacuvan klijent";
        int status = STATUS_ODGOVOR_SERVER_OK;
        try {
            /*--------------------------------------------
            | Kontroler.getInstance().insert(objekat);    |
            | Kontroler.getInstance().update(objekat);    |
            | Kontroler.getInstance().delete(objekat);    |
             ---------------------------------------------*/
        } catch (Exception ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            status = STATUS_ODGOVOR_SERVER_NOT_OK;
            System.out.println("NIJE USPESNO URADJENA OPERACIJA PREKO MREZE");
            poruka = ex.getMessage();
        }
        //pravimo server odgovor
        TOServerOdgovor serverOdgovor = new TOServerOdgovor();
        serverOdgovor.setRezultat(null);
        serverOdgovor.setStatusIzvrsenja(status);
        serverOdgovor.setPoruka(poruka);
        //saljemo odgovor klijentu
        posaljiOdgovor(serverOdgovor, soket);
    }
    
    
}
