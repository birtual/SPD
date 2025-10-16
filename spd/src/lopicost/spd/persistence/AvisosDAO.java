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
import lopicost.spd.security.helper.VisibilidadHelper;


public class AvisosDAO extends GenericDAO{
	
	
	static String className="AvisosDAO";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");


	public static  List<Aviso> findByFilters(String usuario, int oidAviso, String idFarmacia, boolean actuales, Date fecha) throws Exception {

		List<Aviso> result=new ArrayList<Aviso>();
		Aviso aviso =null;
		String qry = " SELECT distinct a.* ";
				qry+= " FROM SPD_AVISOS a ";
				qry+= " WHERE 1=1 ";
				//qry+= "AND a.idFarmacia is null ";
				if(oidAviso>0)
					qry+= " AND oidAviso = '"+oidAviso+"' ";
				if (StringUtils.isNotEmpty(idFarmacia))
					qry+= " AND idFarmacia = '"+idFarmacia+"' ";
			    if (actuales){
			    	qry += " AND fechaInicio <=  CONVERT(datetime, getDate(), 120) AND  fechaFin +1 >= CONVERT(datetime, getDate(), 120)";
			    }
			    if (fecha!=null) 
			    {
			        SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        String formattedDate = sqlDateFormat.format(fecha);
			        Date parsedDate = sqlDateFormat.parse(formattedDate);
			        Timestamp sqlTimestamp = new Timestamp(parsedDate.getTime());
			        
			        System.out.println(sqlTimestamp);
			        
			    	qry += " AND fechaInicio  <=  CONVERT(datetime, '"+sqlTimestamp+"', 120)   AND  fechaFin +1 >= CONVERT(datetime, '"+sqlTimestamp+"', 120) ";
			    	qry += " AND ACTIVO='SI' ";
			    }
				qry+= " AND (idFarmacia IS NULL OR idFarmacia = ''  OR idFarmacia IN ( " + VisibilidadHelper.idFarmaciasVisibles(usuario)  + "))";

				qry+= " ORDER BY a.activo desc, a.orden, a.fechaInicio ";
				
			    
  		Connection con = Conexion.conectar();
	
		System.out.println(className + "--> findByFilters -->" +qry );		
	   
	    ResultSet resultSet = null;

	    try {
	    	PreparedStatement pstat = con.prepareStatement(qry);
	    	resultSet = pstat.executeQuery();

	    	while (resultSet.next()) {
	    		aviso = new Aviso();
	    		aviso.setActivo(resultSet.getString("activo"));
               
	    		Date fechaInicioDB = resultSet.getDate("fechaInicio");  // O rs.getTimestamp("fecha_inicio") si es Timestamp
                if (fechaInicioDB != null) {
                    // Convertir de java.sql.Date a String con el formato dd/MM/yyyy
                    String fechaInicioStr = DATE_FORMAT.format(fechaInicioDB);
                    aviso.setFechaInicio(fechaInicioStr);
                }
                // Recuperar la fecha_fin de la base de datos
                Date fechaFinDB = resultSet.getDate("fechaFin"); // O rs.getTimestamp("fecha_fin") si es Timestamp
                if (fechaFinDB != null) {
                    // Convertir de java.sql.Date a String con el formato dd/MM/yyyy
                    String fechaFinStr = DATE_FORMAT.format(fechaFinDB);
                    aviso.setFechaFin(fechaFinStr);
                }
               
	    		aviso.setFechaInsert(resultSet.getTimestamp("fechaInsert"));
	    		aviso.setIdFarmacia(resultSet.getString("idFarmacia"));
	    		aviso.setOidAviso(resultSet.getInt("oidAviso"));
	    		aviso.setTexto(resultSet.getString("texto"));
	    		aviso.setUsuarioCreador(resultSet.getString("usuarioCreador"));
	    		aviso.setOrden(resultSet.getInt("orden"));
	    		aviso.setTipo(resultSet.getString("tipo"));
	    		aviso.setUsuarioUpdate(resultSet.getString("usuarioUpdate"));
	    		aviso.setFechaUpdate(resultSet.getTimestamp("fechaUpdate"));
	    		
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
		  		qry+= " 	texto, activo, idFarmacia, usuarioCreador, orden, tipo, usuarioUpdate, fechaUpdate ";
		  		qry+= " ) VALUES ( ";
	  	   		//qry+= " '"+new Date(aviso.getFechaInicioDate().getTime())+"', '"+new Date(aviso.getFechaFinDate().getTime())+"', '"+aviso.getTexto()+"', ";
	  	   		qry+= " CONVERT(DATETIME, '"+aviso.getFechaInicio()+"', 103),  CONVERT(DATETIME, '"+aviso.getFechaFin()+"', 103), '"+aviso.getTexto()+"', ";
	  	   		qry+= " '"+aviso.getActivo()+"', '"+aviso.getIdFarmacia()+"', '"+idUser+"', '"+aviso.getOrden()+"', '"+aviso.getTipo()+"', ";
	  	   		qry+= " '"+idUser+"', GETDATE() ";
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

	public static List<Aviso> getAvisosDeHoy(String idUsuario, int oidAviso, String idFarmacia, boolean actuales, Date fecha) throws Exception {
		
		return findByFilters(idUsuario, oidAviso, idFarmacia, actuales, fecha);
	}

	public static Aviso findByOid(String idUsuario, int oidAviso) throws Exception {
		List<Aviso> aux = findByFilters(idUsuario, oidAviso,null, false, null);
		if (aux != null && !aux.isEmpty()) {
			return (Aviso)aux.get(0);
		}
		return null;
	}

	public static boolean edita(String query) {
		// TODO Esbozo de método generado automáticamente
		return false;
	}


	
}
 