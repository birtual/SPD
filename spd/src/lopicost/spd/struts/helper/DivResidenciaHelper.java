package lopicost.spd.struts.helper;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lopicost.config.pool.dbaccess.Conexion;
import lopicost.spd.controller.SpdLogAPI;
import lopicost.spd.model.DivisionResidencia;
import lopicost.spd.persistence.DivisionResidenciaDAO;
import lopicost.spd.persistence.FicheroResiDetalleDAO;
import lopicost.spd.persistence.PacienteDAO;
import lopicost.spd.persistence.UsuarioDAO;
import lopicost.spd.security.helper.VisibilidadHelper;
import lopicost.spd.struts.bean.BolquersDetalleBean;
import lopicost.spd.struts.bean.DiscrepanciaBean;
import lopicost.spd.struts.bean.FicheroResiBean;
import lopicost.spd.struts.bean.PacienteBean;
import lopicost.spd.struts.bean.TratamientoRctBean;
import lopicost.spd.struts.form.DivResidenciasForm;
import lopicost.spd.struts.form.PacientesForm;
import lopicost.spd.utils.SPDConstants;
import lopicost.spd.utils.StringUtil;


/**
 
 *Logica de negocio 
 */
public class DivResidenciaHelper  {


	private final static String cLOGGERHEADER = "DivResidenciaHelper: ";
	private final String cLOGGERHEADER_ERROR = cLOGGERHEADER + "ERROR: ";





	public static boolean nuevo(String idUsuario, DivResidenciasForm form) throws SQLException {
		boolean result = false;// DivisionResidenciaDAO.nuevo(form);
		if(result)
		{
		//INICIO creación de log en BBDD
			try{
				SpdLogAPI.addLog(idUsuario, null, form.getIdDivisionResidencia(), null, SpdLogAPI.A_RESIDENCIA, SpdLogAPI.B_CREACION, ".", "SpdLog.residencia.creacion.crear", 
						 new String[]{idUsuario, form.getIdDivisionResidencia()} );
			}catch(Exception e){}	// Cambios--> @@.
			//FIN creación de log en BBDD
		}
		return result;
	}

	


	
	public static boolean actualizaDatos(String idUsuario, DivisionResidencia p, DivResidenciasForm f) throws SQLException {
		boolean cambios =false;
		boolean cambiosNombre =false;
		boolean cambiosEstado =false;
		String antes = ""; 
		String despues = "";

		
		String querySet="";
		if(p!=null)
		{
			if(cambios)
			{
				//String query = " UPDATE SPDAC.dbo.bd_pacientes SET " + querySet + " WHERE  OIDPACIENTE='"+p.getOidPaciente()+"'";
				//cambios = PacienteDAO.edita(query);

				//INICIO creación de log en BBDD
				try{
					SpdLogAPI.addLog(idUsuario, null,  p.getIdDivisionResidencia(), null, SpdLogAPI.A_RESIDENTE, SpdLogAPI.B_EDICION, SpdLogAPI.C_DATOSGENERALES, "SpdLog.residente.edicion.general", 
							 new String[]{idUsuario, p.getIdDivisionResidencia(), antes, despues} );
				}catch(Exception e){}	// Cambios--> @@.
				//FIN creación de log en BBDD
			}
		}
		return cambiosEstado;

	}
		


		
}
	


	
	
	
	
	
	
	
	
	