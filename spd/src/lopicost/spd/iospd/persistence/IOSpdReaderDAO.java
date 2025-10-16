package lopicost.spd.iospd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lopicost.config.logger.Logger;
import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.iospd.model.IOSpdReader;


//public class IOSpdReaderDAO extends GenericDao{
public class IOSpdReaderDAO {
    private static final String columnId = "idreader";
    /**
     * Define el nombre de la clase para el objeto 
     */
    private static final Class cClass = IOSpdReader.class;
    
    /**
     * Metodo que llama a la clase padre con los parametros específicos para
     * el objeto 
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @see lopicost.config.hibernate.GenericDao.findById
     */
    public static IOSpdReader findById(String idreader) throws ClassNotFoundException, SQLException {

        log(idreader, Logger.DEBUG);
       	String query= "select distinct *  from GEST_IOSPD_READER where activo=1 ";
    	if(idreader!=null && !idreader.equals(""))
    		query+=" and idreader='"+idreader+"' ";

       	List<IOSpdReader>	llistaIOSpdReader=generaObjeto(query);
    	if(llistaIOSpdReader!=null && llistaIOSpdReader.size()>0)
    		
        return llistaIOSpdReader.get(0);
    	else return null;
    }
    
    public static List findReaders () throws ClassNotFoundException, SQLException
    {
       	String query = " select * from GEST_IOSPD_READER where activo=1 order by namereader ";
         	List<IOSpdReader>	llistaIOSpdReader=generaObjeto(query);
            return llistaIOSpdReader;
      }
 	
 	public static List generaObjeto (String query) throws ClassNotFoundException, SQLException
    {
     	List<IOSpdReader> llistaIOSpdReader= new ArrayList<IOSpdReader>();
   //    	System.out.println("IOSpdReaderDAO.generaObjeto -->" +query );		
    	Connection con = Conexion.conectar();
     	ResultSet resultSet = null;
       	PreparedStatement pstat = con.prepareStatement(query);
       try 
        {
        	resultSet = pstat.executeQuery();
         	while (resultSet.next()) {
         		IOSpdReader  c =new IOSpdReader();
        		c.setOidreader(resultSet.getLong("oidreader"));
        		c.setIdreader(resultSet.getString("idreader"));
        		c.setNamereader(resultSet.getString("namereader"));
        		c.setClassname(resultSet.getString("classname"));
        		c.setDescription(resultSet.getString("description"));        		
           		c.setActivo(resultSet.getInt("activo"));        		
           	 llistaIOSpdReader.add(c);
        	}
        	
         }
        finally 
        {
            try{pstat.close();resultSet.close();con.close();} catch (Exception e2){e2.printStackTrace();};
        }

        return llistaIOSpdReader;
    }
    
    
	public static void log (String message, int level)
	{
		Logger.log(message,level);	
	}
	
}
