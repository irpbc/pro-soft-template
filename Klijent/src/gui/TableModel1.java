/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import domen.Objekat;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ivan
 */
public class TableModel1 extends DefaultTableModel {

	private List<Objekat> lista;
	
	private static Class[] klase = { };
	
	private static String[] imena = {  };
	
	private static int brojKolona = 0;
	
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		Objekat o = lista.get(row);
		switch (column) {
			case 0: o.setF1((int)aValue); break;
			case 1: o.setF2((int)aValue); break;
			case 2: o.setF3((int)aValue); break;
			case 3: o.setF4((int)aValue); break;
			case 4: o.setF5((int)aValue); break;
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		Objekat o = lista.get(row);
		switch (column) {
			case 0: return o.getF1();
			case 1: return o.getF2();
			case 2: return o.getF3();
			case 3: return o.getF4();
			case 4: return o.getF5();
		}
		return o;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public String getColumnName(int column) {
		return imena[column];
	}

	@Override
	public int getColumnCount() {
		return brojKolona;
	}

	@Override
	public int getRowCount() {
		return lista.size();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return klase[columnIndex];
	}	
}
