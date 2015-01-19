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
	
	public static DBBroker getInstance(){
		if(instance == null)
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
		
		int count = klase.length + 1; // + 1 za id

		String qm = "?";;
		for (int i = 1; i < count; i++)
			qm += ",?";
				
		String sql = "INSERT INTO " + o.getClass().getSimpleName() + "VALUES (" + qm + ")";
		PreparedStatement ps = konekcija.prepareStatement(sql);
		
		Object[] vrednosti = new Object[klase.length];
		o.getValues(vrednosti);

		for (int i = 0; i < klase.length; i++) {
			Class cl = klase[i];
			Object val = vrednosti[i];
			if (cl == Integer.class)		ps.setInt(i+1,(int)val);
			else if (cl == Long.class)		ps.setLong(i+1,(long)val);
			else if (cl == String.class)	ps.setString(i+1,(String)val);
			else if (cl == Double.class)	ps.setDouble(i+1,(double)val);
			else if (cl == DomObjekat.class)ps.setLong(i+1, ((DomObjekat)val).getId());
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
		
		Object[] vrednosti = new Object[count];
		o.getValues(vrednosti);

		ps.setLong(1, o.getId());

		for (int i = 0; i < klase.length; i++) {
			Class cl = klase[i];
			Object val = vrednosti[i];
			if (cl == Integer.class)		ps.setInt(i+2,(int)val);
			else if (cl == Long.class)		ps.setLong(i+2,(long)val);
			else if (cl == String.class)	ps.setString(i+2,(String)val);
			else if (cl == Double.class)	ps.setDouble(i+2,(double)val);
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
		Object[] vrednosti = new Object[valKlase.length];

		ArrayList<T> lista = new ArrayList<>();

		ResultSet rs = st.getResultSet();
		
		while (rs.next()) {
			for (int i = 0; i < valKlase.length; i++) {
				Class cl = valKlase[i];
				if (cl == Integer.class)		vrednosti[i] = rs.getInt(i+2);
				else if (cl == Long.class)		vrednosti[i] = rs.getLong(i+2);
				else if (cl == String.class)	vrednosti[i] = rs.getString(i+2);
				else if (cl == Double.class)	vrednosti[i] = rs.getDouble(i+2);
			}
			T o = klasa.newInstance();
			o.setValues(vrednosti);

			lista.add(o);
		}

		return lista;
	}
}