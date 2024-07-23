package lopicost.spd.iospd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lopicost.config.logger.Logger;
import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.iospd.model.IOSpdProcess;


//public class IOSpdProcessDAO extends GenericDao{
public class IOSpdProcessDAO {
    private static final String columnId = "idprocess";

    private static final Class cClass = IOSpdProcess.class;
    
    public static IOSpdProcess findById(String id) throws ClassNotFoundException, SQLException {

        log(id, Logger.DEBUG);
        return findByFields(id, null);
    }
    
    
    public static List<IOSpdProcess> findProcess (String type, String idDivisionResidencia, int tipoIO) throws ClassNotFoundException, SQLException
    {
    	String query = " select * from GEST_IOSPD_PROCESS where activo=1 ";
    	if(type!=null && !type.equals(""))
    		query+=" and type='"+type+"'";
    	if(idDivisionResidencia!=null && !idDivisionResidencia.equals(""))
    		query+=" and idprocess='"+idDivisionResidencia+"'";
    	if(tipoIO>-1)
    		query+=" and gestion='"+ tipoIO+"' ";
    	query+=" order by nameprocess ";
     	List<IOSpdProcess>	llistaIOSpdProcess=generaObjeto(query);
        return llistaIOSpdProcess;
    }

    public static List findProcess (String type, String idDivisionResidencia) throws ClassNotFoundException, SQLException
    {
        return findProcess(type, idDivisionResidencia, 0 );
    }
    public static List findProcess (String type) throws ClassNotFoundException, SQLException
    {
        return findProcess(type, null, 0 );
    }



    public static IOSpdProcess findByFields (String type, String idprocess, boolean soloGestion) throws ClassNotFoundException, SQLException
    {
    	String query= "select distinct *  from GEST_IOSPD_PROCESS where activo=1 ";
    	if(idprocess!=null && !idprocess.equals(""))
    		query+=" and idprocess='"+idprocess+"'";
    	if(type!=null && !type.equals(""))
    		query+=" and type='"+type+"'";
    	if(soloGestion)
    		query+=" and gestion='1' ";
    		
    	List<IOSpdProcess>	llistaIOSpdProcess=generaObjeto(query);
    	if(llistaIOSpdProcess!=null && llistaIOSpdProcess.size()>0)
    		
        return llistaIOSpdProcess.get(0);
    	else return null;
    	
   }
 	
    public static IOSpdProcess findByFields (String type, String idprocess) throws ClassNotFoundException, SQLException
	{
        return findByFields(type, idprocess, false);
 	}

    
    
    public static IOSpdProcess findByIdType (String type, String id) throws ClassNotFoundException, SQLException
    {
         return findByFields(id, type);
    }

    
 	public static List<IOSpdProcess> generaObjeto (String query) throws ClassNotFoundException, SQLException
    {
     	List<IOSpdProcess> llistaIOSpdProcess= new ArrayList<IOSpdProcess>();
     //  	System.out.println("IOSpdProcessDAO.generaObjeto -->" +query );		
    	Connection con = Conexion.conectar();
     	ResultSet resultSet = null;
       	PreparedStatement pstat = con.prepareStatement(query);

        try 
        {
        	resultSet = pstat.executeQuery();
         	while (resultSet.next()) {
        		IOSpdProcess  c =new IOSpdProcess();
        		c.setOidprocess(resultSet.getLong("oidprocess"));
        		c.setIdprocess(resultSet.getString("idprocess"));
        		c.setNameprocess(resultSet.getString("nameprocess"));
        		c.setClassname(resultSet.getString("classname"));
        		c.setDescription(resultSet.getString("description"));        		
           		c.setType(resultSet.getString("type"));        		
        		c.setActivo(resultSet.getInt("activo"));
        		c.setGestion(resultSet.getInt("gestion"));
           	llistaIOSpdProcess.add(c);
        	}
        	
         }
        finally 
        {
            try{pstat.close();resultSet.close();con.close();} catch (Exception e2){e2.printStackTrace();};
        }

        return llistaIOSpdProcess;
    }
 	
 	
	public static void log (String message, int level)
	{
		Logger.log(message,level);	
	}
	
}
