/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;


/**
 *
 * @author ivan
 */
public abstract class DomObjekat {
	
	protected long id;

	public long getId() { return id; }
	public void setId(long id) { this.id = id; }
	
	public abstract Class[] getValueClasses();

	public abstract void getValues(Object[] vs);

	public abstract void setValues(Object[] vs);
}
