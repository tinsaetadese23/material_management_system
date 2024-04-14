package model;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

//import javax.activation.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Part;

import com.mysql.jdbc.Blob;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;

public class DbConnection{
Connection connection = null;
	
	public int isConnected() throws ClassNotFoundException, NamingException{

		try{
			//datasource
			//InitialContext ic = new InitialContext();
			//DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/WallyDB");
			//connection = ((Statement) ds).getConnection();
			//end data source
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");	
			connection = DriverManager.getConnection("jdbc:sqlserver://x.x.x.x:1433;databaseName=ITTM","selam", "Ttkf@321");
			
			return 1;
		}
		catch (SQLException e)
		{
			System.out.println("Error");
			e.printStackTrace();
			return 0;
		}
	}
}