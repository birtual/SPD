package lopicost.spd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.ProcesoEjecucionLog;



public class ProcesoEjecucionLogDAO extends GenericDAO{
	
	
	static String className="ProcesoEjecucionLogDAO";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
   
    /**
     * 
     * @param idUsuario
     * @param oidProcesoEjecucion
     * @param orderDesc
     * @return
     * @throws SQLException
     */
	public List<ProcesoEjecucionLog> listarLogProcesoEjecuciones(String idUsuario, int oidProcesoEjecucion, boolean orderDesc) throws SQLException {
		 List<ProcesoEjecucionLog> result = new ArrayList<ProcesoEjecucionLog>();
	     	String sql = "SELECT * FROM SPD_LOG_procesoEjecuciones ";
	     	sql+= " WHERE 1 = 1 ";
       		sql+= " AND oidProcesoEjecucion =  '" + oidProcesoEjecucion + "'";
       		sql+= " order by oidLogProcesoEjecucion  ";
	        
	  		Connection con = Conexion.conectar();
			System.out.println(className + "--> listarLogProcesoEjecuciones -->" +sql );		
		    ResultSet resultSet = null;
		    try {
		    	PreparedStatement pstat = con.prepareStatement(sql);
		    	resultSet = pstat.executeQuery();
		    	ProcesoEjecucionLog procesoEjecucionLog =null;

		    	while (resultSet.next()) {
		    		procesoEjecucionLog = creaProcesoEjecucionLog(resultSet);
		    		result.add(procesoEjecucionLog);
	    		 }
		    } catch (SQLException e) {
		    	e.printStackTrace(); 
		    } finally {
		    	con.close();
		    }
		    return result;
		}

	
	/** ok
	 * Método que se encarga de construir un bean a partir de un registro de proceso de la bbdd
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
    public ProcesoEjecucionLog creaProcesoEjecucionLog(ResultSet rs) throws SQLException {
    	ProcesoEjecucionLog procesoEjecucionLog = new ProcesoEjecucionLog();
    	procesoEjecucionLog.setOidLogProcesoEjecucion(rs.getInt("oidLogProcesoEjecucion"));
    	procesoEjecucionLog.setOidProcesoEjecucion(rs.getInt("oidProcesoEjecucion"));
    	procesoEjecucionLog.setPaso(rs.getString("paso"));
			Date dFecha  = rs.getTimestamp("fecha");
			String fecha =  DATE_FORMAT_TIME.format(dFecha);
		procesoEjecucionLog.setFecha(fecha);
		procesoEjecucionLog.setMensaje(rs.getString("mensaje"));
		return procesoEjecucionLog;
	}




    
	}









 