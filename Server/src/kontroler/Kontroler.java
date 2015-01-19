/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import dbbroker.DBBroker;
import domen.NewClass1;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ivan
 */
public class Kontroler {
    
    private static Kontroler instance;
    private Kontroler(){}
    
    public static Kontroler getInstance(){
        if(instance == null)
            instance = new Kontroler();
        return instance;
    }
   //vrati
    public List<NewClass1> vratiSve(NewClass1 objekat){
        List<NewClass1> lista = null;
        try {
            DBBroker.getInstance().ucitajDrajver();
            DBBroker.getInstance().poveziSaBazom();
            //operacija
            lista = (List<NewClass1>)(List<?>)DBBroker.getInstance().izvrsiSELECT(objekat);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                DBBroker.getInstance().close();
            } catch (SQLException ex) {
                //nista
            }
        }
        return lista;
    }
   //sacuvaj,izmeni,obrisi 
    public void dodaj/*izmeni,obrisi*/(NewClass1 objekat){
    try {
            DBBroker.getInstance().ucitajDrajver();
            DBBroker.getInstance().poveziSaBazom();
            
            //operacija
            DBBroker.getInstance().izvrsiINSERT(objekat);
           //DBBroker.getInstance().izvrsiUPDATE(objekat);
           //DBBroker.getInstance().izvrsiDELETE(objekat);
            DBBroker.getInstance().commit();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
        try {
            DBBroker.getInstance().rollback();
        } catch (SQLException ex1) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
        }
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                DBBroker.getInstance().close();
            } catch (SQLException ex) {
              //nista  
            }
        }
    }
    
    
    
}