/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import domen.DomObjekat;
import domen.Objekat;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ivan
 */
public class TableModel1<T extends DomObjekat> extends DefaultTableModel {

	private List<T> lista;
	
	private Class[] klaseKolona;
	private String[] imenaKolona;
	
	private boolean showID;

	public TableModel1(Class<T> klasa, List<T> lista, boolean showID) {
		this.showID = showID;
		this.lista = lista;
		
		T proto = null;
		try { proto = klasa.newInstance(); } catch (Exception ex) {}
		
		Class[] klaseKolona = proto.getValueClasses();
		String[] imenaKolona = proto.getValueNames();
		
		if (showID) {
			this.imenaKolona = new String[imenaKolona.length + 1];
			this.klaseKolona = new Class[klaseKolona.length + 1];
			System.arraycopy(imenaKolona, 0, this.imenaKolona, 1, imenaKolona.length);
			System.arraycopy(klaseKolona, 0, this.klaseKolona, 1, klaseKolona.length);
			this.imenaKolona[0] = "ID";
			this.klaseKolona[0] = Long.class;
		} else {
			this.klaseKolona = klaseKolona;
			this.imenaKolona = imenaKolona;
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		if (showID) {
			if (column == 0) {
				lista.get(row).setId((long)aValue);
			} else {
				lista.get(row).setValueAt(column-1, aValue);
			}
		} else {
			lista.get(row).setValueAt(column, aValue);
		}
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (showID) {
			if (column == 0) {
				return lista.get(row).getId();
			} else {
				return lista.get(row).getValueAt(column-1);
			}
		} else {
			return lista.get(row).getValueAt(column);
		}
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public String getColumnName(int column) {
		return imenaKolona[column];
	}

	@Override
	public int getColumnCount() {
		return klaseKolona.length;
	}

	@Override
	public int getRowCount() {
		return lista.size();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return klaseKolona[columnIndex];
	}
}
