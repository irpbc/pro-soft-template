/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokretanje;

import gui.FrmGlavna;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import static kodovi.Kodovi.*;
import kontrolerk.KontrolerKL;

/**
 *
 * @author ivan
 */
public class KlijentPokretanje {

	public static void main(String[] args) throws IOException {
		
		FrmGlavna f = new FrmGlavna();
		f.setVisible(true);
	}
}
