package lopicost.spd.controller;

import lopicost.spd.model.SpdLog;
import lopicost.spd.utils.TextManager;
import lopicost.spd.persistence.SpdLogDao;

import java.sql.SQLException;
import java.util.TreeMap;



/**
 * @author ccostap
  */
public class SpdLogAPI 
{
	//MODULO - Constantes de primer nivel 
	public static final String A_RESIDENTE = "RESIDENTE";
	public static final String A_SUSTITUCION= "SUSTITUCION";
	public static final String A_GESTION_FARMACIA= "GESTION_FARMACIA";
	public static final String A_PRODUCCION= "PRODUCCION";
	public static final String A_TRATAMIENTO= "TRATAMIENTO";
	public static final String A_IOSPD= "IOSPD";	
	public static final String A_RESIDENCIA = "RESIDENCIA";
	
	
	//accion - Constantes de segundo nivel 
	public static final String B_CONSULTA= "CONSULTA";
	public static final String B_CARGAFICHERO= "CARGAFICHERO";
	public static final String B_DESCARGAFICHERO= "DESCARGAFICHERO";
	public static final String B_EDICION= "EDICION";
	public static final String B_BORRADO= "BORRADO";
	public static final String B_CREACION= "CREACION";
	public static final String B_VALIDACION= "VALIDACION";
	public static final String B_CONFIRMACION= "CONFIRMACION";
	public static final String B_CONFIRMACION_MASIVA= "CONFIRMACION_MASIVA";
	public static final String B_COMPARACION= "COMPARACION";
	public static final String B_EXPORTACION= "EXPORTACION";
	

	//subAccion - Constantes de tercer nivel 
	public static final String C_DATOSGENERALES= "DATOSGENERALES";
	public static final String C_LISTADO= "LISTADO";
	public static final String C_LISTADO_HIST= "LISTADO_HIST";
	public static final String C_DETALLE= "DETALLE";
	public static final String C_DIAS= "DIAS";
	public static final String C_PAUTAS= "PAUTAS";
	public static final String C_START= "INICIO";
	public static final String C_END= "FIN";	
	public static final String C_PREVISION= "PREVISION";
	public static final String C_FICHERO_HELIUM= "FICHERO_HELIUM";
	public static final String C_FICHERO_ROBOT_UNIFICADA= "FICHERO_ROBOT_UNIFICADA";
	public static final String C_FICHERO_IOSPD_ANEXO = "C_FICHERO_IOSPD_ANEXO";
	public static final String C_CONTROL_PRINCIPIO_ACTIVO= "CONTROL_PRINCIPIO_ACTIVO";

	
	
	
	public static final String TODOS_REGISTROS = ".";
	public static final String C_FICHERO_DM_RX = "FICHEROS_DM_RX";
	
	
	private static TreeMap cacheLogLevels = null;
	
	/**
	 * Constructor de clase
	 * @param cl
	 * @param idApartado
	 * @param accion
	 * @param subAccion
	 * @param borraCache
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */

	public static boolean addLog(String idUsuario, String CIP, String idDivisionResidencia, String idProceso, String idApartado, String accion, String subAccion, String tag) throws ClassNotFoundException, SQLException
	{
		return add(idUsuario,  CIP,  idDivisionResidencia, idProceso, idApartado, accion, subAccion, tag);
	}


	public static  boolean addLog(String idUsuario, String CIP, String idDivisionResidencia, String idProceso, String idApartado, String accion, String subAccion, String tag,  String[] variables) throws ClassNotFoundException, SQLException
	{
		return add(idUsuario,  CIP,  idDivisionResidencia, idProceso, idApartado, accion, subAccion, setdescripcion(tag, variables));

	}

	public static boolean addLog(String idUsuario, String CIP, String idDivisionResidencia, String idProceso, String idApartado, String accion, String subAccion, String tag,  String variable) throws ClassNotFoundException, SQLException
	{
		return add(idUsuario,  CIP,  idDivisionResidencia, idProceso, idApartado, accion, subAccion, setdescripcion(tag, variable));
	}
	


	private static boolean add(String idUsuario, String CIP, String idDivisionResidencia, String idProceso, String idApartado, String accion, String subAccion,  String descripcion) throws ClassNotFoundException, SQLException
	{
		if(idUsuario==null || idUsuario.equals("")) return false;
		if(CIP==null || CIP.equals("")) CIP =".";
		if(idDivisionResidencia==null || idDivisionResidencia.equals("")) idDivisionResidencia =".";
		if(idProceso==null || idProceso.equals("")) idProceso =".";
		if(idApartado==null || idApartado.equals("")) idApartado =".";
		if(accion==null || accion.equals("")) accion =".";
		if(subAccion==null || subAccion.equals("")) subAccion =".";		
		SpdLog spdlog = new SpdLog(idUsuario, CIP, idDivisionResidencia, idProceso,   idApartado, accion, subAccion, descripcion);
		return SpdLogDao.addLog(spdlog);
	}





	private static String setdescripcion(String tag, String[] variables)
	{
		if(variables!=null && variables.length>0)			
			return  TextManager.getFormattedLogMessage(tag,variables);
		else				
			return  tag;
	}

	private static String setdescripcion(String tag, String variable)
	{
		if(variable!=null && !variable.equals(""))			
			return	TextManager.getFormattedLogMessage(tag,variable);
		else				
			return  tag;
	}

	public static void setBorrarcache()
	{
		cacheLogLevels=new TreeMap();	
	}


}
