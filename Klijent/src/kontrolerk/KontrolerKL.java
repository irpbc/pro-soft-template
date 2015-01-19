/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontrolerk;

import domen.NewClass1;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import static kodovi.Kodovi.*;
import transfer.TOKlijentZahtev;
import transfer.TOServerOdgovor;

/**
 *
 * @author ivan
 */
public class KontrolerKL {
    
    private static KontrolerKL instance;
    private Socket soket;
    
    private KontrolerKL(){}
    
    public static KontrolerKL getInstance(){
        if(instance == null){
            instance = new KontrolerKL();
        }
        return instance;
    }

    public Socket getSoket() {
        return soket;
    }

    public void setSoket(Socket soket) {
        this.soket = soket;
    }
    
    /*-------------------------------------------------------------------------------
    |  OBJECT OPERACIJE (treba za svaki pozvati kod i proslediti konkretan objekat)  |
     --------------------------------------------------------------------------------*/
    
      // dodaj, izmeni, obrisi
        public void operacija_izmene(Object obj, int kod_operacije) throws Exception {
        TOKlijentZahtev klijentZahtev = new TOKlijentZahtev();
        klijentZahtev.setOperacija(kod_operacije);
        klijentZahtev.setParametar(obj);
        ObjectOutputStream outSoket
                = new ObjectOutputStream(soket.getOutputStream());
        // belezimo zahtev u soket
        outSoket.writeObject(klijentZahtev);
        //sacekaj odgovor od servera
        ObjectInputStream inStream
                = new ObjectInputStream(soket.getInputStream());
        //dobijamo odgovor od servera
        TOServerOdgovor serverOdgovor
                = (TOServerOdgovor) inStream.readObject();
        int statusOperacije = serverOdgovor.getStatusIzvrsenja();
        if (statusOperacije == STATUS_ODGOVOR_SERVER_NOT_OK) {
            throw new Exception(serverOdgovor.getPoruka());
        } 
    }
      //vrati
        public List<Object> operacija_vracanja(Object obj, int kod_operacije) throws IOException, ClassNotFoundException, Exception {
        TOKlijentZahtev klijentZahtev = new TOKlijentZahtev();
        klijentZahtev.setOperacija(kod_operacije);
        //ako je prosledjen objekat za pretragu postavlja se kao parametar
        //ako nije onda nista
        if(obj != null)
             klijentZahtev.setParametar(obj);
        ObjectOutputStream outSoket
                = new ObjectOutputStream(soket.getOutputStream());
        // belezimo zahtev u soket
        outSoket.writeObject(klijentZahtev);
        //sacekaj odgovor od servera
        ObjectInputStream inStream
                = new ObjectInputStream(soket.getInputStream());
        //dobijamo odgovor od servera
        TOServerOdgovor serverOdgovor
                = (TOServerOdgovor) inStream.readObject();
        int statusOperacije = serverOdgovor.getStatusIzvrsenja();
        if (statusOperacije == STATUS_ODGOVOR_SERVER_OK) {
            return (List<Object>) serverOdgovor.getRezultat();
        } else {
            throw new Exception(serverOdgovor.getPoruka());
        }
    }
        
    /*------------------
    |KONKRETNE OPERACIJE |
     ------------------*/
    //class1  
    public List<NewClass1> vratiSve() throws Exception{
        return (List<NewClass1>)(List<?>) operacija_vracanja(null, SELECT_class1); 
    }    
        
    public void dodaj(NewClass1 objekat) throws Exception {
            operacija_izmene(objekat, INSERT_class1);
    }
       
    public void izmeniKlijenta(NewClass1 objekat) throws Exception{
        operacija_izmene(objekat,UPDATE_class1);
    }
    
    public void obrisiKlijenta(NewClass1 objekat) throws Exception{
        operacija_izmene(objekat, DELETE_class1);
    }
    
}
