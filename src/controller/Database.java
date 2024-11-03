package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	private static Connection con=null;
	private static Database instance=new Database();
	
	public static Connection getConnection() {
		return con;
	}
	public static Database getInstance() {
		return instance;
	}
	
	public void connect() {
		String url="jdbc:sqlserver://localhost:1433;databasename=DVTimViecLam";
		String user="sa";
		String pass="sapassword";
		
		try {
			con=DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
