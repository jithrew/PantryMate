package appUI;

import java.sql.*;

public class Queries {
	
	Connection c = null;
	
	public static Connection dbconnect() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection c = DriverManager.getConnection("jdbc:sqlite:pantrydb1.db");
			return c;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
