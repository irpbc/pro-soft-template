/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontrolerk;

import domen.DomObjekat;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
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

	private ObjectOutputStream soketOut;
	private ObjectInputStream soketIn;

	private KontrolerKL() {
	}

	public static KontrolerKL getInstance() {
		if (instance == null) {
			instance = new KontrolerKL();
		}
		return instance;
	}

	private void poveziSeSaServerom() throws IOException {

		soket = new Socket(InetAddress.getLocalHost(), PORT);
		soketOut = new ObjectOutputStream(soket.getOutputStream());
		soketIn = new ObjectInputStream(soket.getInputStream());

		this.soket = soket;
		this.soketOut = soketOut;
		this.soketIn = soketIn;
	}

	private void prekiniVezu() {
		try {
			soket.close();
		} catch (Exception ex) {

		}
		soket = null;
		soketIn = null;
		soketOut = null;
	}

	public <T extends DomObjekat> List<T> vratiSve(int kod_operacije)
			throws IOException, ClassNotFoundException, Exception {

		TOKlijentZahtev klijentZahtev = new TOKlijentZahtev();
		klijentZahtev.setOperacija(kod_operacije);

		try {
			poveziSeSaServerom();

			soketOut.writeObject(klijentZahtev);
			TOServerOdgovor serverOdgovor = (TOServerOdgovor) soketIn.readObject();

			int statusOperacije = serverOdgovor.getStatusIzvrsenja();
			if (statusOperacije == STATUS_ODGOVOR_SERVER_OK) {
				return (List<T>) serverOdgovor.getRezultat();
			} else {
				throw new Exception(serverOdgovor.getPoruka());
			}
		} finally {
			prekiniVezu();
		}
	}

	public long ubaci(DomObjekat objekat) throws Exception {
		return operacijaIzmene(objekat, UBACI);
	}

	public void izmeni(DomObjekat objekat) throws Exception {
		operacijaIzmene(objekat, IZMENI);
	}

	public void obrisi(DomObjekat objekat) throws Exception {
		operacijaIzmene(objekat, OBRISI);
	}

	private long operacijaIzmene(Object obj, int kod_operacije) throws Exception {

		TOKlijentZahtev klijentZahtev = new TOKlijentZahtev();
		klijentZahtev.setOperacija(kod_operacije);
		klijentZahtev.setParametar(obj);

		try {
			poveziSeSaServerom();

			soketOut.writeObject(klijentZahtev);
			TOServerOdgovor serverOdgovor = (TOServerOdgovor) soketIn.readObject();

			int statusOperacije = serverOdgovor.getStatusIzvrsenja();
			if (statusOperacije == STATUS_ODGOVOR_SERVER_NOT_OK) {
				throw new Exception(serverOdgovor.getPoruka());
			}
			Object rez = serverOdgovor.getRezultat();
			if (rez != null) {
				return (long)rez;
			}
			return -1;
		} finally {
			prekiniVezu();
		}
	}
	
	
}
