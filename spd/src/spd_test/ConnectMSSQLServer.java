package spd_test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnectMSSQLServer
{
   public void dbConnect(String db_connect_string,
            String db_userid,
            String db_password)
   {
	    String result = null;
	    Statement statement =  null;
		ResultSet rs = null;
		Connection conn = null;
      try {
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         conn = DriverManager.getConnection(db_connect_string,
                  db_userid, db_password);
         System.out.println("connected");
         statement = conn.createStatement();
         String queryString = "select * from sysobjects where type='u'";
         rs = statement.executeQuery(queryString);
         while (rs.next()) {
            System.out.println(rs.getString(1));
         }
      } catch (Exception e) {
         e.printStackTrace();
		}finally {
			try {rs.close();} catch (Exception e1) {}
			try {statement.close();} catch (Exception e1) {}
		}	
   }
   
 

   public static void main(String[] args) throws ClassNotFoundException
   {
	  ConnectMSSQLServer connServer = new ConnectMSSQLServer();
      //connServer.dbConnect("jdbc:sqlserver://<hostname>", "<user>", "<password>");
	  //connServer.dbConnect("jdbc:sqlserver://WIN-FML34DAFTCP;databaseName=SPDAC", "sa", "20L0pic0st16");
	  connServer.dbConnect("jdbc:sqlserver://localhost;databaseName=SPDAC", "sa", "20L0pic0st16");
	   Connection con = conectar();
//		   System.out.println("connected main" );

   }
   
   
	public static  Connection conectar() throws ClassNotFoundException {
	       Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	       
		Connection con = null;
		String password = "20L0pic0st16";
		String usuario = "sa";
		
		String url = "jdbc:sqlserver://localhost:1433;databaseName=SPDAC";
	     
		try {
		        con = DriverManager.getConnection(url,
	        		usuario, password);
	         
		//	if (con != null) {
			//	System.out.println("Conectado");
		//	}
		} catch (SQLException e) {
			System.out.println("No se pudo conectar a la base de datos");
			e.printStackTrace();
		}
		return con;
	}
	
 public void closeConnection(Connection con) {
     try {
         con.close();
     } catch (SQLException e) {
         e.printStackTrace();
     }
 }
 
 
 
}
