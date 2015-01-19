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
public class Objekat2 extends DomObjekat {
	
	private int f1;
	private int f2;
	private int f3;
	private int f4;
	private int f5;

	public Objekat2() {
	}
	
	private static Class[] klase = {  };

	public Class[] getValueClasses() { return klase; }

	public void getValues(Object[] vs) {
		vs[0] = f1;
		vs[1] = f2;
		vs[2] = f3;
		vs[3] = f4;
		vs[4] = f5;
	}

	public void setValues(Object[] vs) {
		f1 = (int)vs[0];
		f2 = (int)vs[1];
		f3 = (int)vs[2];
		f4 = (int)vs[3];
		f5 = (int)vs[4];
	}

	public int getF1() {
		return f1;
	}

	public void setF1(int f1) {
		this.f1 = f1;
	}

	public int getF2() {
		return f2;
	}

	public void setF2(int f2) {
		this.f2 = f2;
	}

	public int getF3() {
		return f3;
	}

	public void setF3(int f3) {
		this.f3 = f3;
	}

	public int getF4() {
		return f4;
	}

	public void setF4(int f4) {
		this.f4 = f4;
	}

	public int getF5() {
		return f5;
	}

	public void setF5(int f5) {
		this.f5 = f5;
	}
	
	
}

