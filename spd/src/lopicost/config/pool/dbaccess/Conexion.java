package lopicost.config.pool.dbaccess;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
    private static String url;
    private static String usuario;
    private static String contraseña;
    private static String maxConnections; 
	   static {
	        // Cargar las propiedades de conexión desde el archivo properties
	        try {
	            Properties propiedades = new Properties();
	            InputStream input = Conexion.class.getClassLoader().getResourceAsStream("lopicost/config/pool/dbaccess/poolSpd.properties");
	            if (input != null) {
	                propiedades.load(input);
	                Class.forName(propiedades.getProperty("db.driver"));
	                url = propiedades.getProperty("db.url");
	                usuario = propiedades.getProperty("db.username");
	                contraseña = propiedades.getProperty("db.password");
	                maxConnections = propiedades.getProperty("db.maxConnections");

	                url=url+";maxPoolSize="+maxConnections;
	                
	            } else {
	                System.err.println("No se pudo encontrar el archivo de propiedades.");
	            }
	        } catch (IOException | ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	    }

	    public static Connection conectar() throws SQLException {
	        return DriverManager.getConnection(url, usuario, contraseña);
	    }

	    public static void close(Connection conexion) throws SQLException {
	        if (conexion != null) {
	            conexion.close();
	        }
	    }
	

    
}
