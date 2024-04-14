package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.NamingException;

public class DbConnection {
	 private String dbURL = "jdbc:sqlserver://x.x.x.x:1433;databaseName=FAMS\",\"sa1\", \"sqlserver";
	 private String dbUser = "sa";
	 private String dbPass = "sqlserver";
	Connection connection;
	public DbConnection() {
		connection = null;
	}
	public boolean isConnected() throws ClassNotFoundException{

		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");	
			//connection = DriverManager.getConnection("jdbc:sqlserver://172.16.0.11:1433;databaseName=FAMS;integratedSecurity=true;encrypt=true;trustServerCertificate=true","sa", "sqlserver");
			connection = DriverManager.getConnection("jdbc:sqlserver://x.x.x.x:1433;databaseName=FAMS","sa1", "sqlserver");
			
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Error");
			e.printStackTrace();
			return false;
		}
	}
}
