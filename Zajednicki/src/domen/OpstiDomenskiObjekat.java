/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ivan
 */
public interface OpstiDomenskiObjekat {
    
   //public String vratiImeTabele();   //za sada nema potrebe za ovim
    
    /*----------------
    |    upiti        |
    ------------------*/
    public String vratiSQL_SELECT();
    public String vratiSQL_INSERT();
    public String vratiSQL_UPDATE();
    public String vratiSQL_DELETE();
    /*-------------------------
    |  definisanje result_set  |
    --------------------------*/
    public abstract List<OpstiDomenskiObjekat> vratiSve(ResultSet rs) throws SQLException;
    
    
}
