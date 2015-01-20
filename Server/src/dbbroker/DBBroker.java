package dbbroker;

import domen.DomObjekat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBBroker {
	private static DBBroker instance;
	private Connection konekcija;
	
	private DBBroker(){}
	
	public static DBBroker getInstance() {
		if (instance == null)
			instance = new DBBroker();
		return instance;
	}

	public void ucitajDrajver() throws ClassNotFoundException {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	}

	public void poveziSaBazom() throws SQLException {
		konekcija = DriverManager.getConnection("jdbc:odbc:baza");
		konekcija.setAutoCommit(false);
	}

	public void commit() throws SQLException{
		konekcija.commit();
	}

	public void rollback() throws SQLException{
		konekcija.rollback();
	}

	public void close() throws SQLException {
		konekcija.close();
	}
	
	private static int[] keyIndex = { 1 };

	public void ubaciObjekat(DomObjekat o) throws SQLException {

		Class[] klase = o.getValueClasses();
		String[] imena = o.getValueNames();
		
		int count = klase.length + 1; // + 1 za id

		String qm = imena[0];
		for (int i = 1; i < count; i++)
			qm += ("," + imena[i]);
				
		String sql = "INSERT INTO " + o.getClass().getSimpleName() + "VALUES (" + qm + ")";
		PreparedStatement ps = konekcija.prepareStatement(sql);
		
		for (int i = 0; i < klase.length; i++) {
			Class cl = klase[i];
			if (cl == Integer.class)		ps.setInt(i+1,(int)o.getValueAt(i));
			else if (cl == Long.class)		ps.setLong(i+1,(long)o.getValueAt(i));
			else if (cl == String.class)	ps.setString(i+1,(String)o.getValueAt(i));
			else if (cl == Double.class)	ps.setDouble(i+1,(double)o.getValueAt(i));
		}

		ps.executeUpdate(sql, keyIndex);

		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		o.setId(rs.getLong(1));
	}

	public void izmeniObjekat(DomObjekat o) throws SQLException {

		Class[] klase = o.getValueClasses();
		int count = klase.length + 1; // + 1 za id

		String qm = "?";
		for (int i = 1; i < count; i++)
			qm += ",?";
		
		String sql = "INSERT INTO " + o.getClass().getSimpleName() + "VALUES (" + qm + ")";
		PreparedStatement ps = konekcija.prepareStatement(sql);

		ps.setLong(1, o.getId());

		for (int i = 0; i < klase.length; i++) {
			Class cl = klase[i];
			if (cl == Integer.class)		ps.setInt(i+2,(int)o.getValueAt(i));
			else if (cl == Long.class)		ps.setLong(i+2,(long)o.getValueAt(i));
			else if (cl == String.class)	ps.setString(i+2,(String)o.getValueAt(i));
			else if (cl == Double.class)	ps.setDouble(i+2,(double)o.getValueAt(i));
		}

		ps.executeUpdate(sql, 1);
	}

	public void obrisiObjekat(DomObjekat o) throws SQLException {
		String sql = "DELETE FROM " + o.getClass().getSimpleName() + "WHERE Id=?";

		PreparedStatement st = konekcija.prepareStatement(sql);
		st.setLong(1, o.getId());

		st.executeUpdate(sql);
	}

	public <T extends DomObjekat> List<T> vratiSve(Class<T> klasa)
			throws SQLException, IllegalAccessException, InstantiationException {

		String sql = "SELECT * from " + klasa.getSimpleName();

		Statement st = konekcija.createStatement();

		if (!st.execute(sql)) {
			return null;
		}
		
		T proto = klasa.newInstance();

		Class[] valKlase = proto.getValueClasses();

		ArrayList<T> lista = new ArrayList<>();

		ResultSet rs = st.getResultSet();
		
		while (rs.next()) {
			T o = klasa.newInstance();
			for (int i = 0; i < valKlase.length; i++) {
				Class cl = valKlase[i];
				if (cl == Integer.class)		o.setValueAt(i, rs.getInt(i+2));
				else if (cl == Long.class)		o.setValueAt(i, rs.getLong(i+2));
				else if (cl == String.class)	o.setValueAt(i, rs.getString(i+2));
				else if (cl == Double.class)	o.setValueAt(i, rs.getDouble(i+2));
			}
			lista.add(o);
		}

		return lista;
	}
}