/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import domen.DomObjekat;
import domen.Objekat;
import domen.Objekat1;
import domen.Objekat2;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kodovi.Kodovi;
import transfer.TOKlijentZahtev;
import static kodovi.Kodovi.*;
import kontroler.Kontroler;
import transfer.TOServerOdgovor;

/**
 *
 * @author ivan
 */
public class NitKlijent extends Thread {

	private final Socket soket;

	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	
	private Kontroler kontroler = Kontroler.getInstance();
		
	
	NitKlijent(Socket soket) {
		this.soket = soket;
	}

	@Override
	public void run() {
		try {
			pokreniKomunikaciju(soket);
		} catch (Exception ex) {
			Logger.getLogger(NitKlijent.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void pokreniKomunikaciju(Socket soket) throws IOException, ClassNotFoundException {
		inputStream = new ObjectInputStream(soket.getInputStream());
		outputStream = new ObjectOutputStream(soket.getOutputStream());
		
		while (true) {
			TOKlijentZahtev klijentZahtev = (TOKlijentZahtev)inputStream.readObject();
			if (klijentZahtev == null) break;
			TOServerOdgovor odgovor = obradiZahtev(klijentZahtev);
			outputStream.writeObject(odgovor);
		}
		soket.close();
	}

	private TOServerOdgovor obradiZahtev(TOKlijentZahtev klijentZahtev) throws IOException {
		
		int operacija = klijentZahtev.getOperacija();
		TOServerOdgovor odg = new TOServerOdgovor();
		odg.setStatusIzvrsenja(STATUS_ODGOVOR_SERVER_NOT_OK);
		
		List ls = null;
		
		switch (operacija) {
			
			case VRATI_SVE_Objekat:
				ls = kontroler.vratiSve(Objekat.class);
				if (ls != null) {
					odg.setStatusIzvrsenja(STATUS_ODGOVOR_SERVER_OK);
					odg.setRezultat(ls);
				}
				break;
			case VRATI_SVE_Objekat1:
				ls = kontroler.vratiSve(Objekat1.class);
				if (ls != null) {
					odg.setStatusIzvrsenja(STATUS_ODGOVOR_SERVER_OK);
					odg.setRezultat(ls);
				}
				break;
			case VRATI_SVE_Objekat2:
				ls = kontroler.vratiSve(Objekat2.class);
				if (ls != null) {
					odg.setStatusIzvrsenja(STATUS_ODGOVOR_SERVER_OK);
					odg.setRezultat(ls);
				}
				break;
				
				
				
			case SACUVAJ:
				if (kontroler.ubaci((DomObjekat)klijentZahtev.getParametar())) {
					odg.setStatusIzvrsenja(STATUS_ODGOVOR_SERVER_OK);
				}
				break;
			case AZURIRAJ:
				if (kontroler.izmeni((DomObjekat)klijentZahtev.getParametar())) {
					odg.setStatusIzvrsenja(STATUS_ODGOVOR_SERVER_OK);
				}
				break;
			case OBRISI:
				if (kontroler.obrisi((DomObjekat)klijentZahtev.getParametar())) {
					odg.setStatusIzvrsenja(STATUS_ODGOVOR_SERVER_OK);
				}
				break;
		}
		
		return odg;
	}
}
