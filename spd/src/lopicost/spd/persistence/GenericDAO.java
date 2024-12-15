package lopicost.spd.persistence;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import lopicost.config.logger.Logger;
import lopicost.config.pool.dbaccess.Conexion;
 
 
public class GenericDAO {

	

		
		/**
		 * Define la cabecera del log para los casos INFO
		 */	
		private static final String cLOGGERHEADER_ERROR = "GENERICPERSISTENCEMANAGER: " 
			+ "ERROR: GenericDao";

		private final static String cLOGGERHEADER = "GenericDao: ";

		private static Class cClass=null;

		public static boolean delete(String query) throws SQLException {
			return update(query);
		}
	
		public static boolean update(String query) throws SQLException {
			int result=0;
			Connection con = Conexion.conectar();
			System.out.println( "  - edita-->  " +query );		
			try {
				PreparedStatement pstat = con.prepareStatement(query);
			    result=pstat.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				
			} finally {con.close();}
				return result>0;
		}

		/**
		 * Devuelve el nombre de una clase en formato cadena partir de una Clase.
		 * @param classToGetName la clase de la que se quiere recuperar el nombre
		 * @return una cadena con el nombre de la clase
		 */
		private static String getSimpleNameClass(Class classToGetName)
		{

			String namePackage=classToGetName.getPackage().getName();
			String nameCompleteClass=classToGetName.getName();

			return nameCompleteClass.substring(namePackage.length() + 1, 
				   nameCompleteClass.length());
		}

		public static void log (String message, int level)
		{
			Logger.log(message,level);	
		}



}
 