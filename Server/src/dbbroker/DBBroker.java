/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbbroker;

import domen.OpstiDomenskiObjekat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author ivan
 */
public class DBBroker {
    
    private static DBBroker instance;
    private Connection konekcija;
    
    private DBBroker(){}
    
    public static DBBroker getInstance(){
        if(instance == null)
            instance = new DBBroker();
        return instance;
    }
    
    public void ucitajDrajver() throws ClassNotFoundException{
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    }
    
    public void poveziSaBazom() throws SQLException{
        //treba dati pravu putanju za odbc bazu
        konekcija = DriverManager.getConnection("jdbc:odbc:g1");
        konekcija.setAutoCommit(false);
    }
    
    public void commit() throws SQLException{
        konekcija.commit();
    }
    
    public void rollback() throws SQLException{
        konekcija.rollback();
    }
    
    public void close() throws SQLException {
        konekcija.close();
    }
    
    public List<OpstiDomenskiObjekat> izvrsiSELECT(OpstiDomenskiObjekat odo) throws SQLException{
        String sql = odo.vratiSQL_SELECT();
        Statement s = konekcija.createStatement();
        return odo.vratiSve(s.executeQuery(sql));
    }
    
    public void izvrsiINSERT(OpstiDomenskiObjekat odo) throws SQLException{
        String sql = odo.vratiSQL_INSERT();
        Statement s = konekcija.createStatement();
        s.executeUpdate(sql);
    }
    
    public void izvrsiUPDATE(OpstiDomenskiObjekat odo) throws SQLException{
        String sql = odo.vratiSQL_UPDATE();
        Statement s = konekcija.createStatement();
        s.executeUpdate(sql);
    }

    public void izvrsiDELETE(OpstiDomenskiObjekat odo) throws SQLException{
        String sql = odo.vratiSQL_DELETE();
        Statement s = konekcija.createStatement();
        s.executeUpdate(sql);
    }

    
    
}
