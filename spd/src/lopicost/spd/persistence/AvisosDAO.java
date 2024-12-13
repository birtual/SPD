package lopicost.spd.persistence;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.model.Aviso;
import lopicost.spd.model.Report;
import lopicost.spd.model.Usuario;

import lopicost.spd.struts.form.AvisosForm;
import lopicost.spd.struts.form.EnlacesForm;

public class AvisosDAO {
	
	
	static String className="AvisosDAO";
    
/*
	public static List<Aviso> findByFilters(String usuario, String idFarmacia, Date fecha) throws ClassNotFoundException, SQLException {
	    List<Aviso> result = new ArrayList<>();
		Aviso aviso =null;
	    
	    String qry = "SELECT DISTINCT a.* FROM SPD_AVISOS a WHERE 1=1 ";
	
		    if (idFarmacia != null && !idFarmacia.isEmpty()) {
		        qry += "AND idFarmacia = ? ";
		    }
		    if (fecha != null) {
		    	qry += "AND ? BETWEEN fechaInicio AND fechaFin";
		    }
		    try (Connection con = Conexion.conectar();
		            PreparedStatement stmt = con.prepareStatement(qry)) {
		           int paramIndex = 1;

		           if (idFarmacia != null && !idFarmacia.isEmpty()) {
		               stmt.setString(paramIndex++, idFarmacia);
		           }

		           if (fecha != null) {
		               java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());
		               stmt.setDate(paramIndex++, sqlDate); // fecha
		           }

		           ResultSet resultSet = stmt.executeQuery();

			    	while (resultSet.next()) {
			    		aviso = new Aviso();
			    		aviso.setActivo(resultSet.getString("activo"));
			    		aviso.setFechaFin(resultSet.getDate("fechaFin"));
			    		aviso.setFechaInicio(resultSet.getDate("fechaInicio"));
			    		aviso.setFechaInsert(resultSet.getDate("fechaInsert"));
			    		aviso.setIdFarmacia(resultSet.getString("idFarmacia"));
			    		aviso.setOidAviso(resultSet.getInt("oidAviso"));
			    		aviso.setOrden(resultSet.getInt("orden"));
			    		result.add(aviso);
			    		
			    		 }
		       } catch (SQLException e) {
		           e.printStackTrace();
		           throw e; // Propaga la excepción o maneja según corresponda
		       }

		       return result;
		   }
	*/    

	public static  List findByFilters(String usuario, String idFarmacia, boolean actuales, Date fecha) throws ClassNotFoundException, SQLException, ParseException {

		List result=new ArrayList<Report>();
		Aviso aviso =null;
		String qry = " SELECT distinct a.* ";
				qry+= " FROM SPD_AVISOS a ";
				qry+= " WHERE 1=1 ";
				//qry+= "AND a.idFarmacia is null ";
				if (StringUtils.isNotEmpty(idFarmacia))
					qry+= " AND idFarmacia = '"+idFarmacia+"' ";
			    if (actuales){
			    	qry += " AND CONVERT(datetime, getDate(), 120)   BETWEEN fechaInicio AND fechaFin ";
			    }
			    if (fecha!=null) 
			    {
			        SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        String formattedDate = sqlDateFormat.format(fecha);
			        Date parsedDate = sqlDateFormat.parse(formattedDate);
			        Timestamp sqlTimestamp = new Timestamp(parsedDate.getTime());
			        
			        System.out.println(sqlTimestamp);
			        
			    	qry += " AND CONVERT(datetime, '"+sqlTimestamp+"', 120)   BETWEEN fechaInicio AND fechaFin ";
			    	qry += " AND ACTIVO='SI' ";
			    }
				    	
		

        
  		Connection con = Conexion.conectar();
	
		System.out.println(className + "--> findByFilters -->" +qry );		
	   
	    ResultSet resultSet = null;

	    try {
	    	PreparedStatement pstat = con.prepareStatement(qry);
	    	resultSet = pstat.executeQuery();

	    	while (resultSet.next()) {
	    		aviso = new Aviso();
	    		aviso.setActivo(resultSet.getString("activo"));
	    		aviso.setFechaFin(resultSet.getDate("fechaFin"));
	    		aviso.setFechaInicio(resultSet.getDate("fechaInicio"));
	    		aviso.setFechaInsert(resultSet.getDate("fechaInsert"));
	    		aviso.setIdFarmacia(resultSet.getString("idFarmacia"));
	    		aviso.setOidAviso(resultSet.getInt("oidAviso"));
	    		aviso.setAviso(resultSet.getString("aviso"));
	    		aviso.setOrden(resultSet.getInt("orden"));
	    		aviso.setTipo(resultSet.getString("tipo"));
	    		result.add(aviso);
	    		
	    		 }
	    } catch (SQLException e) {
	    	e.printStackTrace(); 
	    } finally {
	    	con.close();
	    }
	    return result;
	}

	public static boolean nuevo(String idUser, Aviso aviso) throws ClassNotFoundException, SQLException {

        int result=0;
		  Connection con = Conexion.conectar();
		  String qry = "INSERT INTO dbo.SPD_avisos  ";
		  		qry+= " ( ";
		  		qry+= " 	fechaInicio, fechaFin, ";
		  		qry+= " 	aviso, activo, idFarmacia, usuarioCreador, orden, tipo ";
		  		qry+= " ) VALUES ( ";
	  	   		qry+= " '"+aviso.getFechaInicio()+"', '"+aviso.getFechaFin()+"', '"+aviso.getAviso()+"', ";
	  	   		qry+= " '"+aviso.getActivo()+"', '"+aviso.getIdFarmacia()+"', '"+idUser+"', '"+aviso.getOrden()+"', '"+aviso.getTipo()+"";
		  		qry+= " )  ";

                
	      		System.out.println(className + "  - nuevo-->  " +qry );		
	    try {
	         PreparedStatement pstat = con.prepareStatement(qry);
	         result=pstat.executeUpdate();
	       
	     } catch (SQLException e) {
	         e.printStackTrace();
	     } finally {con.close();}
		
		return result>0;
	}

	public static List getAvisosDeHoy(String idUsuario, String idFarmacia, boolean actuales, Date fecha) throws ClassNotFoundException, SQLException, ParseException {
		
		return findByFilters(idUsuario,  idFarmacia, actuales, fecha);
	}


	
}
 