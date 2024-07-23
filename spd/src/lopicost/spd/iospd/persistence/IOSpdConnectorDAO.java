package lopicost.spd.iospd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lopicost.config.logger.Logger;
import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.iospd.model.IOSpdConnector;
import lopicost.spd.model.DivisionResidencia;


//public class IOSpdConnectorDAO extends GenericDao{
public class IOSpdConnectorDAO {

	private static final String columnId = "idreader";
    private static final Class cClass = IOSpdConnector.class;
    
    /**
     * Metodo que llama a la clase padre con los parametros específicos para
     * el objeto 
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @see lopicost.config.hibernate.GenericDao.findById
     */
    
    public static IOSpdConnector findById(String id) throws ClassNotFoundException, SQLException {

        log(id, Logger.DEBUG);
        return findByFields(id, null);
    }
    
    public static IOSpdConnector findByFields (String idreader,String type) throws ClassNotFoundException, SQLException
    {
    	String query= "select distinct * from GEST_IOSPD_CONNECTOR where activo=1 ";
    	if(idreader!=null && !idreader.equals(""))
    		query+=" and idreader='"+idreader+"' ";
    	if(type!=null && !type.equals(""))
    		query+=" and type='"+type+"'";
    	query+="  order by namereader  ";
    	List<IOSpdConnector>	llistaIOSpdConnector=generaObjeto(query);
    	if(llistaIOSpdConnector!=null && llistaIOSpdConnector.size()>0)
    		
        return llistaIOSpdConnector.get(0);
    	else return null;
    	
   }
    /*public static IOSpdConnector findByFields (String idreader,String type) throws ClassNotFoundException, SQLException
    {
    	String query= "select distinct * from GEST_IOSPD_CONNECTOR where activo=1 ";
    	if(idreader!=null && !idreader.equals(""))
    		query+=" and idreader='"+idreader+"' ";
    	if(type!=null && !type.equals(""))
    		query+=" and type='"+type+"'";
    	query+="  order by namereader  ";
    	List<IOSpdConnector>	llistaIOSpdConnector=generaObjeto(query);
    	if(llistaIOSpdConnector!=null && llistaIOSpdConnector.size()>0)
    		
        return llistaIOSpdConnector.get(0);
    	else return null;
    	
   }*/
    public static List findReaders() throws ClassNotFoundException, SQLException
    {
    	
       	String query = " select distinct * from GEST_IOSPD_CONNECTOR  where activo=1 order by namereader desc";
     	List<IOSpdConnector>	llistaIOSpdConnector=generaObjeto(query);
        return llistaIOSpdConnector;
     }
    
	public static List generaObjeto (String query) throws ClassNotFoundException, SQLException
    {
     	List<IOSpdConnector> llistaIOSpdConnector= new ArrayList<IOSpdConnector>();
      // 	System.out.println("IOSpdConnectorDAO.generaObjeto -->" +query );		
    	Connection con = Conexion.conectar();
     	ResultSet resultSet = null;
       	PreparedStatement pstat = con.prepareStatement(query);

        try 
        {
        	resultSet = pstat.executeQuery();
         	while (resultSet.next()) {
         		IOSpdConnector  c =new IOSpdConnector();
        		c.setOidreader(resultSet.getInt("oidreader"));
        		c.setIdreader(resultSet.getString("idreader"));
        		c.setNamereader(resultSet.getString("namereader"));
        		c.setClassname(resultSet.getString("classname"));
        		c.setDescription(resultSet.getString("description"));        		
           		c.setType(resultSet.getString("type"));        		
        		c.setActivo(resultSet.getInt("activo"));
           		llistaIOSpdConnector.add(c);
        	}
        	
         }
        finally 
        {
            try{pstat.close();resultSet.close();con.close();} catch (Exception e2){e2.printStackTrace();};
        }

        return llistaIOSpdConnector;
    } 
	
	public static void log (String message, int level)
	{
		Logger.log(message,level);	
	}
	
	public static List findDivisionResidencias() throws ClassNotFoundException, SQLException {
        return findDivisionResidencias("");
	}


	public static List findDivisionResidencias(String exportType) throws ClassNotFoundException, SQLException {
	   	String query= "select distinct * from bd_divisionResidencia ";
    	if(exportType!=null && !exportType.equals(""))
    		query+=" where idProcessIospd='"+exportType+"'";
    	query+="  order by nombreDivisionResidencia  ";
    	
    	List<DivisionResidencia> listaDivisionResidencia= new ArrayList<DivisionResidencia>();
    //   	System.out.println("IOSpdConnectorDAO.recupera listaDivisionResidencia -->" +query );		
    	Connection con = Conexion.conectar();
     	ResultSet resultSet = null;
       	PreparedStatement pstat = con.prepareStatement(query);

        try 
        {
        	resultSet = pstat.executeQuery();
         	while (resultSet.next()) {
         		DivisionResidencia  c =new DivisionResidencia();
        		c.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
        		c.setNombreDivisionResidencia(resultSet.getString("nombreDivisionResidencia"));
        		c.setIdResidencia(resultSet.getString("idResidencia"));
        		c.setIdFarmacia(resultSet.getString("idFarmacia"));
        		c.setIdRobot(resultSet.getString("idRobot"));
        		c.setIdProcessIospd(resultSet.getString("idProcessIospd"));;
        		listaDivisionResidencia.add(c);
        	}
        	
         }
        finally 
        {
            try{pstat.close();resultSet.close();con.close();} catch (Exception e2){e2.printStackTrace();};
        }

        return listaDivisionResidencia;
	}
}
