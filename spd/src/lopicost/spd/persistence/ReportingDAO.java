package lopicost.spd.persistence;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.Report;
import lopicost.spd.model.Usuario;

public class ReportingDAO {
	
	
	static String className="ReportingDAO";
	
	
/**
 * Devuelve un listado de reports según el usuario y sus privilegios 
 * @param usuario
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
	public static  List findByIdUser(String usuario) throws ClassNotFoundException, SQLException {

		List result=new ArrayList<Report>();
		Report report =null;
		String qry = " SELECT distinct r.*, p.* ";
				qry+= "FROM SPD_PERFIL p ";
				qry+= "INNER JOIN SPD_PERFIL_REPORT pr ON p.OIDPERFIL=pr.OIDPERFIL ";
				qry+= "INNER JOIN SPD_report r ON r.OIDREPORT=pr.OIDREPORT ";
				qry+= "INNER JOIN SPD_usuarios u ON u.perfil=p.OIDPERFIL ";
				qry+= "and u.idUsuario='"+usuario+"' and estado='ACTIVO' ";
        
  		Connection con = Conexion.conectar();
	
		System.out.println(className + "--> findByIdUser -->" +qry );		
	   
	    ResultSet resultSet = null;

	    try {
	    	PreparedStatement pstat = con.prepareStatement(qry);
	    	resultSet = pstat.executeQuery();

	    	while (resultSet.next()) {
	    		report = new Report();
	    		report.setOidReport(resultSet.getLong("oidReport"));
	    		report.setIdReport(resultSet.getString("idReport"));
	    		report.setDescripcion(resultSet.getString("descripcion"));
	    		report.setLinkReport(resultSet.getString("linkReport"));
	    		report.setNombreReport(resultSet.getString("nombreReport"));
	    		report.setParamsReport(resultSet.getString("paramsReport"));
	    		result.add(report);
	    		
	    		 }
	    } catch (SQLException e) {
	    	e.printStackTrace(); 
	    } finally {
	    	con.close();
	    }
	    return result;
	}



	
}
 