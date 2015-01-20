/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package domen;

/**
 *
 * @author Ivan
 */
public class Objekat extends DomObjekat {
	
	private int f1;
	private int f2;
	private int f3;
	private int f4;
	private int f5;

	public Objekat() {
	}
	
	private static Class[] klase = {  };
	private static String[] imena = {  };

	public Class[] getValueClasses() { return klase; }
	public String[] getValueNames() { return imena; }
	
	@Override
	public Object getValueAt(int index) {
		switch (index) {
			case 0: return f1;
			case 1: return f2;
			case 2: return f3;
			case 3: return f4;
			case 4: return f5;
		}
		return null;
	}

	@Override
	public void setValueAt(int index, Object value) {
		switch (index) {
			case 0: f1 = (int)value;
			case 1: f2 = (int)value;
			case 2: f3 = (int)value;
			case 3: f4 = (int)value;
			case 4: f5 = (int)value;
		}
	}
}

