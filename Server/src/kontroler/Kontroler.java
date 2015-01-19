/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import dbbroker.DBBroker;
import domen.DomObjekat;
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

	private Kontroler() {
	}

	public static Kontroler getInstance() {
		if (instance == null) {
			instance = new Kontroler();
		}
		return instance;
	}
	
	private DBBroker dbb = DBBroker.getInstance();

	public <T extends DomObjekat> List<T> vratiSve(Class<T> klasa) {
		List<T> lista = null;
		try {
			dbb.ucitajDrajver();
			dbb.poveziSaBazom();
			lista = dbb.vratiSve(klasa);
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException ex) {
			Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				DBBroker.getInstance().close();
			} catch (SQLException ex) {
				
			}
		}
		return lista;
	}

	 
	public boolean ubaci(DomObjekat objekat) {
		try {
			dbb.ucitajDrajver();
			dbb.poveziSaBazom();
			dbb.ubaciObjekat(objekat);
			dbb.commit();
			return true;
		} catch (Exception ex) {
			try {
				dbb.rollback();
			} catch (SQLException ex1) {
				Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
			}
			Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		} finally {
			try {
				dbb.close();
			} catch (SQLException ex) {
				return false;
			}
		}
	}
	
	public boolean izmeni(DomObjekat objekat) {
		try {
			dbb.ucitajDrajver();
			dbb.poveziSaBazom();
			dbb.izmeniObjekat(objekat);
			dbb.commit();
			return true;
		} catch (Exception ex) {
			try {
				dbb.rollback();
			} catch (SQLException ex1) {
				Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
			}
			Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		} finally {
			try {
				dbb.close();
			} catch (SQLException ex) {
				return false;
			}
		}
	}
	
	public boolean obrisi(DomObjekat objekat) {
		try {
			dbb.ucitajDrajver();
			dbb.poveziSaBazom();
			dbb.obrisiObjekat(objekat);
			dbb.commit();
			return true;
		} catch (Exception ex) {
			try {
				dbb.rollback();
			} catch (SQLException ex1) {
				Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
			}
			Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		} finally {
			try {
				dbb.close();
			} catch (SQLException ex) {
				return false;
			}
		}
	}

}
