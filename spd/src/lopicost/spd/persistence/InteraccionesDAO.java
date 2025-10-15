package lopicost.spd.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.Interaccion;


public class InteraccionesDAO extends GenericDAO{
	
	static String className="InteraccionesDAO";


	private static String getQuery(String idProceso, String CIP){
		
		String qry = " ;WITH MedicamentosResidente AS  ";
		qry+= " (	";
		qry+= " 	SELECT DISTINCT spdCNFinal AS CN ";
		qry+= " 	FROM dbo.SPD_ficheroResiDetalle ";
		qry+= " 	WHERE 1=1 ";
		
		if(CIP!=null && !CIP.equals(""))
			qry+= " 	AND resiCIP = '"+CIP+"' ";
		
		qry+= " 	AND idProceso = '"+idProceso+"' ";
		qry+= " ) ";
		qry+= " SELECT DISTINCT ";
		qry+= " co1.CN + '_'+  co2.CN as key, co1.CN AS CN1, i.PACTIVO1, co1.NOMPACTIVO AS NOMPACTIVO1, ";
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
		
		return qry;
	}
	
	
	//	Nuevo enfoque.CIP Ir añadiendo los CN de los registros validados en un TreeMap
	//	Al final de todo, hacer un repaso de todo lo cargado en el TreeMap
	
	public static  List<Interaccion> findListInteracciones(String idProceso, String CIP) throws Exception {
		String qry = getQuery(idProceso, CIP);
		List<Interaccion> result=new ArrayList<Interaccion>();
		
  		Connection con = Conexion.conectar();
		System.out.println(className + "--> findInteracciones -->" +qry );		
	    ResultSet rs = null;
	    try {
	    	PreparedStatement pstat = con.prepareStatement(qry);
	    	rs = pstat.executeQuery();

	    	while (rs.next()) {
	    		Interaccion it = creaInteraccion(rs);
	    		result.add(it);
	    	}
	    } catch (SQLException e) {
	    	e.printStackTrace(); 
	    } finally {
	    	con.close();
	    }
	    return result;
	}
	
	public static  TreeMap<String, Interaccion> findTreeMapInteracciones(String idProceso, String CIP) throws Exception {
		String qry = getQuery(idProceso, CIP);
		TreeMap<String, Interaccion> result=new TreeMap<String, Interaccion>();
		
  		Connection con = Conexion.conectar();
		System.out.println(className + "--> findTreeMapInteracciones -->" +qry );		
	    ResultSet rs = null;
	    try {
	    	PreparedStatement pstat = con.prepareStatement(qry);
	    	rs = pstat.executeQuery();

	    	while (rs.next()) {
	    		Interaccion interaccion = creaInteraccion(rs);
	    		//añadimos los dos CN con la interacción 
	    		if(interaccion!=null)
	    		{
	    			//result.put(interaccion.getCn1()+"_"+interaccion.getCn2(), interaccion);
	    			result.put(interaccion.getCn1(), interaccion);
	    			result.put(interaccion.getCn2(), interaccion);
	    			
	    		}
	    		
	    	}
	    } catch (SQLException e) {
	    	e.printStackTrace(); 
	    } finally {
	    	con.close();
	    }
	    return result;
	}



	private static Interaccion creaInteraccion(ResultSet rs) throws SQLException {
		Interaccion interaccion = new Interaccion();
    	if(rs!=null)
    	{
    		interaccion.setCn1(rs.getString("cn1"));
    		interaccion.setPactivo1(rs.getString("pactivo1"));
    		interaccion.setNomPactivo1(rs.getString("nomPactivo1"));
    		interaccion.setCn2(rs.getString("cn2"));
    		interaccion.setPactivo2(rs.getString("pactivo2"));
    		interaccion.setNomPactivo2(rs.getString("nomPactivo2"));
    		interaccion.setMedidas(rs.getString("medidas"));
    		interaccion.setTextoMedidas(rs.getString("textoMedidas"));
    		interaccion.setSemaforo(rs.getString("semaforo"));
    		interaccion.setTextoSemaforo(rs.getString("textoSemaforo"));
    		interaccion.setSentido(rs.getString("sentido"));
    		interaccion.setTextoSentido(rs.getString("textoSentido"));
     	}
		return interaccion;
	}


	
}
 