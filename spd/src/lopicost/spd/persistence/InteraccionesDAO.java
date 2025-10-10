package lopicost.spd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import lopicost.config.pool.dbaccess.Conexion;

import lopicost.spd.security.helper.VisibilidadHelper;


public class InteraccionesDAO extends GenericDAO{
	
	
	static String className="InteraccionesDAO";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");


	public static  List findByFilters(String CIP, String idProceso) throws Exception {

		List result=new ArrayList<>();
	
		
		Nuevo enfoque.CIP Ir añadiendo los CN de los registros validados en un TreeMap
		Al final de todo, hacer un repaso de todo lo cargado en el TreeMap

		String qry = " ;WITH MedicamentosResidente AS  ";
				qry+= " (	";
				qry+= " 	SELECT DISTINCT spdCNFinal AS CN ";
				qry+= " 	FROM dbo.SPD_ficheroResiDetalle ";
				qry+= " 	WHERE resiCIP = '"+CIP+"' ";
				qry+= " 	AND idProceso = '"+idProceso+"' ";
				qry+= " ) ";
				qry+= " SELECT DISTINCT ";
				qry+= " co1.CN AS CN1, i.PACTIVO1, co1.NOMPACTIVO AS NOMPACTIVO1, ";
				qry+= " co2.CN AS CN2, i.PACTIVO2, co2.NOMPACTIVO AS NOMPACTIVO2, ";
				qry+= " i.sentido, i.texto_sentido, ";
				qry+= " i.semaforo, i.texto_semaforo, ";
				qry+= " i.medidas, i.texto_medidas, i.titulo ";
				qry+= " FROM dbo.SPD_INTERACCIONES i ";
				qry+= " INNER JOIN dbo.SPD_INTERACCIONES_COMPO co1 ON co1.PACTIVO = i.PACTIVO1 ";
				qry+= " INNER JOIN dbo.SPD_INTERACCIONES_COMPO co2 ON co2.PACTIVO = i.PACTIVO2 ";
				qry+= " INNER JOIN MedicamentosResidente m1 ON m1.CN = co1.CN ";
				qry+= " INNER JOIN MedicamentosResidente m2 ON m2.CN = co2.CN ";
				qry+= " WHERE co1.NOMPACTIVO IS NOT NULL ";
				qry+= " AND co2.NOMPACTIVO IS NOT NULL; ";
				
			    
  		Connection con = Conexion.conectar();
	
		System.out.println(className + "--> findByFilters -->" +qry );		
	   
	    ResultSet resultSet = null;

	    try {
	    	PreparedStatement pstat = con.prepareStatement(qry);
	    	resultSet = pstat.executeQuery();

	    	while (resultSet.next()) {
	    		
	    		
	    		
	    		
	    		 }
	    } catch (SQLException e) {
	    	e.printStackTrace(); 
	    } finally {
	    	con.close();
	    }
	    return result;
	}


	
}
 