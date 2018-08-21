package Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnectionUtil {
	public static final DBConnectionUtil dBCU = new DBConnectionUtil();
	private Properties dBProps = new Properties();
		
	private DBConnectionUtil() {
		try {
			dBProps.load(new FileInputStream("src/main/resources/database.properties"));
		} catch (FileNotFoundException e) {
			System.out.println("not working");
		} catch (IOException e) {
			System.out.println("not working");
		}
	}
	public Connection getConnection() throws SQLException{
		return DriverManager.getConnection(dBProps.getProperty("url"), dBProps.getProperty("username"),
				dBProps.getProperty("password"));
	}
}