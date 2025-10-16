package lopicost.spd.persistence;

import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.model.ControlPrincipioActivo;

import lopicost.spd.model.SpdLog;
import lopicost.spd.struts.form.SpdLogForm;
import lopicost.spd.utils.HelperSPD;

import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;
import  lopicost.config.logger.Logger;
import lopicost.config.pool.dbaccess.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


	public class SpdLogDao {

	private static final Class cClass = SpdLog.class;
	private static final String csLOGGER_HEADER = "SpdLogDao";
	private static final String csLOGGER_HEADER_ERROR = csLOGGER_HEADER + ": ERROR -- "+ csLOGGER_HEADER;


	public int getCount(String idUsuario, SpdLogForm formulario) throws SQLException {
		return getCount(idUsuario,  formulario.getIdUsuarioFiltro(), formulario.getCampoGoogle(), formulario.getCIP(),  formulario.getOidDivisionResidenciaFiltro(), formulario.getIdProcesoFiltro()
				, formulario.getIdApartadoFiltro(), formulario.getIdAccionFiltro(), formulario.getIdSubAccionFiltro(), formulario.getFechaInicioFiltro(), formulario.getFechaFinFiltro());
	}

	public List getListado(String idUsuario, SpdLogForm formulario, int inicio, int fin, boolean count) throws SQLException {
		return getListado(idUsuario, formulario.getIdUsuarioFiltro(), formulario.getCampoGoogle(), formulario.getCIP(), formulario.getOidDivisionResidenciaFiltro(), formulario.getIdProcesoFiltro()
				, formulario.getIdApartadoFiltro(), formulario.getIdAccionFiltro(), formulario.getIdSubAccionFiltro(), formulario.getFechaInicioFiltro(), formulario.getFechaFinFiltro(), inicio,  fin, count);  			
		
	}

	
	
	public int getCount(String idUsuario,  String idUsuarioFiltro, String campoGoogle, String CIP, int oidDivisionResidencia, String idProceso, String idApartado, String idAccion, String idSubAccion, String fechaInicioFiltro, String fechaFinFiltro) throws SQLException {
		String query = getQuery(idUsuario, idUsuarioFiltro, campoGoogle, CIP, oidDivisionResidencia, idProceso, idApartado, idAccion, idSubAccion, fechaInicioFiltro, fechaFinFiltro, 0,1, true);
		   
		 System.out.println( csLOGGER_HEADER  + " getCount -->" +query );		
	   	 Connection con = Conexion.conectar();
	   //		 System.out.println("connected main" );
	     ResultSet resultSet = null;
		int result =0;
		try {
	       PreparedStatement pstat = con.prepareStatement(query);
	       resultSet = pstat.executeQuery();
	       resultSet.next();
	       result = resultSet.getInt("quants");

	   } catch (SQLException e) {
	       e.printStackTrace();
	   }finally {con.close();}

	   return result;
	}

	private static String getQuery(String idUsuario, String idUsuarioFiltro,  String campoGoogle, String CIP, int oidDivisionResidencia, String idProceso, String idApartado, String idAccion
				, String idSubAccion, String fechaInicioFiltro, String fechaFinFiltro, int inicio, int fin, boolean count) {
		   
		String query = " select * from ( select  distinct ROW_NUMBER() OVER(ORDER BY fecha desc) AS ROWNUM, * ";
		   if(count) query = "select count(*) as quants " ;

		   query+=" from dbo.SPD_LOG  where 1=1 ";
			if (CIP!=null && !CIP.equals(""))
				query+="and CIP ='"+CIP+"'";
			//if (idDivisionResidencia!=null && !idDivisionResidencia.equals(""))
			if (oidDivisionResidencia>0)
				query+="and idDivisionResidencia in (select idDivisionResidencia from dbo.bd_divisionResidencia where oidDivisionResidencia='"+oidDivisionResidencia+"')";
			if (idUsuarioFiltro!=null && !idUsuarioFiltro.equals(""))
				query+="and idUsuario ='"+idUsuarioFiltro+"'";
			if (idProceso!=null && !idProceso.equals(""))
				query+="and idProceso ='"+idProceso+"'";
			if (idApartado!=null && !idApartado.equals(""))
				query+="and idApartado ='"+idApartado+"'";
			if (idAccion!=null && !idAccion.equals(""))
				query+="and idAccion ='"+idAccion+"'";
			if (idSubAccion!=null && !idSubAccion.equals(""))
				query+="and idSubAccion ='"+idSubAccion+"'";
			if (campoGoogle!=null && !campoGoogle.equals(""))
			{
				query+=" and ( CIP like '%"+campoGoogle+"%' ";
				query+="	OR idDivisionResidencia like '%"+campoGoogle+"%' ";
				query+="	OR idApartado like '%"+campoGoogle+"%' ";
				query+="	OR idAccion like '%"+campoGoogle+"%' ";
				query+="	OR idSubAccion like '%"+campoGoogle+"%' ";
				query+="	OR descripcion like '%"+campoGoogle+"%' ";
				query+="	) ";
			}
			if (fechaInicioFiltro!=null && !fechaInicioFiltro.equals(""))
			{
				query+="and fecha >='"+fechaInicioFiltro+"'";
			}
			if (fechaFinFiltro!=null && !fechaFinFiltro.equals(""))
			{
				query+="and fecha <='"+fechaFinFiltro+"'";
			}		
			if(!count) query+= 	HelperSPD.getOtrosSql2008(inicio, fin, count);
			
			return query;
	}

	/**
	 * Creación de un registro de log's
	 * @param tablename Tabla afectada
	 * @param cl Clase afectada
	 * @param oidobject Objeto afectado
	 * @param action Acción efectuado
	 * @return
	 */
	//@TODO String subaction, String description 
	public static boolean addLog(SpdLog spdlog) throws ClassNotFoundException, SQLException {
        int result = 0;
        final Connection con = Conexion.conectar();
        String qry = " INSERT INTO SPD_LOG (idUsuario, CIP, idDivisionResidencia, IdProceso, IdApartado, idAccion, idSubAccion, descripcion) VALUES ( ";
        qry+= "'" + spdlog.getIdUsuario() + "',  '" + spdlog.getCIP() + "',  '" + spdlog.getIdDivisionResidencia() + "',  '" + spdlog.getIdProceso() + "', '" + spdlog.getIdApartado() + "',  '" + 
        		spdlog.getIdAccion() + "', '" + spdlog.getIdSubAccion() + "', '" + spdlog.getDescripcion() + "')";
        System.out.println("addLog -->" + qry);
        try {
            final PreparedStatement pstat = con.prepareStatement(qry);
            result = pstat.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }finally {con.close();}
        return result > 0;
    }


	public static List getListado(String idUsuario,  String idUsuarioFiltro,  String campoGoogle,  String CIP, int oidDivisionResidencia, String idProceso, String idApartado, String idAccion, String idSubAccion
				, String fechaInicioFiltro, String fechaFinFiltro, int inicio, int fin, boolean count) throws  SQLException
	{
		List result=new ArrayList();
		Connection con = Conexion.conectar();
		   
		String query = getQuery(idUsuario, idUsuarioFiltro, campoGoogle, CIP, oidDivisionResidencia, idProceso, idApartado, idAccion, idSubAccion, fechaInicioFiltro, fechaFinFiltro, inicio, fin, count);
		System.out.println(csLOGGER_HEADER + "--> getListado -->" +query );		
		ResultSet resultSet = null;
 	 	
 	    try {
 	         PreparedStatement pstat = con.prepareStatement(query);
 	         resultSet = pstat.executeQuery();
 	         while (resultSet.next()) {
 	        	SpdLog f = new SpdLog();
	        	 f.setIdUsuario(resultSet.getString("idUsuario"));
	        	 f.setCIP(resultSet.getString("CIP"));
 	        	 f.setIdDivisionResidencia(resultSet.getString("idDivisionResidencia"));
	        	 f.setIdProceso(resultSet.getString("idProceso"));
	        	 f.setIdApartado(resultSet.getString("idApartado"));
	        	 f.setIdAccion(resultSet.getString("idAccion"));
	        	 f.setIdSubAccion(resultSet.getString("idSubAccion"));
	        	 f.setDescripcion(HelperSPD.getDetalleRowFechasOk(resultSet.getString("descripcion")));
	        	 java.sql.Timestamp fecha = resultSet.getTimestamp("fecha");
		 		 if(fecha!=null) 
				 {
		 		    // Crea un objeto SimpleDateFormat con el formato deseado
		 			 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		 			 String fechaFormateada = (fecha != null) ? sdf.format(new Date(fecha.getTime())) : "";
		 			 f.setFecha(fechaFormateada);
				}
		        	 result.add(f);
 	            }
 	     } catch (SQLException e) {
 	         e.printStackTrace();
 	     }finally {con.close();}

 	     return result;
 	 }

	public static List getProcesos() throws SQLException
	{
		List result=new ArrayList();
		   Connection con = Conexion.conectar();
		   String query = "select distinct idProceso from dbo.SPD_LOG where 1=1 ";
		   query+= " and fecha > SYSDATE -  " + SPDConstants.DIAS_CONSULTA_LOG  ;
		 //	System.out.println(className + "--> getListado -->" +query );		
	     	ResultSet resultSet = null;
 	 	
 	    try {
 	         PreparedStatement pstat = con.prepareStatement(query);
 	         resultSet = pstat.executeQuery();
 	         while (resultSet.next()) {
 	        	SpdLog f = new SpdLog();
	        	 f.setIdApartado(resultSet.getString("idProceso"));
	        	 result.add(f);
 	            }
 	     } catch (SQLException e) {
 	         e.printStackTrace();
 	     }finally {con.close();}

 	     return result;
 	 }

	
	public static List getApartados() throws SQLException
	{
		List result=new ArrayList();
		   Connection con = Conexion.conectar();
		   String query = "select distinct idApartado from dbo.SPD_LOG where 1=1 ";
		 //	System.out.println(className + "--> getListado -->" +query );		
	     	ResultSet resultSet = null;
 	 	
 	    try {
 	         PreparedStatement pstat = con.prepareStatement(query);
 	         resultSet = pstat.executeQuery();
 	         while (resultSet.next()) {
 	        	SpdLog f = new SpdLog();
	        	 f.setIdApartado(resultSet.getString("idApartado"));
	        	 result.add(f);
 	            }
 	     } catch (SQLException e) {
 	         e.printStackTrace();
 	     }finally {con.close();}

 	     return result;
 	 }

	public static List getAcciones() throws SQLException
	{
		List result=new ArrayList();
		   Connection con = Conexion.conectar();
		   String query = "select distinct idAccion from dbo.SPD_LOG where 1=1 ";
		 //	System.out.println(className + "--> getListado -->" +query );		
	     	ResultSet resultSet = null;
 	 	
 	    try {
 	         PreparedStatement pstat = con.prepareStatement(query);
 	         resultSet = pstat.executeQuery();
 	         while (resultSet.next()) {
 	        	SpdLog f = new SpdLog();
	        	 f.setIdAccion(resultSet.getString("idAccion"));
	        	 result.add(f);
 	            }
 	     } catch (SQLException e) {
 	         e.printStackTrace();
 	     }finally {con.close();}

 	     return result;
 	 }
	
	public static List getSubAcciones() throws SQLException
	{
		List result=new ArrayList();
		   Connection con = Conexion.conectar();
		   String query = "select distinct idSubAccion from dbo.SPD_LOG where 1=1 ";
		 //	System.out.println(className + "--> getListado -->" +query );		
	     	ResultSet resultSet = null;
 	 	
 	    try {
 	         PreparedStatement pstat = con.prepareStatement(query);
 	         resultSet = pstat.executeQuery();
 	         while (resultSet.next()) {
 	        	SpdLog f = new SpdLog();
	        	 f.setIdSubAccion(resultSet.getString("idSubAccion"));
	        	 result.add(f);
 	            }
 	     } catch (SQLException e) {
 	         e.printStackTrace();
 	     }finally {con.close();}

 	     return result;
 	 }

	
	
}